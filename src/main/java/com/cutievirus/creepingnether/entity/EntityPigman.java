package com.cutievirus.creepingnether.entity;

import javax.annotation.Nullable;

import com.cutievirus.creepingnether.RandomList;
import com.cutievirus.creepingnether.Ref;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityPigman extends EntityPigZombie {

	public static final RandomList<Item> mainhandItems = new RandomList<>();
	public static final RandomList<Item> offhandItems = new RandomList<>();
	static {
		mainhandItems.addMany(Items.STICK,10)
		.addMany(Items.CARROT_ON_A_STICK, 5)
		.addMany(Items.FISHING_ROD, 5)
		.addMany(Items.BONE, 5)
		.addMany(Items.WOODEN_HOE, 20)
		.addMany(Items.WOODEN_AXE, 10)
		.addMany(Items.WOODEN_SHOVEL, 10)
		.addMany(Items.WOODEN_PICKAXE, 10)
		.addMany(Items.WOODEN_SWORD, 40)
		.addMany(Items.GOLDEN_HOE, 10)
		.addMany(Items.GOLDEN_AXE, 5)
		.addMany(Items.GOLDEN_SHOVEL, 5)
		.addMany(Items.GOLDEN_PICKAXE, 5)
		.addMany(Items.GOLDEN_SWORD, 20)
		.addMany(Items.DIAMOND_SWORD, 1)
		.addMany(Items.AIR, 10)
		;
		offhandItems.addMany(Items.AIR, 30)
		.addMany(Items.FERMENTED_SPIDER_EYE, 3)
		.addMany(Items.POISONOUS_POTATO, 3)
		.addMany(Items.GOLD_NUGGET, 3)
		.addMany(Items.SHIELD, 1)
		;
	}

	public EntityPigman(World world) {
		super(world);
		isEntityUndead();
		isImmuneToFire = false;
	}

    @Override
	public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }

    @Override
	protected boolean shouldBurnInDay() {
        return false;
    }

    private boolean immortal = false;
    private boolean actuallyNotImmortal = false;
    @Override
	public void onUpdate() {
    	immortal=true;
        super.onUpdate();
        immortal=false;
    }
    @Override
	protected void onDeathUpdate(){
    	actuallyNotImmortal=true;
    	super.onDeathUpdate();
    	actuallyNotImmortal=false;
    }
    @Override
	public void setDead() {
    	if(world.getDifficulty()==EnumDifficulty.PEACEFUL
    	&&immortal&&!actuallyNotImmortal) { return; }
        isDead = true;
    }

    @Override
	public boolean getCanSpawnHere() {
        return true;
    }
    /*
    @Override
	public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
    	if(world.getDifficulty()==EnumDifficulty.PEACEFUL) {
    		return;
    	}
        super.setRevengeTarget(livingBase);
    }
    */

    @Override
	protected void initEntityAI() {
    	 tasks.addTask(0, new EntityAISwimming(this));
    	tasks.addTask(1, new EntityAIAvoidEntity<>(this, EntityPlayer.class,
    	(com.google.common.base.Predicate<EntityPlayer>)(player)->{
    		if(world.getDifficulty()==EnumDifficulty.PEACEFUL
    		&& recentlyHit>0) {
    			return true;
    		}
    		return false;
    	}, 6.0F, 1.0D, 1.2D));
        tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, false) {
        	@Override
			public boolean shouldExecute() {
        		if(world.getDifficulty()==EnumDifficulty.PEACEFUL) { return false; }
				return super.shouldExecute();
        	}
        });
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        applyEntityAI();
    }

    @Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        equipPigman(this);
    }

    public static void equipPigman(EntityPigZombie pigman) {
    	pigman.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(mainhandItems.getRandom()));
    	pigman.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(offhandItems.getRandom()));
    	if (Ref.rand.nextInt(100)<10)
    	{pigman.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));}
    	if (Ref.rand.nextInt(100)<10)
    	{pigman.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));}
    	if (Ref.rand.nextInt(100)<10)
    	{pigman.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.LEATHER_LEGGINGS));}
    	if (Ref.rand.nextInt(100)<10)
    	{pigman.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.LEATHER_BOOTS));}
    	if (Ref.rand.nextInt(100)<10) { pigman.setCanPickUpLoot(true); }
    }

    @Override
	@Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_PIG;
    }

    @Override
	protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    @Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    @Override
	protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

}
