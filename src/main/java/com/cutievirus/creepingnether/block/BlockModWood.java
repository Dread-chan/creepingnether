package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.IModItem;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockModWood extends BlockRotatedPillar implements IModBlock{

	public ModItemBlock item;

	public BlockModWood(String name) {
		this(name,Material.WOOD, MapColor.WOOD);
	}
	public BlockModWood(String name, Material material, MapColor mapColor){
		super(material, mapColor);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.WOOD);

		setTickRandomly(true);
		setHardness(2f);

		item = new ModItemBlock(this);
		item.setBurnCount(6);
		item.setRegistryName(name);
	}

	@Override
	public IModItem getModItem() {
		return item;
	}

    @Override public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos){
    	return true;
    }
    @Override public boolean isWood(IBlockAccess world, BlockPos pos){ return true; }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(!Options.lingeringcorruption) { return; }
		for (int x=-1;x<=1;++x)for (int y=-1;y<=1;++y)for (int z=-1;z<=1;++z) {
			if(x==0&&y==0&&z==0) { continue; }
			corruption(world,pos.add(x,y,z));
		}
	}

	protected void corruption(World world, BlockPos pos){ }

}
