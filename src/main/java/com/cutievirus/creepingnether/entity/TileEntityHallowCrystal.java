package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class TileEntityHallowCrystal extends TileEntityAbstractCrystal {
	
	@Override
	public void createCorruptor() {
		corruptor = new Purifier(world,pos);
	}
	
	@Override
	public boolean blockCanPower(BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		return block==Ref.hallowstone_charged;
	}
}
