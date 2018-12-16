package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.EntityPortal;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBloodStone extends BlockModBlock{
	
	public BlockBloodStone(){
		super("bloodstone", Material.ROCK, MapColor.STONE);
		setTickRandomly(true);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.STONE);
		
		setHardness(2.0F);
		setResistance(10.0F);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if(!Options.lingeringcorruption) { return; }
		corruption(world,pos.add(rand.nextInt(3)-1,rand.nextInt(3)-1,rand.nextInt(3)-1));
	}
	
	private void corruption(World world, BlockPos pos){
		IBlockState state=world.getBlockState(pos);
		Block block = state.getBlock();
		switch(block.getRegistryName().toString()) {
		case "minecraft:cobblestone":
			world.setBlockState(pos, getDefaultState(),2);
			break;
		case "minecraft:stone_slab":
			if(EntityPortal.isCobbleSlab(block,state)) {
				EntityPortal.replaceSlab(world,pos,Ref.bloodstone_slab);
			}
			break;
		case "minecraft:double_stone_slab":
			if(EntityPortal.isCobbleSlab(block,state)) {
				world.setBlockState(pos, Ref.bloodstone_slab2.getDefaultState(),2);
			}
			break;
		case "minecraft:stone_stairs":
			EntityPortal.replaceStairs(world,pos,Ref.bloodstone_stairs);
			break;
		}
	}
	
}
