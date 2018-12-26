package com.cutievirus.creepingnether.block;

import org.apache.commons.lang3.ArrayUtils;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.Corruptor;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCharWood extends BlockModWood{

	public BlockCharWood(){
		super("charwood");
		setHardness(1.6F);
		item.setBurnCount(8);
	}

	@Override
	protected void corruption(World world, BlockPos pos){
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
			Corruptor.instance.DoCorruption(world, pos);
			Corruptor.instance.corruptionFinal(world, pos);
		}
	}

    @Override
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

    @Override public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos){
    	return false;
    }
}
