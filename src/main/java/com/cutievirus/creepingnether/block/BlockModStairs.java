package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.IModItem;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModStairs extends BlockStairs implements IModBlock{

	public ModItemBlock item;
	protected Block base;

	public BlockModStairs(Block base, String name){
		this(base,name,
			base instanceof IModBlock ? ((IModBlock)base).getModItem().getBurnTime() : 0
		);
	}
	public BlockModStairs(Block base, String name, int burnTime){
		super(base.getDefaultState());
		this.base=base;
		setUnlocalizedName(name+"_stairs");
		setRegistryName(name+"_stairs");

		setTickRandomly(base.getTickRandomly());
		setCreativeTab(Ref.tabcreepingnether);

		item = new ModItemBlock(this,burnTime);
		item.setRegistryName(name+"_stairs");
	}

	@Override
	public IModItem getModItem() {
		return item;
	}

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        base.updateTick(worldIn, pos, state, rand);
    }

	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		base.onEntityWalk(world, pos, entity);
    }

	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity){
    	base.onEntityCollidedWithBlock(world, pos, state, entity);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    	base.addDestroyEffects(world,pos,manager);
    	return false;
    }

}
