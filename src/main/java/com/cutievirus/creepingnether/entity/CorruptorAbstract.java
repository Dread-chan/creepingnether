package com.cutievirus.creepingnether.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.cutievirus.creepingnether.EasyMap;
import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.Utility;
import com.cutievirus.creepingnether.block.BlockModSlab;

import gnu.trove.list.array.TIntArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public abstract class CorruptorAbstract {

	// lambda
	public interface BlockForMeta{ Object apply(int data); }
	public interface BlockForPos{ Object apply(World world,BlockPos pos); }
	public interface Corruption{ void corrupt(World world,BlockPos pos); }
	public interface EntityCorruption{ boolean corrupt(EntityLiving entity); }

	protected World world = null;
	protected BlockPos position=BlockPos.ORIGIN;
	protected long genesis=0;
	protected double powerlevel=1.0;
	protected List<NetherFairy> fairies = new ArrayList<>();
	protected List<NetherFairy> new_fairies = new ArrayList<>();

	protected static Random rand = Ref.rand;

	public abstract CorruptorAbstract getInstance();
	public abstract EasyMap<Object> getCorruptionMap();
	public abstract EasyMap<Corruption> getCorruptionSpecial();
	public abstract EasyMap<Corruption> getCorruptionFinal();
	public abstract EasyMap<EntityCorruption> getCorruptionEntities();

	public abstract Biome getBiome();

	CorruptorAbstract(){
		genesis=0;
		world=null;
	}
	CorruptorAbstract(World world, BlockPos pos){
		genesis = world.getTotalWorldTime();
		this.world=world;
		position=pos;
	}

	protected static void setFire(World world, BlockPos pos) {
		world.setBlockState(pos,Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 15),2);
	}

	public void readNBT(NBTTagCompound compound) {
		genesis = compound.getLong("genesis");
		int[] fairy_list = compound.getIntArray("fairies");
		for (int f=0; f<fairy_list.length-4; f+=5) {
			new_fairies.add(new NetherFairy(
					new BlockPos(fairy_list[f],fairy_list[f+1],fairy_list[f+2]),
					Block.getBlockById(fairy_list[f+3]),
					Block.getBlockById(fairy_list[f+4])
					));
		}
	}

	public void writeNBT(NBTTagCompound compound) {
        compound.setLong("genesis", genesis);
        TIntArrayList fairy_list = new TIntArrayList();
        Consumer<List<NetherFairy>> addFairies = (fairies)->{
            for (NetherFairy fairy : fairies) {
            	fairy_list.add(fairy.pos.getX());
            	fairy_list.add(fairy.pos.getY());
            	fairy_list.add(fairy.pos.getZ());
            	fairy_list.add(Block.getIdFromBlock(fairy.base));
            	fairy_list.add(Block.getIdFromBlock(fairy.into));
            }
        };
        addFairies.accept(fairies);
        addFairies.accept(new_fairies);
        compound.setIntArray("fairies", fairy_list.toArray());
	}

	public double getLife () {
		if(world==null) { return 0; }
		return Math.min((world.getTotalWorldTime() - genesis)/(double)getCreepTime(),1);
	}

	public long getCreepTime () {
		return (long)Options.creep_time*1200;
	}

	public int getRadius () {
		return (int)(getMaxRadius() * getLife() * powerlevel)+1;
	}

	public int getMaxRadius () {
		return Options.creep_radius;
	}

	public boolean inRadius(BlockPos pos) {
		if(Options.creep_radius==0 || world==null) { return true; }
		int radius = getRadius();
		return getPos().distanceSq(pos) <= radius*radius;
	}

	public BlockPos getPos() {
		return position;
	}
	public void setPos (BlockPos pos) {
		position = pos;
	}
	public void setPower(double power) {
		powerlevel = power;
	}

	public static boolean doesBlockCreep() { return testChance(Options.creep_chance); }
	public static boolean doesPortalCreep() { return testChance(Options.portal_creep_chance); }
	public static boolean doesNetherCreep() { return testChance(Options.internalcorruption); }
	public static boolean testChance(double chance) {
		return rand.nextDouble()*100<chance;
	}

	public BlockPos randomPos(int radius) {
		int diameter = 2*radius+1;
		return position.add(
				rand.nextInt(diameter)-radius,
				rand.nextInt(diameter)-radius,
				rand.nextInt(diameter)-radius);
	}

	public void onUpdate() {
		world.profiler.startSection("Corruptor");
		if(doesPortalCreep()) {
			int radius;
			if(Options.creep_radius==0) { radius = 16; }
			else { radius = getRadius(); }
			BlockPos pos=randomPos(radius);
			if(world.isBlockLoaded(pos) && inRadius(pos)) {
				DoCorruption(world,pos,new_fairies);
			}
			radius = Math.max(6, rand.nextInt(radius));
			pos=randomPos(radius);
			if(world.isBlockLoaded(pos) && inRadius(pos)) {
				DoCorruption(world,pos,new_fairies);
				corruptEntities(world, pos);
			}
		}
		updateFairies(world,fairies,new_fairies);
		world.profiler.endSection();
	}

	public void updateFairies(World world, List<NetherFairy> fairies, List<NetherFairy> new_fairies ) {
		//System.out.println(new_fairies.size());
		world.profiler.startSection("Update Fairies");
		Iterator<NetherFairy> it = fairies.iterator();
		while (it.hasNext()) {
			NetherFairy fairy = it.next();
			if(!doesBlockCreep()) { continue;}
			if(updateFairy(world,fairy,new_fairies)) { it.remove(); }
		}
		while( fairies.size()<Options.fairylimit && new_fairies.size()>0) {
			fairies.add(new_fairies.remove(0));
		}
		//fairies.addAll(new_fairies);
		//new_fairies.clear();
		world.profiler.endSection();
	}

	private boolean updateFairy(World world, NetherFairy fairy, List<NetherFairy> new_fairies) {
		boolean remove = true;
		if (!world.isBlockLoaded(fairy.pos)) { return remove; }
		Block thisblock = world.getBlockState(fairy.pos).getBlock();
		if (!fairy.blockValid(thisblock)) { return remove; }
		BlockPos neighbor = fairy.getNeighbor();
		if(world.isBlockLoaded(neighbor) && inRadius(neighbor)) {
			DoCorruption(world,neighbor,new_fairies);
		}
		if(fairy.neighbors.size() == 0) {
			corruptionFinal(world, fairy.pos);
			return remove;
		}
		return false;
	}

	public void corruptBiome(World world, BlockPos pos) {
		if(!world.isBlockLoaded(pos)) {return;}
		Biome oldbiome = world.getBiome(pos);
		Biome newbiome = getBiome();
		if(oldbiome==newbiome
		|| oldbiome==Biomes.HELL&&newbiome==Ref.biomeCreepingNether) {
			return;
		}
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		chunk.getBiomeArray()[(pos.getZ() & 15) << 4 | (pos.getX() & 15)] =
				(byte) Biome.getIdForBiome(newbiome);
		if(world.isRemote) { return; }
		MessageCorruptBiome.sendMessage(world, pos, getInstance());
	}

	public void DoCorruption(World world, BlockPos pos, List<NetherFairy> new_fairies) {
		NetherFairy newfairy = DoCorruption(world, pos);
		if (newfairy.into != null && new_fairies.size()<Options.fairylimit) {
			new_fairies.add(newfairy);
		}
	}

	public NetherFairy DoCorruption(World world, BlockPos pos) {

		IBlockState state1 = world.getBlockState(pos);
		Block block1 = state1.getBlock();
		Object[] keys = getKeys(state1);

		Corruption corruptSpecial = getCorruptionSpecial().getFrom(keys);
		if(corruptSpecial != null) {
			corruptSpecial.corrupt(world,pos);
		}

		Block block2 = null;
		IBlockState state2 = getCorruptState(world,pos);
		if (state2 != null) {
			block2 = state2.getBlock();
			if (block1 instanceof BlockSlab && block2 instanceof BlockModSlab) {
				BlockModSlab slab = (BlockModSlab)block2;
				block2 = ((BlockSlab)block1).isDouble()?slab.getFullVersion():slab.getHalfVersion();
				state2 = block2.getDefaultState();
			}
			if(block1 instanceof BlockRotatedPillar && block2 instanceof BlockRotatedPillar)
			{ state2 = copyPillarState(state1,state2); }
			else if(block1 instanceof BlockStairs && block2 instanceof BlockStairs)
			{ state2 = copyStairState(state1,state2); }
			else if(block1 instanceof BlockSlab && block2 instanceof BlockSlab)
			{ state2 = copySlabState(state1,state2); }
			world.setBlockState(pos, state2, 2);
			corruptBiome(world,pos);
		}
		return new NetherFairy(pos, block1, block2);
	}

	public static Object[] getKeys(IBlockState state) {
		Block block = state.getBlock();
		String blockname = block.getRegistryName().toString();
		String statename = blockname+"@"+block.getMetaFromState(state);
		Object[] keys = {statename,blockname,block};
		return keys;
	}

	public static IBlockState getStateForName(String name) {
		String datastring = "";
		if(name.contains("@")) {
			String[] parts = name.split("@");
			if(parts.length==0) {return null;}
			if(parts.length==1) {name=parts[0]; datastring="";}
			else { name=parts[0]; datastring=parts[1]; }
		}
		Block block = Block.getBlockFromName(name);
		if(block == null) { return null; }
		IBlockState state = block.getDefaultState();
		try{
			int data = Integer.parseInt(datastring);
			@SuppressWarnings("deprecation")
			IBlockState metastate = block.getStateFromMeta(data);
			state = metastate;
		}catch(NumberFormatException e) {
			state = block.getDefaultState();
		}
		return state;
	}

	public IBlockState getCorruptState(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);
		Object[] keys = getKeys(state);
		IBlockState corruptstate = null;
		for (Object key:keys) {
			Object value = getCorruptionMap().get(key);
			if(value instanceof BlockForMeta) {
				value=((BlockForMeta) value).apply(meta);
			}else if(value instanceof BlockForPos) {
				value=((BlockForPos) value).apply(world,pos);
			}
			if(value==null) { continue; }
			else if(value.equals("null")) { break; }
			else if( value instanceof Block) {
				corruptstate = ((Block)value).getDefaultState();
				break;
			}
			else if( value instanceof String) {
				corruptstate = getStateForName((String)value);
				if (corruptstate==null) { continue; }
				break;
			}
		}
		return corruptstate;
	}

	public void corruptionFinal(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		String blockname = block.getRegistryName().toString();
		Object[] keys = {blockname,block};
		Corruption corruptFinal = getCorruptionFinal().getFrom(keys);
		if(corruptFinal != null) {
			corruptFinal.corrupt(world, pos);
		}
	}

	public void corruptEntities(World world, BlockPos pos) {
		corruptEntities(world,pos,3d);
	}

	public void corruptEntities(World world, BlockPos pos, double radius) {
        List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class,
        	new AxisAlignedBB(
        	pos.getX()-radius, pos.getY()-radius, pos.getZ()-radius,
        	pos.getX()+1+radius, pos.getY()+1+radius, pos.getZ()+1+radius));
        int lightningCount=0;
        int x=0,y=0,z=0;
        for(EntityLiving entity : list) {
        	if(entity.isDead) { continue; }
    		String name = getEntityString(entity);
    		EntityCorruption corruption = getCorruptionEntities().get(name);
    		if(corruption != null) {
    			if(corruption.corrupt(entity)) {
            		++lightningCount;
            		x+=entity.posX;
            		y+=entity.posY;
            		z+=entity.posZ;
    			}
        	}
        }
        if(lightningCount>0) {
        	corruptionEffect(world,new BlockPos(x/lightningCount,y/lightningCount,z/lightningCount));
        }
	}

	protected void corruptionEffect(World world, BlockPos pos) {
		lightning(world, pos);
	}

	@Nullable
	private static String getEntityString (Entity entity) {
        ResourceLocation resourcelocation = EntityList.getKey(entity);
        return resourcelocation == null ? null : resourcelocation.toString();
	}

	protected static void lightning(World world, BlockPos pos) {
		if(!Options.entity_corruption.lightning) { return; }
		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true));
	}

	protected static void lightning(EntityLiving mob) {
		if(!Options.entity_corruption.lightning) { return; }
		mob.world.addWeatherEffect(new EntityLightningBolt(mob.world, mob.posX, mob.posY, mob.posZ, true));
	}

	private static IBlockState copyPillarState(IBlockState state1, IBlockState state2) {
		Block block1 = state1.getBlock();
		Block block2 = state2.getBlock();
		if(block1 instanceof BlockLog && block2 instanceof BlockLog) {
			return state2.withProperty(BlockLog.LOG_AXIS, state1.getValue(BlockLog.LOG_AXIS));
		}
		EnumFacing.Axis axis;
		if(block1 instanceof BlockLog) {
			switch (state1.getValue(BlockLog.LOG_AXIS)) {
			case X: axis = EnumFacing.Axis.X; break;
			case Z: axis = EnumFacing.Axis.Z; break;
			default: axis = EnumFacing.Axis.Y; break;
			}
		}else { axis = state1.getValue(BlockRotatedPillar.AXIS); }
		if(block2 instanceof BlockLog) {
			return state2.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(axis));
		} else { return state2.withProperty(BlockRotatedPillar.AXIS, axis); }
	}

	private static IBlockState copyStairState(IBlockState state1, IBlockState state2) {
		return state2.withProperty(BlockStairs.FACING, state1.getValue(BlockStairs.FACING))
				.withProperty(BlockStairs.SHAPE, state1.getValue(BlockStairs.SHAPE))
				.withProperty(BlockStairs.HALF, state1.getValue(BlockStairs.HALF));
	}

	private static IBlockState copySlabState(IBlockState state1, IBlockState state2) {
		if( ((BlockSlab) state1.getBlock()).isDouble()) { return state2; }
		return state2.withProperty(BlockSlab.HALF, state1.getValue(BlockSlab.HALF));
	}

	protected static void replaceMob(EntityLiving mob1, EntityLiving mob2){
		World world = mob1.world;
		if (world.isRemote || mob1.isDead){ return; }
		mob2.setLocationAndAngles(mob1.posX, mob1.posY, mob1.posZ,
				mob1.rotationYaw, mob1.rotationPitch);
        world.spawnEntity(mob2);
		mob2.setLocationAndAngles(mob1.posX, mob1.posY, mob1.posZ,
				mob1.rotationYaw, mob1.rotationPitch);
        mob1.setDead();
	}
	protected static void copyMobData(EntityLiving mob1, EntityLiving mob2){
		World world = mob1.world;
        if (world.isRemote || mob1.isDead){ return; }
        //mob2.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(mob2)), (IEntityLivingData)null);

        String mob1_type = "entity:"+getEntityString(mob1);
        String mob2_type = "entity:"+getEntityString(mob2);
        NBTTagCompound mob1_nbt = new NBTTagCompound();
        NBTTagCompound mob2_nbt = new NBTTagCompound();
        if(Utility.dataHasKey(mob1, mob2_type)) {
        	mob2_nbt.merge(Utility.getDataCompound(mob1, mob2_type));
        }
        Utility.setModData(mob2, Utility.getModData(mob1));
    	mob1.writeEntityToNBT(mob1_nbt);
    	mob2_nbt.merge(mob1_nbt);
    	mob2_nbt.removeTag("Attributes");
    	mob2.readEntityFromNBT(mob2_nbt);
    	Utility.setDataCompound(mob2, mob1_type, mob1_nbt);

        if(mob2 instanceof EntityZombie) {
        	((EntityZombie) mob2).setChild(mob1.isChild());
        }
        if(!(mob1 instanceof EntityAgeable) && mob1.isChild() && mob2 instanceof EntityAgeable) {
        	((EntityAgeable) mob2).setGrowingAge(-24000);
        }

        mob2.setHealth(mob2.getMaxHealth()*mob1.getHealth()/mob1.getMaxHealth());
	}

	protected static void loadCustomCorruption(String[] customList,EasyMap<Object> corruptionMap) {
		for (String corruption : customList) {
			String[] arr = corruption.split(">",2);
			if(arr.length<2) { continue; }
			corruptionMap.add(arr[0], arr[1]);
		}
	}

}
