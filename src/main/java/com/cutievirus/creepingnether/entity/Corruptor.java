package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.RandomList;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.Options.TransformationLists;
import com.cutievirus.creepingnether.Options.TransformationLists.Transformations;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Corruptor extends CorruptorAbstract {
	
	Corruptor(World world, BlockPos pos){
		super(world,pos);
	}
	
	public static void allocateMaps() {
		corruptionMap.clear();
		corruptionSpecial.clear();
		corruptionFinal.clear();
		corruptionMap.add(Blocks.SNOW_LAYER, Blocks.AIR)
		.assign(new Object[]{
				Blocks.DIRT,
				Blocks.MYCELIUM,
				Blocks.GRASS,
				Blocks.STONE
		}, Blocks.NETHERRACK)
		.assign(new Object[]{
				Blocks.SAND,
				Blocks.CLAY,
				Blocks.FARMLAND,
				Blocks.GRASS_PATH
		}, Blocks.SOUL_SAND)
		
		.assign(new Object[]{
				Blocks.COBBLESTONE,
				Blocks.MOSSY_COBBLESTONE
		}, Ref.bloodstone)
		.add(Blocks.STONE_STAIRS, Ref.bloodstone_stairs)
		.assign(new Object[]{
				Blocks.STONE_SLAB
		}, (BlockForMeta)data->
		data==3 ? Ref.bloodstone_slab : data==1 ? Ref.soulstone_slab : null)
		.assign(new Object[]{
				Blocks.DOUBLE_STONE_SLAB
		}, (BlockForMeta)data->
		data==3 ? Ref.bloodstone_slab2 : data==1 ? Ref.soulstone_slab2 : null)
		
		.assign(new Object[]{
				Blocks.LOG,
				Blocks.LOG2
		}, Ref.charwood)
		.assign(new Object[]{
				Blocks.PLANKS
		}, Ref.charwood_planks)
		.assign(new Object[]{
				Blocks.OAK_STAIRS,
				Blocks.SPRUCE_STAIRS,
				Blocks.BIRCH_STAIRS,
				Blocks.JUNGLE_STAIRS,
				Blocks.ACACIA_STAIRS,
				Blocks.DARK_OAK_STAIRS
		}, Ref.charwood_stairs)
		.assign(new Object[]{
				Blocks.WOODEN_SLAB
		}, Ref.charwood_slab)
		.assign(new Object[]{
				Blocks.DOUBLE_WOODEN_SLAB
		}, Ref.charwood_slab2)
		
		.assign(new Object[]{
				Blocks.SANDSTONE,
				Blocks.RED_SANDSTONE,
				Blocks.HARDENED_CLAY,
				Blocks.STAINED_HARDENED_CLAY
		}, Ref.soulstone)
		.assign(new Object[]{
				Blocks.SANDSTONE_STAIRS,
				Blocks.RED_SANDSTONE_STAIRS
		}, Ref.soulstone_stairs)
		.assign(new Object[]{
				Blocks.STONE_SLAB2
		}, Ref.soulstone_slab)
		.assign(new Object[]{
				Blocks.DOUBLE_STONE_SLAB2
		}, Ref.soulstone_slab2)
		
		.assign(new Object[]{
				Blocks.WATER,
				Blocks.ICE,
		}, Ref.creepingobsidian)
		.assign(new Object[]{
				Blocks.PACKED_ICE
		}, Blocks.OBSIDIAN)
		
		.add(Blocks.COAL_ORE, Ref.nethercoal)
		.add(Blocks.IRON_ORE, Ref.netheriron)
		.add(Blocks.GOLD_ORE, Ref.nethergold)
		.add(Blocks.DIAMOND_ORE, Ref.netherdiamond)
		.add(Blocks.EMERALD_ORE, Ref.netheremerald)
		.add(Blocks.LAPIS_ORE, Ref.netherlapis)
		.assign(new Object[]{
				Blocks.REDSTONE_ORE,
				Blocks.LIT_REDSTONE_ORE
		}, Ref.netherredstone)
		
		.add(Blocks.CACTUS, Blocks.NETHER_WART_BLOCK)
		;
		corruptionSpecial.assign(new Object[]{
				Blocks.LEAVES,
				Blocks.LEAVES2
		},(world,pos)->{
			if(world.getBlockState(pos.up()).getBlock()==Blocks.AIR) {
				setFire(world, pos.up());
			}else if(world.getBlockState(pos.north()).getBlock()==Blocks.AIR
			||world.getBlockState(pos.south()).getBlock()==Blocks.AIR
			||world.getBlockState(pos.east()).getBlock()==Blocks.AIR
			||world.getBlockState(pos.west()).getBlock()==Blocks.AIR){
				if(world.getBlockState(pos.north()).getBlock()==Blocks.AIR)
				{ setFire(world, pos.north()); }
				if(world.getBlockState(pos.south()).getBlock()==Blocks.AIR)
				{ setFire(world, pos.south()); }
				if(world.getBlockState(pos.east()).getBlock()==Blocks.AIR)
				{ setFire(world, pos.east()); }
				if(world.getBlockState(pos.west()).getBlock()==Blocks.AIR)
				{ setFire(world, pos.west()); }
			}else if(world.getBlockState(pos.down()).getBlock()==Blocks.AIR) {
				setFire(world, pos.down());
			}
		})
		.assign(new Object[]{
				Blocks.TALLGRASS,
				Blocks.DOUBLE_PLANT
		},(world,pos)->{
			if(rand.nextFloat()<0.5) {
				setFire(world, pos);
			}else {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(),2);
			}
		})
		.assign(new Object[]{
				Blocks.DEADBUSH,
				Blocks.WHEAT,
				Blocks.CARROTS,
				Blocks.POTATOES,
				Blocks.BEETROOTS
		},(world,pos)->{
			world.setBlockState(pos,Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, 3),2);
		})
		;
		corruptionFinal.add(Blocks.SOUL_SAND, (world,pos)->{
			BlockPos above=pos.up();
			if (world.getBlockState(above).getBlock()==Blocks.AIR && rand.nextInt(100)<5){
				world.setBlockState(above, Blocks.NETHER_WART.getDefaultState(),2);
			}
		})
		.add(Blocks.NETHERRACK, (world,pos)->{
			loop:for (int x=-1; x<=1; x++)for (int z=-1; z<=1; z++){
				BlockPos pos2=pos.add(x,1,z);
				Block block = world.getBlockState(pos2).getBlock();
				if (block==Blocks.LAVA||block==Blocks.WATER||block==Ref.creepingobsidian){
					world.setBlockState(pos, Blocks.MAGMA.getDefaultState(),2);
					break loop;
				}
			}
		})
		.assign(new Object[] {
				Ref.nethercoal,
				Ref.netheriron,
				Ref.nethergold,
				Ref.netherdiamond,
				Ref.netheremerald,
				Ref.netherlapis,
				Ref.netherredstone
		}, (world,pos)->{
			if(rand.nextFloat()<0.9) { return; }
			RandomList<BlockPos> neighbors = new RandomList<BlockPos>();
			neighbors.addAll(pos.up(),pos.down(),pos.north(),pos.south(),pos.east(),pos.west());
			while(neighbors.size()>0) {
				BlockPos neighbor = neighbors.removeRandom();
				Block block = world.getBlockState(neighbor).getBlock();
				if(block==Blocks.STONE || block==Blocks.NETHERRACK) {
					world.setBlockState(neighbor, world.getBlockState(pos),2);
					break;
				}
			}
		})
		;
		corruptionEntities.add("minecraft:villager", entity->{
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
				replaceMob(villager,new EntityWitch(entity.world));
				break;
			case VILLAGER_ZOMBIFY:
				EntityZombieVillager zombie = new EntityZombieVillager(entity.world);
	        	@SuppressWarnings("deprecation")
				int profession = villager.getProfession();
	            zombie.setProfession(profession);
				replaceMob(villager,zombie);
				break;
			default: break;
			}
			return true;
		})
		.add("minecraft:pig", entity->{
			if(!Options.entity_corruption.pig_zombies) {return false;}
			EntityPigZombie pigman = new EntityPigZombie(entity.world);
			replaceMob(entity,pigman);
			return true;
		})
		.add("minecraft:slime", entity->{
			if(!Options.entity_corruption.magma_slime) {return false;}
			EntitySlime slime = (EntitySlime)entity;
			EntityMagmaCube magmacube = new EntityMagmaCube(entity.world);
        	NBTTagCompound tag = new NBTTagCompound();
        	slime.writeEntityToNBT(tag);
        	magmacube.readEntityFromNBT(tag);
            magmacube.setHealth(slime.getMaxHealth());
			replaceMob(slime,magmacube);
			return true;
		})
		.add("minecraft:cow", entity->{
			if(Options.entity_corruption.cowsexplode) {
	        	entity.world.createExplosion(entity,
	        		entity.posX, entity.posY, entity.posZ, 3f,
	        		entity.world.getGameRules().getBoolean("mobGriefing"));
	        	entity.setDead();
	        	return true;
			} else if(Options.entity_corruption.cow_mooshroom) {
				replaceMob(entity,new EntityMooshroom(entity.world));
				return true;
			}
        	return false;
		})
		.add("minecraft:sheep", entity->{
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
		})
		.add("minecraft:horse", entity->{
			EntityHorse horse = (EntityHorse)entity;
			if(horse.isTame()) {return false;}
			if(TransformationLists.horse_transformations.isEmpty()) {return false;}
			Transformations transformation = TransformationLists.horse_transformations.getRandom();
			switch(transformation) {
			case HORSE_SKELEFY:
				replaceMob(horse,new EntitySkeletonHorse(entity.world));
				break;
			case HORSE_ZOMBIFY:
				replaceMob(horse,new EntityZombieHorse(entity.world));
				break;
			default: break;
			}
			return true;
		})
		.add("minecraft:creeper", entity->{
			EntityCreeper creeper = (EntityCreeper)entity;
			if(!Options.entity_corruption.creepercharge) { return false; }
			if(creeper.getPowered()) { return false; }
			creeper.onStruckByLightning(null);
			creeper.extinguish();
			creeper.heal(20);
			return true;
		})
		.add("minecraft:skeleton", entity->{
			if(!Options.entity_corruption.wither_skeletons) { return false; }
			replaceMob(entity,new EntityWitherSkeleton(entity.world));
			return true;
		})
		;
	}

}
