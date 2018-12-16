package com.cutievirus.creepingnether.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemBlock extends ItemBlock {
	
	protected int burnTime = 0;
	private boolean shiny = false;
	
	public ModItemBlock(Block block) {
		this(block,0);
	}
	
	public ModItemBlock(Block block, int burnTime) {
		super(block);
		this.burnTime=burnTime;
	}
	
	@Override
    public int getItemBurnTime(ItemStack itemStack) {
        return this.burnTime;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack){
        return shiny;
    }
    
    public void setShiny(boolean isShiny) {
    	this.shiny = isShiny;
    }

}
