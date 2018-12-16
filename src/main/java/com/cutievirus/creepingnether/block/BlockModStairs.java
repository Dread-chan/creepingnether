package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModStairs extends BlockStairs{
	
	public Item item;
	protected Block base;
	
	public BlockModStairs(Block base, String name){
		this(base,name,0);
	}
	public BlockModStairs(Block base, String name, int burnTime){
		super(base.getDefaultState());
		this.base=base;
		setUnlocalizedName(name+"_stairs");
		setRegistryName(name+"_stairs");
		
		setTickRandomly(base.getTickRandomly());
		this.setCreativeTab(Ref.tabcreepingnether);

		this.item = new ModItemBlock(this,burnTime);
		item.setRegistryName(name+"_stairs");
	}
	
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    	base.addDestroyEffects(world,pos,manager);
    	return false;
    }
	
}
