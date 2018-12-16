package com.cutievirus.creepingnether.block;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class BlockSoulStoneCrystal extends BlockSoulStone{
	
	public BlockSoulStoneCrystal(){
		super("soulstone_crystal");
		this.setLightLevel(0.8f);
		this.item.setShiny(true);
	}
	
	@Override
	protected void applyAfflictions(EntityLivingBase entity) {
		applyAffliction(entity,MobEffects.SLOWNESS,2);
		applyAffliction(entity,MobEffects.WEAKNESS,2);
		applyAffliction(entity,MobEffects.WITHER,2);
		entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,80, 0, false, true));
		entity.setFire(6);
	}
	
	@Override
	protected double getSpeedMult() {
		return 0.2D;
	}
	
}
