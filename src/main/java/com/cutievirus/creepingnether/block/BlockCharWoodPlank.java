package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCharWoodPlank extends BlockModBlock{
	
	public BlockCharWoodPlank(){
		super("charwood_planks", Material.WOOD, MapColor.WOOD, 400);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.WOOD);
		setTickRandomly(true);
		setHardness(1.6F);
		setResistance(4.0F);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		Ref.charwood.updateTick(world,pos,state,rand);
	}
	
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    	Ref.charwood.addDestroyEffects(world,pos,manager);
    	return false;
    }
}
