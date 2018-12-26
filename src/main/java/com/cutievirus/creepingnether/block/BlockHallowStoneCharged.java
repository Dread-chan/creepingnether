package com.cutievirus.creepingnether.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;

public class BlockHallowStoneCharged extends BlockHallowStone{
	
	public BlockHallowStoneCharged() {
		this("hallowstone_charged");
	}
	
	public BlockHallowStoneCharged(String name) {
		super(name);
		this.setLightLevel(0.5f);
		this.item.setShiny(true);
	}
	
	protected void applyAfflictions(EntityLivingBase entity) {
		applyAffliction(entity,MobEffects.SPEED,0);
		if(entity.isEntityUndead() && !entity.isBurning()) {
			entity.setFire(10);
		}
	}
	
	protected int getAfflictionDuration() {
		return 10*20+2;
	}
	
	protected double getSpeedMult() {
		return 1.3d;
	}
	
	@Override
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
		if(entity instanceof EntityLivingBase) {
			return !((EntityLivingBase)entity).isEntityUndead()||entity.isImmuneToFire();
		}
		return false;
    }
	
}
