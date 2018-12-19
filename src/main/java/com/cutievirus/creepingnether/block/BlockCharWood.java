package com.cutievirus.creepingnether.block;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.Corruptor;
import com.cutievirus.creepingnether.item.ModItemBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCharWood extends BlockRotatedPillar{
	
	public Item item;
	
	public BlockCharWood(){
		super(Material.WOOD, MapColor.WOOD);
		setUnlocalizedName("charwood");
		setRegistryName("charwood");
		setTickRandomly(true);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.WOOD);
		
		setHardness(1.6F);
		
		this.item = new ModItemBlock(this,1600);
		item.setRegistryName("charwood");
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(!Options.lingeringcorruption) { return; }
		corruption(world,pos.add(rand.nextInt(3)-1,rand.nextInt(2)-1,rand.nextInt(3)-1));
		corruption(world,pos.add(rand.nextInt(3)-1,1,rand.nextInt(3)-1));
		corruption(world,pos.up());
		corruption(world,pos.down());
	}
	
	private void corruption(World world, BlockPos pos){
		IBlockState state=world.getBlockState(pos);
		Block block = state.getBlock();
		boolean corrupt = false;
		switch(block.getRegistryName().toString()) {
		case "minecraft:log":
		case "minecraft:log2":
		case "minecraft:planks":
		case "minecraft:oak_stairs":
		case "minecraft:spruce_stairs":
		case "minecraft:birch_stairs":
		case "minecraft:jungle_stairs":
		case "minecraft:acacia_stairs":
		case "minecraft:dark_oak_stairs":
		case "minecraft:wooden_slab":
		case "minecraft:double_wooden_slab":
			corrupt=true;
			break;
		}
		if(corrupt) {
			Corruptor.DoCorruption(world, pos);
		}
	}
	
	/*
	@Override
    public int quantityDropped(Random rand) {
        return rand.nextInt(2)+3;
    }
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Items.COAL;
    }
	@Override
    public int damageDropped(IBlockState state) {
        return 1;
    }

	@Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this.item, 1, 0);
    }
	*/
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    	IBlockState state = world.getBlockState(pos);
    	Block block = state.getBlock();
        if(ArrayUtils.indexOf(Ref.CharwoodList, block)<0) { return false; }
        @SuppressWarnings("deprecation")
		AxisAlignedBB bbox = block.getBoundingBox(state, world, pos);
        for (int i=0;i<8;++i) {
        	world.spawnParticle(EnumParticleTypes.FLAME, pos.getX()+bbox.minX+Ref.rand.nextDouble()*(bbox.maxX-bbox.minX), pos.getY()+bbox.minY+Ref.rand.nextDouble()*(bbox.maxY-bbox.minY), pos.getZ()+bbox.minZ+Ref.rand.nextDouble()*(bbox.maxZ-bbox.minZ), 0d, 0d, 0d, new int[0]);
        }
    	return false;
    }
    

}
