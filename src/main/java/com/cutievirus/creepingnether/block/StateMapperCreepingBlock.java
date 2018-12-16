package com.cutievirus.creepingnether.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;

public class StateMapperCreepingBlock extends StateMapperBase{
    protected ModelResourceLocation getModelResourceLocation(IBlockState state)
    {
    	String location = ((BlockCreepingBlock)state.getBlock()).base.getRegistryName().toString();
    	return new ModelResourceLocation(new ResourceLocation(location), "normal");
    }
}
