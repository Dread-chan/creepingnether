package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.entity.TileEntityNetherCrystal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

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
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TileEntityNetherCrystal();
	}
	
	@Override
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return entity.isEntityInvulnerable(DamageSource.WITHER);
    }
	
}
