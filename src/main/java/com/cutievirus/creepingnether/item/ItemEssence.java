package com.cutievirus.creepingnether.item;

import java.util.HashMap;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.EntityPortal;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemEssence extends ItemSimpleFoiled {
	
	protected SoundEvent sound;
	
	public ItemEssence(String name) {
		this(name, SoundEvents.ENTITY_ZOMBIE_INFECT);
	}
	
	public ItemEssence(String name, SoundEvent sound) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);
		this.sound=sound;
	}
	
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	boolean transformed = transformBlock(world,pos);
    	if(!transformed) { return EnumActionResult.FAIL; }
    	for (int x=-1; x<=1; x++)for (int y=-1; y<=1; y++)for (int z=-1; z<=1; z++) {
    		if(x==0&&y==0&&z==0) { continue; }
    		transformBlock(world,pos.add(x, y, z));
    	}
    	ItemStack itemstack = player.getHeldItem(hand);
    	itemstack.shrink(1);
    	world.playSound((EntityPlayer)null, pos, this.sound, SoundCategory.BLOCKS, 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
    	return EnumActionResult.SUCCESS;
    }
    
    
    protected boolean transformBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        HashMap<Block,Block> corruptionMap = getCorruptionMap();
        boolean isSuccess = corruptionSpecial(world, pos);
		if (corruptionMap.containsKey(block)) {
			isSuccess=true;
			Block into = corruptionMap.get(block);
			if(world.getBlockState(pos).getBlock()!=into) {
				world.setBlockState(pos, into.getDefaultState(),2);
			}
			corruptBiome(world,pos);
			corruptionFinal(world,pos);
		}
		if(isSuccess) {
			EntityPortal.corruptEntities(world, pos);
		}
    	return isSuccess;
    }
    
    protected HashMap<Block,Block> getCorruptionMap(){
    	return EntityPortal.corruptionMap;
    }
    protected boolean corruptionSpecial(World world, BlockPos pos) {
    	return EntityPortal.corruptionSpecial(world, pos);
    }
    protected void corruptBiome(World world, BlockPos pos) {
    	EntityPortal.corruptBiome(world, pos);
    }
    protected void corruptEntities(World world, BlockPos pos) {
    	EntityPortal.corruptEntities(world, pos, 1d);
    }
    protected void corruptionFinal(World world, BlockPos pos) {
    	EntityPortal.corruptionFinal(world, world.getBlockState(pos).getBlock(), pos);
    }

}
