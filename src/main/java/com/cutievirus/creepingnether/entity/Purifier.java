package com.cutievirus.creepingnether.entity;

import java.util.function.Consumer;

import com.cutievirus.creepingnether.EasyMap;
import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.block.BlockHallowGrass;
import com.cutievirus.creepingnether.item.ItemEssence;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class Purifier extends CorruptorAbstract {

	public static Purifier instance = new Purifier();
	@Override
	public Purifier getInstance() {
		return instance;
	}

	public static final EasyMap<Object> corruptionMap = new EasyMap<>();
	public static final EasyMap<Corruption> corruptionSpecial = new EasyMap<>();
	public static final EasyMap<Corruption> corruptionFinal = new EasyMap<>();
	public static final EasyMap<EntityCorruption> corruptionEntities = new EasyMap<>();
	@Override
	public EasyMap<Object> getCorruptionMap(){ return corruptionMap; }
	@Override
	public EasyMap<Corruption> getCorruptionSpecial(){ return corruptionSpecial; }
	@Override
	public EasyMap<Corruption> getCorruptionFinal(){ return corruptionFinal; }
	@Override
	public EasyMap<EntityCorruption> getCorruptionEntities(){ return corruptionEntities; }

	Purifier(){ super(); }
	Purifier(World world, BlockPos pos){
		super(world,pos);
	}

	@Override
	public Biome getBiome() {
		return Ref.biomePurified;
	}

	public static void allocateMaps() {
		corruptionMap.clear();
		corruptionSpecial.clear();
		corruptionFinal.clear();
		corruptionMap.assign(new Object[][]{
			{Blocks.NETHERRACK, Ref.hallowrock},
			{Blocks.SOUL_SAND, Ref.hallowsand},
			{Blocks.MAGMA, Ref.magma_cooled},
			{Blocks.NETHER_WART, Blocks.DEADBUSH},
			{Blocks.NETHER_WART_BLOCK,(BlockForPos)(world,pos)->{
				BlockPos below = pos.down();
				IBlockState soilState = world.getBlockState(below);
				Block soil = soilState.getBlock();
				if(soil.canSustainPlant(soilState, world, pos, EnumFacing.UP, Blocks.CACTUS)) {
					return Blocks.CACTUS;
				}
				return null;
			}},

			{Ref.bloodstone, Blocks.COBBLESTONE},
			{Ref.bloodstone_stairs, Blocks.STONE_STAIRS},
			{Ref.bloodstone_slab, "minecraft:stone_slab@3"},
			{Ref.bloodstone_slab2, "minecraft:double_stone_slab@3"},

			{Ref.soulstone, Ref.hallowstone},
			{Ref.soulstone_stairs, Ref.hallowstone_stairs},
			{Ref.soulstone_slab, Ref.hallowstone_slab},
			{Ref.soulstone_slab2, Ref.hallowstone_slab2},

			{Ref.charwood, Ref.hallowwood},
			{Ref.charwood_planks, Ref.hallowwood_planks},
			{Ref.charwood_stairs, Ref.hallowwood_stairs},
			{Ref.charwood_slab, Ref.hallowwood_slab},
			{Ref.charwood_slab2, Ref.hallowwood_slab2},

			{Ref.nethercoal,Ref.hallowcoal},
			{Ref.netheriron,Ref.hallowiron},
			{Ref.nethergold, Ref.hallowgold},
			{Ref.netherdiamond, Ref.hallowdiamond},
			{Ref.netheremerald, Ref.hallowemerald},
			{Ref.netherlapis, Ref.hallowlapis},
			{Ref.netherredstone, Ref.hallowredstone},

			{Blocks.LAVA, Ref.drippyobsidian},
			{Ref.creepingobsidian, Ref.drippyobsidian},
		})
		;
		corruptionFinal.add(Ref.hallowrock, (world,pos)->{
			BlockPos above=pos.up();
			int skylight = world.getLightFor(EnumSkyBlock.SKY, above);
			int blocklight = world.getLightFor(EnumSkyBlock.BLOCK, above);
			if (BlockHallowGrass.isBlockClear(world,above)
			&& skylight>14||blocklight>11){
				world.setBlockState(pos,Ref.hallowgrass.getDefaultState(),2);
				if(rand.nextDouble()<0.05) {
					world.setBlockState(above, Ref.netherlight_rose.getDefaultState(), 2);
				}
			}
		})
		.add(Ref.hallowwood, (world,pos)->{
			if(world.getBlockState(pos.up()).getBlock()!=Blocks.AIR) { return; }
			Consumer<BlockPos> placeLeaf = leafpos->{
				IBlockState state = world.getBlockState(leafpos);
				if(state.getBlock().canBeReplacedByLeaves(state, world, leafpos)) {
					world.setBlockState(leafpos, Ref.goldleaves.getDefaultState());
				}
			};
			for(int x=-3;x<=3;++x)for(int y=-2;y<=3;++y)for(int z=-3;z<=3;++z) {
				BlockPos firepos = pos.add(x,y,z);
				if(world.getBlockState(firepos).getBlock()==Blocks.FIRE) {
					world.setBlockToAir(firepos);
				}
			}
			for(int x=-1;x<=1;++x)for(int y=1;y<=2;++y)for(int z=-1;z<=1;++z) {
				BlockPos leafpos = pos.add(x,y,z);
				if(Math.abs(x)==1&&Math.abs(z)==1) { continue; }
				placeLeaf.accept(leafpos);
			}
			for(int x=-2;x<=2;++x)for(int y=-1;y<=0;++y)for(int z=-2;z<=2;++z) {
				BlockPos leafpos = pos.add(x,y,z);
				if(Math.abs(x)==2&&Math.abs(z)==2) { continue; }
				placeLeaf.accept(leafpos);
			}
		})
		;
		corruptionEntities.add("minecraft:witch", entity->{
			if(!Options.entity_corruption.villager_witches) { return false; }
			EntityWitch witch = (EntityWitch)entity;
			EntityVillager villager = new EntityVillager(entity.world);
			copyMobData(witch,villager);
			replaceMob(witch,villager);
			return false;
		})
		.add("minecraft:zombie_villager", entity->{
			if(!Options.entity_corruption.villager_zombies) { return false; }
			EntityZombieVillager zombie = (EntityZombieVillager)entity;
			EntityVillager villager = new EntityVillager(entity.world);
			copyMobData(zombie,villager);
			replaceMob(zombie,villager);
			return false;
		})
		.add("minecraft:magma_cube", entity->{
			if(!Options.entity_corruption.magma_slime) { return false; }
			EntityMagmaCube magmacube = (EntityMagmaCube)entity;
			EntitySlime slime = new EntitySlime(entity.world);
			copyMobData(magmacube,slime);
            //slime.setHealth(magmacube.getMaxHealth());
			replaceMob(magmacube,slime);
			return false;
		})
		.add("minecraft:skeleton_horse", entity->{
			if(!Options.entity_corruption.horse_skeletons) { return false; }
			EntitySkeletonHorse skelly = (EntitySkeletonHorse)entity;
			if(skelly.isTame()&&!ItemEssence.using) {return false;}
			EntityHorse horse = new EntityHorse(entity.world);
			copyMobData(skelly,horse);
			replaceMob(skelly,horse);
			return false;
		})
		.add("minecraft:zombie_horse", entity->{
			if(!Options.entity_corruption.horse_zombies) { return false; }
			EntityZombieHorse zombie = (EntityZombieHorse)entity;
			if(zombie.isTame()&&!ItemEssence.using) {return false;}
			EntityHorse horse = new EntityHorse(entity.world);
			copyMobData(zombie,horse);
			replaceMob(zombie,horse);
			return false;
		})
		.add("minecraft:zombie_pigman", entity->{
			EntityPigman pigman = new EntityPigman(entity.world);
			copyMobData(entity,pigman);
			replaceMob(entity,pigman);
			return false;
		})
		;
		// config settings
		corruptionMap.assign(Options.customPurification.toHallowrock,Ref.hallowrock)
		.assign(Options.customPurification.toHallowsand,Ref.hallowsand)
		.assign(Options.customPurification.toHallowstone,Ref.hallowstone)
		.assign(Options.customPurification.toHallowstone_slab,Ref.hallowstone_slab)
		.assign(Options.customPurification.toHallowstone_stairs,Ref.hallowstone_stairs)
		.assign(Options.customPurification.toHallowwood,Ref.hallowwood)
		.assign(Options.customPurification.toHallowwood_planks,Ref.hallowwood_planks)
		.assign(Options.customPurification.toHallowwood_slab,Ref.hallowwood_slab)
		.assign(Options.customPurification.toHallowwood_stairs,Ref.hallowwood_stairs)
		;
		loadCustomCorruption(Options.customPurification.toCustom, corruptionMap);
	}

}
