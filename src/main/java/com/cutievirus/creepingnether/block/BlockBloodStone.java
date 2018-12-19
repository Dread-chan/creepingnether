package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.Corruptor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStoneSlab;
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
		boolean corrupt = false;
		switch(block.getRegistryName().toString()) {
		case "minecraft:cobblestone":
		case "minecraft:stone_stairs":
			corrupt=true;
			break;
		case "minecraft:stone_slab":
		case "minecraft:double_stone_slab":
			if(state.getValue(BlockStoneSlab.VARIANT)==BlockStoneSlab.EnumType.COBBLESTONE) {
				corrupt=true;
			}
			break;
		}
		if(corrupt) {
			Corruptor.DoCorruption(world, pos);
		}
	}
	
}
