package com.cutievirus.creepingnether.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;

public class BlockHallowStone extends BlockSoulStone{
	
	public BlockHallowStone() {
		this("hallowstone");
	}
	
	public BlockHallowStone(String name) {
		super(name,Material.ROCK,MapColor.SILVER_STAINED_HARDENED_CLAY);
	}
	
	protected void applyAfflictions(EntityLivingBase entity) {
		applyAffliction(entity,MobEffects.SPEED,0);
	}
	
	protected int getAfflictionDuration() {
		return 3*20+2;
	}
	
	protected double getSpeedMult() {
		return 1.1d;
	}
	
}
