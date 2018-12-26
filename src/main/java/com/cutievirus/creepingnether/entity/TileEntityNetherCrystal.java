package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class TileEntityNetherCrystal extends TileEntityAbstractCrystal {

	@Override
	public void createCorruptor() {
		corruptor = new Corruptor(world,pos);
	}
	
	@Override
	public boolean blockCanPower(BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return block==Ref.soulstone_charged;
	}
}
