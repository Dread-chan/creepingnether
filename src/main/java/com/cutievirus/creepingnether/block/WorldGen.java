package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {
	
	private WorldGenerator nethercoal, netheriron, nethergold, netherdiamond
	, netheremerald, netherlapis, netherredstone;
	
	public WorldGen(){
		Predicate<IBlockState> substrate = BlockMatcher.forBlock(Blocks.NETHERRACK);
		nethercoal = new WorldGenMinable(Ref.nethercoal.getDefaultState(), 32, substrate);
		netheriron = new WorldGenMinable(Ref.netheriron.getDefaultState(), 16, substrate);
		nethergold = new WorldGenMinable(Ref.nethergold.getDefaultState(), 12, substrate);
		netherdiamond = new WorldGenMinable(Ref.netherdiamond.getDefaultState(), 12, substrate);
		netheremerald = new WorldGenMinable(Ref.netheremerald.getDefaultState(), 4, substrate);
		netherlapis = new WorldGenMinable(Ref.netherlapis.getDefaultState(), 10, substrate);
		netherredstone = new WorldGenMinable(Ref.netherredstone.getDefaultState().withProperty(BlockNetherRedstone.VARIANT, BlockNetherRedstone.EnumType.UNLIT), 16, substrate);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimensionType()!=DimensionType.NETHER){ return; }
		if (Options.nether_ores.nethercoal){
			generateOre(nethercoal,world,rand,chunkX,chunkZ, 6, 32,96);
		}
		if (Options.nether_ores.netheriron){
			generateOre(netheriron,world,rand,chunkX,chunkZ, 10, 16,120);
		}
		if (Options.nether_ores.nethergold){
			generateOre(nethergold,world,rand,chunkX,chunkZ, 8, 5,122);
		}
		if (Options.nether_ores.netherdiamond){
			if (rand.nextBoolean()){
				generateOre(netherdiamond,world,rand,chunkX,chunkZ, 1, 5,16);
			}else{
				generateOre(netherdiamond,world,rand,chunkX,chunkZ, 1, 106,122);
			}
		}
		if (Options.nether_ores.netheremerald){
			generateOre(netheremerald,world,rand,chunkX,chunkZ, 2, 5,16);
			generateOre(netheremerald,world,rand,chunkX,chunkZ, 3, 106,122);
		}
		if (Options.nether_ores.netherlapis){
			generateOre(netherlapis,world,rand,chunkX,chunkZ, 3, 15,30);
			generateOre(netherlapis,world,rand,chunkX,chunkZ, 6, 64,112);
		}
		if (Options.nether_ores.netherredstone){
			generateOre(netherredstone,world,rand,chunkX,chunkZ, 14, 15,120);
		}

	}
	
	private void generateOre(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, int tries, int minY, int maxY){
		for (int i=0; i<tries; i++){
			generator.generate(world, rand, new BlockPos(
				chunkX*16+rand.nextInt(16),
				minY + rand.nextInt(1+maxY - minY),
				chunkZ*16+rand.nextInt(16)
			));
		}
	}

}
