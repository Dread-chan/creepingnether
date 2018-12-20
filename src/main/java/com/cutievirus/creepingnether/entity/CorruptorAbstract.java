package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.EasyMap;
import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.block.BlockModSlab;

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
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import javax.annotation.Nullable;

public abstract class CorruptorAbstract {
	
	// lambda
	public interface BlockForMeta extends Function<Integer,Object>{}
	public interface Corruption{ void corrupt(World world,BlockPos pos); }
	public interface EntityCorruption{ boolean corrupt(EntityLiving entity); }
	
	protected World world;
	protected BlockPos position;
	protected long genesis;
	protected List<NetherFairy> fairies = new ArrayList<>();
	protected List<NetherFairy> new_fairies = new ArrayList<>();
	
	protected static Random rand = Ref.rand;
	
	public static final EasyMap<Object> corruptionMap = new EasyMap<Object>();
	public static final EasyMap<Corruption> corruptionSpecial = new EasyMap<Corruption>();
	public static final EasyMap<Corruption> corruptionFinal = new EasyMap<Corruption>();
	public static final EasyMap<EntityCorruption> corruptionEntities = new EasyMap<EntityCorruption>();
	
	CorruptorAbstract(World world, BlockPos pos){
		genesis = world.getTotalWorldTime();
		this.world=world;
		this.position=pos;
	}
	
