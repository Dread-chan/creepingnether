package com.cutievirus.creepingnether.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockCreepingBlock extends Block {

	protected final Block base;
	protected Block drop;
	public Item item;
	public String itemModel;
	
	public BlockCreepingBlock(String name, Material material, MapColor mapcolor, Block base){
		super(material, mapcolor);
		setUnlocalizedName(name);
		setRegistryName(name);

		this.base=base;
		this.drop=base;
		this.itemModel=Item.getItemFromBlock(base).getRegistryName().toString();
		this.item = new ItemBlock(this);
		item.setRegistryName(name);
	}
	
	@Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
		if(player.isCreative()) {
			return new ItemStack(this.item, 1, 0);
		}
        return new ItemStack(Item.getItemFromBlock(base), 1, 0);
    }
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	return Item.getItemFromBlock(drop);
    }
	
    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        Item item = Item.getItemFromBlock(base);
        return new ItemStack(item, 1, 0);
    }
    
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return false;
    }
	
	public BlockCreepingBlock setDrop(Block block)
	{
		this.drop=block;
		return this;
	}
	
	public BlockCreepingBlock setItemModel(String string)
	{
		this.itemModel=string;
		return this;
	}
	
}
