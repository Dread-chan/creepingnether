package com.cutievirus.creepingnether.block;


import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class BlockSoulStoneCharged extends BlockSoulStone{
	
	public BlockSoulStoneCharged(){
		super("soulstone_charged");
		this.setLightLevel(0.5f);
		this.item.setShiny(true);
	}
	
	@Override
	protected void applyAfflictions(EntityLivingBase entity) {
		applyAffliction(entity,MobEffects.SLOWNESS,2);
		applyAffliction(entity,MobEffects.WEAKNESS,1);
		applyAffliction(entity,MobEffects.WITHER,0);
		entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,80, 0, false, true));
	}
	
	@Override
	protected double getSpeedMult() {
		return 0.2D;
	}
	
	@Override
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return entity.isEntityInvulnerable(DamageSource.WITHER);
    }
	
}