	protected static void setFire(World world, BlockPos pos) {
		world.setBlockState(pos,Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 15),2);
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		genesis = compound.getLong("genesis");
	}

	public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setLong("genesis", genesis);
	}
	
	public double getLife () {
		return Math.min((world.getTotalWorldTime() - genesis)/(double)getCreepTime(),1);
	}
	
	public long getCreepTime () {
		return (long)Options.creep_time*1200;
	}
	
	public int getRadius () {
		return (int)(getMaxRadius() * getLife())+1;
	}
	
	public int getMaxRadius () {
		return Options.creep_radius;
	}
	
	public boolean inRadius(BlockPos pos) {
		if(Options.creep_radius==0) { return true; }
		int radius = getRadius();
		return getPos().distanceSq(pos) <= radius*radius;
	}
	
	public BlockPos getPos() {
		return position;
	}
	public void setPos (BlockPos pos) {
		this.position = pos;
	}
	
	public static Biome getBiome() {
		return Ref.biome;
	}
	
	public static boolean doesBlockCreep() { return testChance(Options.creep_chance); }
	public static boolean doesPortalCreep() { return testChance(Options.portal_creep_chance); }
	public static boolean doesNetherCreep() { return testChance(Options.internalcorruption); }
	public static boolean testChance(double chance) {
		return rand.nextDouble()*100<chance;
	}
	
	public BlockPos randomPos(int radius) {
		int diameter = 2*radius+1;
		return this.position.add(
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
	
	public static void updateFairies(World world, List<NetherFairy> fairies, List<NetherFairy> new_fairies ) {
		
		world.profiler.startSection("Update Fairies");
		Iterator<NetherFairy> it = fairies.iterator();
		while (it.hasNext()) {
			NetherFairy fairy = it.next();
			if(!doesBlockCreep()) { continue;}
			if(updateFairy(world,fairy,new_fairies)) { it.remove(); }
		}
		if( fairies.size()<Options.fairylimit && new_fairies.size()>0) {
			fairies.add(new_fairies.remove(0));
		}
		//fairies.addAll(new_fairies);
		//new_fairies.clear();
		world.profiler.endSection();
	}
	
	private static boolean updateFairy(World world, NetherFairy fairy, List<NetherFairy> new_fairies) {
		boolean remove = true;
		if (!world.isBlockLoaded(fairy.pos)) { return remove; }
		Block thisblock = world.getBlockState(fairy.pos).getBlock();
		if (!fairy.blockValid(thisblock)) { return remove; }
		BlockPos neighbor = fairy.getNeighbor();
		DoCorruption(world,neighbor,new_fairies);
		if(fairy.neighbors.size() == 0) {
			corruptionFinal(world, fairy.pos);
			return remove;
		}
		return false;
	}
	
	public static void corruptBiome(World world, BlockPos pos) {
		if(!world.isBlockLoaded(pos)) {return;}
		Biome oldbiome = world.getBiome(pos);
		Biome newbiome = getBiome();
		if(oldbiome==newbiome
		|| oldbiome==Biomes.HELL&&newbiome==Ref.biome) {
			return;
		}
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		chunk.getBiomeArray()[(pos.getZ() & 15) << 4 | (pos.getX() & 15)] =
				(byte) Biome.getIdForBiome(newbiome);
		CreepingMessage.sendMessage(world, pos, CreepingMessage.CORRUPTBIOME);
	}
	
	public static void DoCorruption(World world, BlockPos pos, List<NetherFairy> new_fairies) {
		NetherFairy newfairy = DoCorruption(world, pos);
		if (newfairy.into != null && new_fairies.size()<Options.fairylimit) {
			new_fairies.add(newfairy);
		}
	}
	
	public static NetherFairy DoCorruption(World world, BlockPos pos) {
		
		IBlockState state1 = world.getBlockState(pos);
		Block block1 = state1.getBlock();
		String blockname = block1.getRegistryName().toString();
		Object[] keys = {blockname,block1};
		
		Corruption corruptSpecial = corruptionSpecial.getFrom(keys);
		if(corruptSpecial != null) {
			corruptSpecial.corrupt(world,pos);
		}
		
		Object value = corruptionMap.getFrom(keys);
		Block block2 = null;
		if(value instanceof BlockForMeta) {
			value=((BlockForMeta) value).apply(block1.getMetaFromState(state1));
		}
		if( value instanceof Block) { block2 = (Block)value; }
		else if(value instanceof String) { block2 = Block.getBlockFromName((String)value); }
		if (block1 instanceof BlockSlab && block2 instanceof BlockModSlab) {
			BlockModSlab slab = (BlockModSlab)block2;
			block2 = ((BlockSlab)block1).isDouble()?slab.getFullVersion():slab.getHalfVersion();
		}
		if(block2 != null) {
			IBlockState state2 = block2.getDefaultState();
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
	
	public static void corruptionFinal(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		String blockname = block.getRegistryName().toString();
		Object[] keys = {blockname,block};
		Corruption corruptFinal = corruptionFinal.getFrom(keys);
		if(corruptFinal != null) {
			corruptFinal.corrupt(world, pos);
		}
	}
	
	public static void corruptEntities(World world, BlockPos pos) {
		corruptEntities(world,pos,3d);
	}
	
	public static void corruptEntities(World world, BlockPos pos, double radius) {
        List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class,
        	new AxisAlignedBB(
        	pos.getX()-radius, pos.getY()-radius, pos.getZ()-radius,
        	pos.getX()+1+radius, pos.getY()+1+radius, pos.getZ()+1+radius));
        int lightningCount=0;
        int x=0,y=0,z=0;
        for(EntityLiving entity : list) {
        	if(entity.isDead) { continue; }
    		String name = getEntityString(entity);
    		EntityCorruption corruption = corruptionEntities.get(name);
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
	
	protected static void corruptionEffect(World world, BlockPos pos) {
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
		return state2.withProperty(BlockSlab.HALF, state1.getValue(BlockSlab.HALF));
	}
	
	protected static void replaceMob(EntityLiving mob1, EntityLiving mob2){
		World world = mob1.world;
		if (world.isRemote || mob1.isDead){ return; }
		mob2.setLocationAndAngles(mob1.posX, mob1.posY, mob1.posZ,
				mob1.rotationYaw, mob1.rotationPitch);
        world.spawnEntity(mob2);
        mob1.setDead();
	}
	protected static void copyMobData(EntityLiving mob1, EntityLiving mob2){
		World world = mob1.world;
        if (world.isRemote || mob1.isDead){ return; }
        mob2.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(mob2)), (IEntityLivingData)null);
        mob2.setNoAI(mob1.isAIDisabled());
        if(mob2 instanceof EntityZombie) {
        	((EntityZombie) mob2).setChild(mob1.isChild());
        }
        if(mob1 instanceof EntityAgeable && mob2 instanceof EntityAgeable) {
        	((EntityAgeable)mob2).setGrowingAge(((EntityAgeable)mob1).getGrowingAge());
        }
        if (mob1.hasCustomName()){
            mob2.setCustomNameTag(mob1.getCustomNameTag());
            mob2.setAlwaysRenderNameTag(mob1.getAlwaysRenderNameTag());
        }
        Iterator<ItemStack> it = mob1.getEquipmentAndArmor().iterator();
        while(it.hasNext()) {
        	ItemStack item = it.next();
        	if(item.getItem()==Items.AIR) { continue; }
        	EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(item);
        	mob2.setItemStackToSlot(slot, item);
        }
	}
	
	protected static void loadCustomCorruption(String[] customList) {
		for (String corruption : customList) {
			String[] arr = corruption.split(">",2);
			if(arr.length<2) { continue; }
			corruptionMap.add(arr[0], arr[1]);
		}
	}
	
}
