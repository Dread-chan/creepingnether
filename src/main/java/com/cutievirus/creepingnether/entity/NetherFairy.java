package com.cutievirus.creepingnether.entity;

import java.util.ArrayList;
import java.util.List;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class NetherFairy
{
	public BlockPos pos;
	public List<BlockPos> neighbors = new ArrayList<>();
	public Block base;
	public Block into;
	
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
		return neighbors.remove(Ref.rand.nextInt(neighbors.size()));
	}
	
	public boolean blockValid(Block block) {
		return block==base || block==into;
	}

}
