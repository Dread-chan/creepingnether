package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSoulStone extends BlockModBlock{
	
	public BlockSoulStone() {
		this("soulstone");
	}
	public BlockSoulStone(String name) {
		this(name,Material.ROCK, MapColor.BROWN);
	}
	public BlockSoulStone(String name,Material material, MapColor mapColor) {
		super(name,material, mapColor);
		setCreativeTab(Ref.tabcreepingnether);
		setSoundType(SoundType.STONE);
		
		setHardness(1F);
	}
	
	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		double speedmult = getSpeedMult();
		if(world.isRemote) {
	        entity.motionX *= speedmult;
	        entity.motionZ *= speedmult;
			return;
		}
		
		if(entity instanceof EntityLivingBase) {
			applyAfflictions((EntityLivingBase) entity);
		}
		
    }
	
	protected void applyAfflictions(EntityLivingBase entity) {
		applyAffliction(entity,MobEffects.SLOWNESS,1);
		applyAffliction(entity,MobEffects.WEAKNESS,0);
	}
	
	protected int getAfflictionDuration() {
		return 30*20+2;
	}
	
	protected double getSpeedMult() {
		return 0.4D;
	}
	
	protected void applyAffliction(EntityLivingBase entity, Potion effect, int amplifier) {
		int max_dur = getAfflictionDuration();
		int dur = 0;
		PotionEffect pot = entity.getActivePotionEffect(effect);
		if(pot!=null) { dur = pot.getDuration(); }
		if(dur<max_dur) {
			entity.addPotionEffect(new PotionEffect(effect,Math.min(max_dur, dur)+2, amplifier, false, true));
		}
	}
	
}
