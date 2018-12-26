package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Options;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCreepingObsidianWatery extends BlockCreepingObsidian{

	public BlockCreepingObsidianWatery(){
		super("drippyobsidian");
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
		if (blockup!=Blocks.LAVA && blockup!=Blocks.FLOWING_LAVA
		&& blockdown!=Blocks.LAVA && blockdown!=Blocks.FLOWING_LAVA
		&& blocknorth!=Blocks.LAVA && blocknorth!=Blocks.FLOWING_LAVA
		&& blocksouth!=Blocks.LAVA && blocksouth!=Blocks.FLOWING_LAVA
		&& blockeast!=Blocks.LAVA && blockeast!=Blocks.FLOWING_LAVA
		&& blockwest!=Blocks.LAVA && blockwest!=Blocks.FLOWING_LAVA
		){
			world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());
		}
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
    	if(Options.creepingparticles && rand.nextFloat()<0.25) {
    		world.spawnParticle(EnumParticleTypes.WATER_SPLASH, pos.getX()+rand.nextDouble(), pos.getY()+1.1d, pos.getZ()+rand.nextDouble(), 0d, 0d, 0d, new int[0]);
    	}
    }

}
