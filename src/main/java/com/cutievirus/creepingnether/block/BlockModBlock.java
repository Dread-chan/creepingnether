package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.IModItem;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockModBlock extends Block implements IModBlock{
	
	public ModItemBlock item;
	protected MapColor blockMapColor;
	protected Material blockMaterial;
	
	public BlockModBlock(String name) 
		{ this(name,Material.ROCK); }
	public BlockModBlock(String name, Material material) 
		{ this(name,material,material.getMaterialMapColor());}
	public BlockModBlock(String name, Material material, MapColor mapColor)
		{ this(name,material,mapColor,0); }
	public BlockModBlock(String name, Material material, MapColor mapColor, int burnTime){
		super(material, mapColor);
		blockMaterial = material;
		blockMapColor = mapColor;
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);

		this.item = new ModItemBlock(this,burnTime);
		item.setRegistryName(name);
	}
	
	@Override
	public IModItem getModItem() {
		return this.item;
	}
    
    public float getHardness() {
    	return this.blockHardness;
    }
    
    public float getResistance() {
    	return this.blockResistance;
    }
    
    @Override
    public Material getMaterial(IBlockState state) {
        return blockMaterial;
    }
    public Material getMaterial() {
        return blockMaterial;
    }
    public BlockModBlock setMaterial(Material material) {
    	blockMaterial = material;
    	return this;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return blockMapColor;
    }
    public MapColor getMapColor() {
        return blockMapColor;
    }
	public BlockModBlock setMapColor(MapColor mapColor) {
		blockMapColor = mapColor;
		return this;
	}
	
	@Override
    public BlockModBlock setSoundType(SoundType sound) {
        blockSoundType = sound;
        return this;
    }
	
	public static boolean waterAdjacent(IBlockAccess  world, BlockPos pos) {
        return (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
                world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
                world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
                world.getBlockState(pos.south()).getMaterial() == Material.WATER);
	}

}
