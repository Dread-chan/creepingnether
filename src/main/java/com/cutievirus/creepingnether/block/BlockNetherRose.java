package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;

public class BlockNetherRose extends BlockBush implements IModBlock {
	
	public ModItemBlock item;
	
	public BlockNetherRose(){
		this("netherlight_rose");
	}
	
	public BlockNetherRose(String name){
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.PLANT);
		setLightLevel(1f);
		
		this.item = new ModItemBlock(this,0);
		item.setRegistryName(name);
	}

	@Override
	public ModItemBlock getModItem() {
		return this.item;
	}

    protected boolean canSustainBush(IBlockState state) {
        Block block = state.getBlock();
        @SuppressWarnings("deprecation")
		Material material = block.getMaterial(state);
        @SuppressWarnings("deprecation")
		boolean topSolid = block.isTopSolid(state);
        if( topSolid
        &&(material==Material.GRASS
        || material==Material.SAND
        || material==Material.ROCK
        || material==Material.GROUND
        || material==Material.CLAY)) { return true; }
        return false;
    }
    
    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Plains;
    }
    
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }
    
    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.3d, 0.0d, 0.3d, 0.7d, 0.75d, 0.7d);
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return AABB.offset(state.getOffset(world, pos));
    }

}
