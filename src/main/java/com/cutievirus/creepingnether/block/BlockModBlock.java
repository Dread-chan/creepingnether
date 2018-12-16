package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockModBlock extends Block{
	
	public ModItemBlock item;
	
	public BlockModBlock(String name, Material material, MapColor mapColor){
		super(material, mapColor);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);

		this.item = new ModItemBlock(this);
		item.setRegistryName(name);
	}
	
	public BlockModBlock(String name, Material material, MapColor mapColor, int burnTime){
		super(material, mapColor);
		setUnlocalizedName(name);
		setRegistryName(name);

		this.item = new ModItemBlock(this,burnTime);
		item.setRegistryName(name);
	}
	
    public MapColor getMapColor() {
        return this.blockMapColor;
    }
    
    public Material getMaterial() {
        return this.blockMaterial;
    }
    
    public float getHardness() {
    	return this.blockHardness;
    }
    
    public float getResistance() {
    	return this.blockResistance;
    }
	
}
