package com.cutievirus.creepingnether.entity;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityPortal extends Entity{

	private Corruptor corruptor;
	
	public EntityPortal(World world) {
		super(world);
		corruptor = new Corruptor(world,this.getPosition());
	}
	
	public EntityPortal(World world, BlockPos pos) {
		this(world);
		this.setPosition(pos.getX(), pos.getY(), pos.getZ());
		corruptor.setPos(pos);
	}
	
	@Override
	public void onUpdate() {
		if(world.getBlockState(this.getPosition()).getBlock()!=Blocks.PORTAL) {
			this.setDead();
			return; 
		}
		corruptor.onUpdate();
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		corruptor.readNBT(compound);
		corruptor.setPos(this.getPosition());
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
        corruptor.writeNBT(compound);
	}
	
	public static void createPortal(World world, BlockPos pos) {
		if(world.getEntitiesWithinAABB(EntityPortal.class, new AxisAlignedBB(pos.getX()-1d, pos.getY()-1d, pos.getZ()-1d, pos.getX()+2d, pos.getY()+2d, pos.getZ()+2d)).size()>0){
			return;
		}
		world.spawnEntity(new EntityPortal(world,pos));
	}

}
