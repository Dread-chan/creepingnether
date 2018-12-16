package com.cutievirus.creepingnether.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ModItemSlab extends ItemSlab {
	
	protected int burnTime = 0;

	public ModItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
		this(block, singleSlab, doubleSlab, 0);
	}
	
	public ModItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab, int burnTime) {
		super(block, singleSlab, doubleSlab);
		this.burnTime=burnTime;
	}
	
	@Override
    public int getItemBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }

}
