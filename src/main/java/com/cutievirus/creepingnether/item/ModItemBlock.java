package com.cutievirus.creepingnether.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItemBlock extends ItemBlock implements IModItem {
	
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
	public int getBurnTime() {
		return burnTime;
	}
	
	@Override
	public ModItemBlock setBurnTime(int time) {
		this.burnTime=time;
		return this;
	}
	@Override
	public ModItemBlock setBurnCount(float count) {
		this.burnTime=(int) (count*200);
		return this;
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
