package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Options;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCreepingObsidian extends BlockCreepingBlock{

	public BlockCreepingObsidian(){
		this("creepingobsidian");
	}
	public BlockCreepingObsidian(String name){
		super(name, Material.ROCK, MapColor.OBSIDIAN, Blocks.OBSIDIAN);
		setTickRandomly(true);

		setHardness(50.0F);
		setResistance(2000.0F);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		Block blockup = world.getBlockState(pos.up()).getBlock();
		Block blockdown = world.getBlockState(pos.down()).getBlock();
		Block blocknorth = world.getBlockState(pos.north()).getBlock();
		Block blocksouth = world.getBlockState(pos.south()).getBlock();
		Block blockeast = world.getBlockState(pos.east()).getBlock();
		Block blockwest = world.getBlockState(pos.west()).getBlock();
		if (blockup!=Blocks.WATER && blockup!=Blocks.FLOWING_WATER
		&& blockdown!=Blocks.WATER && blockdown!=Blocks.FLOWING_WATER
		&& blocknorth!=Blocks.WATER && blocknorth!=Blocks.FLOWING_WATER
		&& blocksouth!=Blocks.WATER && blocksouth!=Blocks.FLOWING_WATER
		&& blockeast!=Blocks.WATER && blockeast!=Blocks.FLOWING_WATER
		&& blockwest!=Blocks.WATER && blockwest!=Blocks.FLOWING_WATER
		){
			world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
		}
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	if(Options.creepingparticles && rand.nextFloat()<0.25) {
    		world.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+rand.nextDouble(), pos.getY()+1.1d, pos.getZ()+rand.nextDouble(), 0d, 0d, 0d, new int[0]);
    	}
    }

}
