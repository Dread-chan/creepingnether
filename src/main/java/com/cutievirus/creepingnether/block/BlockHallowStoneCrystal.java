package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.entity.TileEntityHallowCrystal;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHallowStoneCrystal extends BlockHallowStone{
	
	public BlockHallowStoneCrystal() {
		this("hallowstone_crystal");
	}
	
	public BlockHallowStoneCrystal(String name) {
		super(name);
		this.setLightLevel(0.8f);
		this.item.setShiny(true);
	}
	
	protected void applyAfflictions(EntityLivingBase entity) {
		applyAffliction(entity,MobEffects.SPEED,1);
		if(!entity.isBurning()) {
			if(entity.isEntityUndead()) {
				entity.setFire(20);
			}else{
				entity.setFire(1);
			}
		}
	}
	
	protected int getAfflictionDuration() {
		return 10*20+2;
	}
	
	protected double getSpeedMult() {
		return 1.4d;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state){
		return new TileEntityHallowCrystal();
	}
	
	@Override
    public boolean canEntitySpawn(IBlockState state, Entity entity) {
        return entity.isImmuneToFire();
    }
	
}
