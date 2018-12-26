package com.cutievirus.creepingnether.entity;

import java.util.Random;

import com.cutievirus.creepingnether.RandomList;
import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class NetherFairy
{
	public BlockPos pos;
	public RandomList<BlockPos> neighbors = new RandomList<>();
	public Block base;
	public Block into;
	
	private static Random rand = Ref.rand;
	
	public NetherFairy(BlockPos pos, Block base, Block into) {
		this.pos = pos;
		neighbors.add(pos.up());
		neighbors.add(pos.down());
		neighbors.add(pos.north());
		neighbors.add(pos.south());
		neighbors.add(pos.east());
		neighbors.add(pos.west());
		this.base = base;
		this.into = into;
	}
	
	public BlockPos getNeighbor() {
		BlockPos neighbor = neighbors.removeRandom();
		while(neighbor.equals(pos.down()) && rand.nextDouble()<0.75 && neighbors.size()>0) {
			neighbors.add(neighbor);
			neighbor = neighbors.removeRandom();
		}
		return neighbor;
	}
	
	public boolean blockValid(Block block) {
		return block==base || block==into;
	}

}
