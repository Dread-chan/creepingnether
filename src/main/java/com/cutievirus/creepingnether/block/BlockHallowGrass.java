package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHallowGrass extends BlockModBlock implements IGrowable {
	
	public BlockHallowGrass(String name) {
		super(name,Material.ROCK,MapColor.GRASS);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.STONE);
		setTickRandomly(true);
		
		setHardness(1.0F);
		setResistance(5.0F);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable){
		EnumPlantType plantType = plantable.getPlantType(world, pos);
        if (super.canSustainPlant(state, world, pos, direction, plantable)) {
            return true;
        }
		switch (plantType) {
	        case Cave:
	        case Plains:
	        	return true;
            case Beach:
                return (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
		                world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
		                world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
		                world.getBlockState(pos.south()).getMaterial() == Material.WATER);
            default:
            	return false;
        }
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(world.isRemote) { return; }
		if (!world.isAreaLoaded(pos, 3)) return;
        if (world.getLightFromNeighbors(pos.up()) < 4 && !isBlockClear(world,pos.up())) {
            world.setBlockState(pos, Ref.hallowrock.getDefaultState());
        }
		if (world.getLightFromNeighbors(pos.up()) >= 9)
		for (int i = 0; i < 4; ++i) {
			BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
			BlockPos above = blockpos.up();
			if(world.getBlockState(blockpos).getBlock()==Ref.hallowrock
			&& isBlockClear(world,above)) {
				world.setBlockState(blockpos, state);
			}
		}
	}
	
	public static boolean isBlockClear(World world, BlockPos pos) {
		return world.getBlockState(pos).getLightOpacity(world,pos)<=2;
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    	return Ref.hallowrock.getItemDropped(Ref.hallowrock.getDefaultState(), rand, fortune);
    }

	@Override
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state) {
		return true;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if(world.getBlockState(pos.up()).getBlock()==Blocks.AIR) {
			world.setBlockState(pos.up(), Ref.netherlight_rose.getDefaultState());
		}
	}
	
}
