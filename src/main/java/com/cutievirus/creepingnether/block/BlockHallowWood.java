package com.cutievirus.creepingnether.block;

import org.apache.commons.lang3.ArrayUtils;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.Purifier;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHallowWood extends BlockModWood{

	public BlockHallowWood(){
		this("hallowwood");
	}
	public BlockHallowWood(String name){
		super(name);
	}

	@Override
	protected void corruption(World world, BlockPos pos){
		IBlockState state=world.getBlockState(pos);
		Block block = state.getBlock();
		if(ArrayUtils.indexOf(Ref.CharwoodList, block)>=0) {
			Purifier.instance.DoCorruption(world, pos);
			Purifier.instance.corruptionFinal(world, pos);
		}
	}
}
