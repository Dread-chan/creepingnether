package com.cutievirus.creepingnether.block;

import java.util.List;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.IModItem;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGoldLeaves extends BlockLeaves implements IModBlock {
	
	public ModItemBlock item;

	public BlockGoldLeaves(String name) {
		super();
		setCreativeTab(Ref.tabcreepingnether);
		setUnlocalizedName(name);
		setRegistryName(name);

		this.item = new ModItemBlock(this);
		item.setRegistryName(name);
		
		this.leavesFancy=true;
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(CHECK_DECAY, true)
				.withProperty(DECAYABLE, true));
	}
	
	@Override
	public IModItem getModItem() {
		return this.item;
	}
	
    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.GOLD;
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this.item));
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return BlockPlanks.EnumType.OAK;
	}
	
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }
    
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    public int getMetaFromState(IBlockState state) {
    	int i=0;
        if (!state.getValue(DECAYABLE)) {
            i |= 4;
        }
        if (state.getValue(CHECK_DECAY)) {
            i |= 8;
        }
        return i;
    }
    
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(DECAYABLE, false);
    }
	
}
