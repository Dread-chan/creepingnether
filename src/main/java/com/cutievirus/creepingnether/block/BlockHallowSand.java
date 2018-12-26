package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.IModItem;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHallowSand extends BlockFalling implements IModBlock {
	
	public ModItemBlock item;

	public BlockHallowSand(String name) {
		super();
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.SAND);
		setHardness(0.5F);
		setUnlocalizedName(name);
		setRegistryName(name);

		this.item = new ModItemBlock(this);
		item.setRegistryName(name);
	}
	
	@Override
	public IModItem getModItem() {
		return this.item;
	}
	
    @Override
    public Material getMaterial(IBlockState state) {
        return Material.SAND;
    }
	
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.SILVER_STAINED_HARDENED_CLAY;
    }

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable){
		EnumPlantType plantType = plantable.getPlantType(world, pos);
        if (super.canSustainPlant(state, world, pos, direction, plantable)) {
            return true;
        }
		switch (plantType) {
	        case Cave:
	        case Desert:
	        	return true;
            case Beach:
                return BlockModBlock.waterAdjacent(world, pos);
            default:
            	return false;
        }
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public int getDustColor(IBlockState state) {
        return 0x95835b;
    }
	
}
