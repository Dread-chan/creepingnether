package com.cutievirus.creepingnether.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Options.TransformationLists;
import com.cutievirus.creepingnether.Options.TransformationLists.Transformations;
import com.cutievirus.creepingnether.Ref;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class EntityPortal extends Entity{

	//private long genesis = System.currentTimeMillis();
	private long genesis;
	protected List<NetherFairy> fairies = new ArrayList<>();
	protected List<NetherFairy> new_fairies = new ArrayList<>();
	
	public static final HashMap<Block,Block> corruptionMap = new HashMap<Block,Block>();
	static {
		//Air
		corruptionMap.put(Blocks.SNOW_LAYER, Blocks.AIR);
		//Netherrack
		corruptionMap.put(Blocks.DIRT, Blocks.NETHERRACK);
		corruptionMap.put(Blocks.MYCELIUM, Blocks.NETHERRACK);
		corruptionMap.put(Blocks.GRASS, Blocks.NETHERRACK);
		corruptionMap.put(Blocks.STONE, Blocks.NETHERRACK);
		corruptionMap.put(Blocks.HARDENED_CLAY, Blocks.NETHERRACK);
		corruptionMap.put(Blocks.STAINED_HARDENED_CLAY, Blocks.NETHERRACK);
		//Bricks
		corruptionMap.put(Blocks.SANDSTONE, Ref.soulstone);
		corruptionMap.put(Blocks.RED_SANDSTONE, Ref.soulstone);
		//Soul Sand
		corruptionMap.put(Blocks.SAND, Blocks.SOUL_SAND);
		corruptionMap.put(Blocks.CLAY, Blocks.SOUL_SAND);
		corruptionMap.put(Blocks.FARMLAND, Blocks.SOUL_SAND);
		//Blood Stone
		corruptionMap.put(Blocks.COBBLESTONE, Ref.bloodstone);
		corruptionMap.put(Blocks.MOSSY_COBBLESTONE, Ref.bloodstone);
		//Charred Wood
		corruptionMap.put(Blocks.LOG, Ref.charwood);
		corruptionMap.put(Blocks.LOG2, Ref.charwood);
		//stairs
		corruptionMap.put(Blocks.STONE_STAIRS, Ref.bloodstone_stairs);
		corruptionMap.put(Blocks.SANDSTONE_STAIRS, Ref.soulstone_stairs);
		corruptionMap.put(Blocks.RED_SANDSTONE_STAIRS, Ref.soulstone_stairs);
		corruptionMap.put(Blocks.OAK_STAIRS, Ref.charwood_stairs);
		corruptionMap.put(Blocks.SPRUCE_STAIRS, Ref.charwood_stairs);
		corruptionMap.put(Blocks.BIRCH_STAIRS, Ref.charwood_stairs);
		corruptionMap.put(Blocks.JUNGLE_STAIRS, Ref.charwood_stairs);
		corruptionMap.put(Blocks.ACACIA_STAIRS, Ref.charwood_stairs);
		corruptionMap.put(Blocks.DARK_OAK_STAIRS, Ref.charwood_stairs);
		//slabs
		// TODO: in 1.13 add slabs here.
		corruptionMap.put(Blocks.DOUBLE_WOODEN_SLAB, Ref.charwood_slab2);
		//Lava
		corruptionMap.put(Blocks.WATER, Ref.creepingobsidian);
		corruptionMap.put(Blocks.ICE, Ref.creepingobsidian);
		corruptionMap.put(Blocks.PACKED_ICE, Blocks.OBSIDIAN);
		//Ores
		corruptionMap.put(Blocks.COAL_ORE, Ref.nethercoal);
		corruptionMap.put(Blocks.IRON_ORE, Ref.netheriron);
		corruptionMap.put(Blocks.GOLD_ORE, Ref.nethergold);
		corruptionMap.put(Blocks.DIAMOND_ORE, Ref.netherdiamond);
		corruptionMap.put(Blocks.EMERALD_ORE, Ref.netheremerald);
		corruptionMap.put(Blocks.LAPIS_ORE, Ref.netherlapis);
		corruptionMap.put(Blocks.REDSTONE_ORE, Ref.netherredstone);
		corruptionMap.put(Blocks.LIT_REDSTONE_ORE, Ref.netherredstone);
		//other
		corruptionMap.put(Blocks.CACTUS, Blocks.NETHER_WART_BLOCK);
	}
	
	public EntityPortal(World world) {
		super(world);
		genesis = world.getTotalWorldTime();
	}
	
	public EntityPortal(World world, BlockPos pos) {
		super(world);
		this.setPosition(pos.getX(), pos.getY(), pos.getZ());
		genesis = world.getTotalWorldTime();
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		genesis = compound.getLong("genesis");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setLong("genesis", genesis);
	}
	
	public BlockPos getPos() {
		return new BlockPos(posX,posY,posZ);
	}
	
	public double getLife () {
		//return Math.min((System.currentTimeMillis() - genesis)/(Config.creep_speed*60000),1);
		return Math.min((world.getTotalWorldTime() - genesis)/(Options.creep_time*1200),1);
	}
	
	public int getRadius () {
		return (int)(Options.creep_radius * getLife())+1;
	}
	
	public boolean inRadius(BlockPos pos2) {
		if(Options.creep_radius==0) { return true; }
		int radius = getRadius();
		return getPos().distanceSq(pos2) <= radius*radius;
	}
	
	public static void createPortal(World world, BlockPos pos) {
		if(world.getEntitiesWithinAABB(EntityPortal.class, new AxisAlignedBB(pos.getX()-1d, pos.getY()-1d, pos.getZ()-1d, pos.getX()+2d, pos.getY()+2d, pos.getZ()+2d)).size()>0){
			return;
		}
		world.spawnEntity(new EntityPortal(world,pos));
	}
	private static Biome toBiome = Ref.biome;
	public static void corruptBiome(World world, BlockPos pos) {
		if(!world.isBlockLoaded(pos)) {return;}
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		chunk.getBiomeArray()[(pos.getZ() & 15) << 4 | (pos.getX() & 15)] = (byte) Biome.getIdForBiome(toBiome);
	}
	
	@Override
	public void onUpdate() {
		BlockPos pos = getPos();
		if(world.getBlockState(pos).getBlock()!=Blocks.PORTAL) {
			this.setDead();
			return;
		}
		
		if (testChance(Options.portal_creep_chance)) {
			int radius;
			if(Options.creep_radius==0) {
				radius = 7;
			} else {
				radius = getRadius();
			}
			int diameter = 2*radius+1;
			BlockPos loc=pos.add(rand.nextInt(diameter)-radius, rand.nextInt(diameter)-radius, rand.nextInt(diameter)-radius);
			if (world.isBlockLoaded(loc)) {
				trySpread(loc);
			}
			if(Options.creep_radius==0) {
				radius = 19;
			}else {
				radius = Math.min(radius, Math.max(6, rand.nextInt(radius)));
			}
			diameter = 2*radius+1;
			loc=pos.add(rand.nextInt(diameter)-radius, rand.nextInt(diameter)-radius, rand.nextInt(diameter)-radius);
			if (world.isBlockLoaded(loc) && inRadius(loc)) {
				corruptEntities(world, loc);
			}
		}
		
		Iterator<NetherFairy> it = fairies.iterator();
		while (it.hasNext()) {
			NetherFairy fairy = it.next();
			if(!testChance(Options.creep_chance)) { continue; }
			if(updateFairy(fairy)) {
				it.remove();
			}
		}
		fairies.addAll(new_fairies);
		new_fairies.clear();
	}
	
	public boolean updateFairy(NetherFairy fairy) {
		if (!world.isBlockLoaded(fairy.pos)) {
			return true; // kill
		}
		Block thisblock = world.getBlockState(fairy.pos).getBlock();
		if (!fairy.blockValid(thisblock)) {
			return true; // kill
		}
		
		//BlockPos neighbor = fairy.getNeighbor();
		//System.out.println("trying to spread "+world.getBlockState(neighbor).getBlock().toString());
		//boolean result = trySpread(neighbor);
		//System.out.println(result?"success":"failure");
		trySpread(fairy.getNeighbor());
		
		if(fairy.neighbors.size() == 0) {
			corruptionFinal(world, fairy);
			return true; //kill
		}

		return false;
	}
	
	public boolean trySpread(BlockPos corruptPos) {
		if ( !inRadius(corruptPos) ) {
			return false;
		}
		Block base = world.getBlockState(corruptPos).getBlock();
		corruptionSpecial(world, corruptPos);
		if (!corruptionMap.containsKey(base)) {
			return false;
		}
		Block into = corruptionMap.get(base);
		if(world.getBlockState(corruptPos).getBlock()!=into) {
			world.setBlockState(corruptPos, into.getDefaultState(),2);
		}
		if(world.getBiome(corruptPos)!=toBiome) {
			corruptBiome(world,corruptPos);
			CreepingMessage.sendMessage(world, corruptPos, CreepingMessage.CORRUPTBIOME);
		}
		// new corruption
		new_fairies.add(new NetherFairy(corruptPos, base, into));
		return true;
	}
	
	public static boolean testChance(float chance) {
		return Ref.rand.nextFloat()*100<chance;
	}
	public static boolean testChance(double chance) {
		return Ref.rand.nextDouble()*100<chance;
	}

	public static void setFire(World world, BlockPos pos) {
		world.setBlockState(pos,Blocks.FIRE.getDefaultState().withProperty(BlockFire.AGE, 15),2);
	}
	
	public static boolean corruptionSpecial(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		switch (block.getRegistryName().toString()) {
		case "minecraft:leaves":
		case "minecraft:leaves2":
			if(world.getBlockState(pos.up()).getBlock()==Blocks.AIR) {
				setFire(world, pos.up());
			}else if(world.getBlockState(pos.north()).getBlock()==Blocks.AIR
			||world.getBlockState(pos.south()).getBlock()==Blocks.AIR
			||world.getBlockState(pos.east()).getBlock()==Blocks.AIR
			||world.getBlockState(pos.west()).getBlock()==Blocks.AIR){
				if(world.getBlockState(pos.north()).getBlock()==Blocks.AIR) {
					setFire(world, pos.north());
				}
				if(world.getBlockState(pos.south()).getBlock()==Blocks.AIR) {
					setFire(world, pos.south());
				}
				if(world.getBlockState(pos.east()).getBlock()==Blocks.AIR) {
					setFire(world, pos.east());
				}
				if(world.getBlockState(pos.west()).getBlock()==Blocks.AIR) {
					setFire(world, pos.west());
				}
				
			}else if(world.getBlockState(pos.down()).getBlock()==Blocks.AIR) {
				setFire(world, pos.down());
			}
			return true;
		case "minecraft:log":
		case "minecraft:log2":
			replaceLog(world,pos);
			return true;
		case "minecraft:tallgrass":
			if(Ref.rand.nextFloat()<0.5) {
				setFire(world, pos);
			}else {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(),2);
			}
			return true;
		case "minecraft:deadbush":
		case "minecraft:wheat":
		case "minecraft:potatoes":
		case "minecraft:carrots":
		case "minecraft:beetroots":
			world.setBlockState(pos,Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, 3),2);
			return true;
		case "minecraft:sandstone_stairs":
		case "minecraft:red_sandstone_stairs":
			replaceStairs(world,pos,Ref.soulstone_stairs);
			return true;
		case "minecraft:stone_stairs":
			replaceStairs(world,pos,Ref.bloodstone_stairs);
			return true;
		case "minecraft:oak_stairs":
		case "minecraft:spruce_stairs":
		case "minecraft:birch_stairs":
		case "minecraft:jungle_stairs":
		case "minecraft:acacia_stairs":
		case "minecraft:dark_oak_stairs":
			replaceStairs(world,pos,Ref.charwood_stairs);
			return true;
		case "minecraft:stone_slab":
		case "minecraft:stone_slab2":
			if (isSandstoneSlab(block,state)) {
				replaceSlab(world,pos,Ref.soulstone_slab);
				return true;
			} else if(isCobbleSlab(block,state)) {
				replaceSlab(world,pos,Ref.bloodstone_slab);
				return true;
			}
			return false;
		case "minecraft:double_stone_slab":
		case "minecraft:double_stone_slab2":
			if (isSandstoneSlab(block,state)) {
				world.setBlockState(pos, Ref.soulstone_slab2.getDefaultState(),2);
				return true;
			} else if(isCobbleSlab(block,state)) {
				world.setBlockState(pos, Ref.bloodstone_slab2.getDefaultState(),2);
				return true;
			}
			return false;
		case "minecraft:wooden_slab":
			replaceSlab(world,pos,Ref.charwood_slab);
			return true;
		//case "minecraft:double_wooden_slab":
		//	world.setBlockState(pos, Ref.charwood_slab2.getDefaultState(),2);
		//	return true;
		}
		return false;
	}
	
	public static boolean isSandstoneSlab(Block block, IBlockState state) {
		return block instanceof BlockStoneSlab
			&&state.getValue(BlockStoneSlab.VARIANT)==BlockStoneSlab.EnumType.SAND
			||block instanceof BlockStoneSlabNew
			&&state.getValue(BlockStoneSlabNew.VARIANT)==BlockStoneSlabNew.EnumType.RED_SANDSTONE;
	}
	public static boolean isCobbleSlab(Block block, IBlockState state) {
		return block instanceof BlockStoneSlab
		&&state.getValue(BlockStoneSlab.VARIANT)==BlockStoneSlab.EnumType.COBBLESTONE;
	}
	
	public static void replaceLog(World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		EnumFacing.Axis axis;
		switch (state.getValue(BlockLog.LOG_AXIS)) {
		case X:
			axis = EnumFacing.Axis.X;
			break;
		case Z:
			axis = EnumFacing.Axis.Z;
			break;
		default:
			axis = EnumFacing.Axis.Y;
			break;
		}
		world.setBlockState(pos,Ref.charwood.getDefaultState().withProperty(BlockRotatedPillar.AXIS, axis),2);
	}
	
	public static void replaceStairs(World world, BlockPos pos, Block stairblock) {
		IBlockState state = world.getBlockState(pos);
		world.setBlockState(pos, stairblock.getDefaultState()
				.withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING))
				.withProperty(BlockStairs.SHAPE, state.getValue(BlockStairs.SHAPE))
				.withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF))
		,2);
	}
	
	public static void replaceSlab(World world, BlockPos pos, Block slabblock) {
		IBlockState state = world.getBlockState(pos);
		world.setBlockState(pos, slabblock.getDefaultState()
			.withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF))
		);
	}
	
	public static void corruptionFinal(World world, NetherFairy fairy) {
		corruptionFinal(world,fairy.into,fairy.pos);
	}
	
	public static void corruptionFinal(World world, Block into, BlockPos pos) {
		switch (into.getRegistryName().toString()) {
		// Netherrack
		case "minecraft:netherrack":{
			loop:for (int x=-1; x<=1; x++)for (int z=-1; z<=1; z++){
				BlockPos pos2=pos.add(x,1,z);
				Block block = world.getBlockState(pos2).getBlock();
				if (block==Blocks.LAVA||block==Blocks.WATER||block==Ref.creepingobsidian){
					world.setBlockState(pos, Blocks.MAGMA.getDefaultState(),2);
					break loop;
				}
			}
			break;
		// Soul Sand
		}case "minecraft:soul_sand":{
			BlockPos pos2=pos.up();
			if (world.getBlockState(pos2).getBlock()==Blocks.AIR && Ref.rand.nextInt(100)<5){
				world.setBlockState(pos2, Blocks.NETHER_WART.getDefaultState(),2);
			}
			break;
		// Obsidian
		}case "creepingnether:nethercoal":
		case "creepingnether:netheriron":
		case "creepingnether:nethergold":
		case "creepingnether:netherdiamond":
		case "creepingnether:netheremerald":
		case "creepingnether:netherlapis":
		case "creepingnether:netherredstone":{
			if(Ref.rand.nextFloat()<0.1) {
				List<BlockPos> neighbors = new ArrayList<>();
				neighbors.add(pos.up());
				neighbors.add(pos.down());
				neighbors.add(pos.north());
				neighbors.add(pos.south());
				neighbors.add(pos.east());
				neighbors.add(pos.west());
				while(neighbors.size()>0) {
					BlockPos neighbor = neighbors.remove(Ref.rand.nextInt(neighbors.size()));
					Block block = world.getBlockState(neighbor).getBlock();
					if(block==Blocks.STONE || block==Blocks.NETHERRACK) {
						world.setBlockState(neighbor, into.getDefaultState(),2);
						break;
					}
				}
			}
			break;
		}default:{
			break;
		}}
	}
	
	public static void corruptEntities(World world, BlockPos pos) {
		corruptEntities(world,pos,3);
	}
	
	public static void corruptEntities(World world, BlockPos pos, double radius){
        List<EntityLiving> list = world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos.getX()-radius, pos.getY()-radius, pos.getZ()-radius, pos.getX()+1+radius, pos.getY()+1+radius, pos.getZ()+1+radius));
        int lightningCount=0;
        int x=0,y=0,z=0;
        for(EntityLiving entity : list) {
            //if(inRadius(entity.getPosition())){
            	if(corruptEntity(entity)) {
            		++lightningCount;
            		x+=entity.posX;
            		y+=entity.posY;
            		z+=entity.posZ;
            	}
            //}
        }
        if(lightningCount>0) {
        	//lightning(world, pos);
        	lightning(world,new BlockPos(x/lightningCount,y/lightningCount,z/lightningCount));
        }
	}
	
	@Nullable
	public static String getOtherEntityString (Entity entity) {
        ResourceLocation resourcelocation = EntityList.getKey(entity);
        return resourcelocation == null ? null : resourcelocation.toString();
	}
	
	public static boolean corruptEntity(EntityLiving entity){
		World world = entity.world;
		Random rand = Ref.rand;
		switch(getOtherEntityString(entity)) {
		case "minecraft:villager":{
			EntityVillager villager = (EntityVillager)entity;
			if(TransformationLists.villager_transformations.isEmpty()) {return false;}
			Transformations transformation = TransformationLists.villager_transformations.getRandom();
			if(villager.isChild() && transformation==Transformations.VILLAGER_WITCHIFY) {
				if(Options.entity_corruption.villager_zombies) {
					transformation=Transformations.VILLAGER_ZOMBIFY;
				} else { return false; }
			}
			switch(transformation) {
			case VILLAGER_WITCHIFY:
				replaceMob(entity,new EntityWitch(world));
				break;
			case VILLAGER_ZOMBIFY:
				replaceMob(entity,new EntityZombieVillager(world));
				break;
			default:
				break;
			}
			return true;
		}case "minecraft:pig":{
			if(!Options.entity_corruption.pig_zombies) {return false;}
			replaceMob(entity,new EntityPigZombie(world));
			return true;
		}case "minecraft:slime":{
			if(!Options.entity_corruption.magma_slime) {return false;}
			replaceMob(entity,new EntityMagmaCube(world));
			return true;
		}case "minecraft:cow":{
			if(entity.isDead) {return false;}
			if(Options.entity_corruption.cowsexplode) {
	        	world.createExplosion(entity, entity.posX, entity.posY, entity.posZ, 3f, world.getGameRules().getBoolean("mobGriefing"));
	        	entity.setDead();
	        	return true;
			} else if(Options.entity_corruption.cow_mooshroom) {
				replaceMob(entity,new EntityMooshroom(world));
				return true;
			}
        	return false;
		}case "minecraft:sheep":{
			EntitySheep sheep = (EntitySheep) entity;
			if(!Options.entity_corruption.sheepfire) { return false; }
			if(sheep.isBurning() || sheep.getSheared()) { return false; }
        	sheep.setFire(16);
        	if(!sheep.getSheared() && !sheep.isChild()) {
        		sheep.setSheared(true);
        		int woolcount = 1 + rand.nextInt(3);
                for (int j = 0; j < woolcount; ++j){
                    EntityItem entityitem = entity.entityDropItem(new ItemStack(
                    	Item.getItemFromBlock(Blocks.WOOL), 1,
                    	((EntitySheep)entity).getFleeceColor().getMetadata()
                    ), 1.0F);
                    entityitem.motionY += (rand.nextFloat()*0.05f);
                    entityitem.motionX += ((rand.nextFloat()-rand.nextFloat())*0.1f);
                    entityitem.motionZ += ((rand.nextFloat()-rand.nextFloat())*0.1f);
                }
        	}
        	return true;
		}case "minecraft:horse":{
			EntityHorse horse = (EntityHorse)entity;
			if(TransformationLists.horse_transformations.isEmpty()) {return false;}
			if(horse.isTame()) {return false;}
			Transformations transformation = TransformationLists.horse_transformations.getRandom();
			switch(transformation) {
			case HORSE_SKELEFY:
				replaceMob(entity,new EntitySkeletonHorse(world));
				break;
			case HORSE_ZOMBIFY:
				replaceMob(entity,new EntityZombieHorse(world));
				break;
			default:
				break;
			}
			return true;
		}case "minecraft:creeper":{
			EntityCreeper creeper = (EntityCreeper)entity;
			if(!Options.entity_corruption.creepercharge) { return false; }
			if(creeper.getPowered()) { return false; }
			creeper.onStruckByLightning(null);
			creeper.extinguish();
			creeper.heal(20);
			return true;
		}case "minecraft:skeleton":{
			//EntitySkeleton skeleton = (EntitySkeleton)entity;
			if(!Options.entity_corruption.wither_skeletons) { return false; }
			replaceMob(entity,new EntityWitherSkeleton(world));
			return true;
		}}
        return false;
	}
	
	public static void lightning(World world, BlockPos pos) {
		if(!Options.entity_corruption.lightning) { return; }
		world.addWeatherEffect(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true));
	}
	
	public static void lightning(EntityLiving mob) {
		if(!Options.entity_corruption.lightning) { return; }
		mob.world.addWeatherEffect(new EntityLightningBolt(mob.world, mob.posX, mob.posY, mob.posZ, true));
	}
	
	public static void replaceMob(EntityLiving mob1, EntityLiving mob2){
		World world = mob1.world;
        if (world.isRemote || mob1.isDead){ return; }
        mob2.setLocationAndAngles(mob1.posX, mob1.posY, mob1.posZ, mob1.rotationYaw, mob1.rotationPitch);
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
        if (mob1 instanceof EntitySlime && mob2 instanceof EntitySlime){
        	NBTTagCompound tag = new NBTTagCompound();
        	mob1.writeEntityToNBT(tag);
        	//tag.setInteger("Size", ((EntitySlime)mob1).getSlimeSize());
        	mob2.readEntityFromNBT(tag);
            mob2.setHealth(mob1.getMaxHealth());
        }
        if(mob1 instanceof EntityVillager && mob2 instanceof EntityZombieVillager) {
        	@SuppressWarnings("deprecation")
			int profession = ((EntityVillager) mob1).getProfession();
            ((EntityZombieVillager)mob2).setProfession(profession);
        }
        /*if(mob1 instanceof EntityPig && mob2 instanceof EntityPigZombie) {
        	mob2.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.AIR));
        }*/
        Iterator<ItemStack> it = mob1.getEquipmentAndArmor().iterator();
        while(it.hasNext()) {
        	ItemStack item = it.next();
        	if(item.getItem()==Items.AIR) { continue; }
        	EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(item);
        	mob2.setItemStackToSlot(slot, item);
        }
        world.spawnEntity(mob2);
        mob1.setDead();
	}

}
