package com.cutievirus.creepingnether.entity;

import java.util.function.Consumer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public abstract class TileEntityAbstractCrystal extends TileEntity implements ITickable {
	
	protected CorruptorAbstract corruptor;
	protected double powerlevel = 0;
	protected int powerblocks=0;
	protected final int maxpower = 18;
	protected NBTTagCompound nbt = null;
	
	@Override
	public void onLoad() {
		createCorruptor();
		if(nbt!=null) {
			corruptor.readNBT(nbt);
			corruptor.setPos(this.getPos());
		}
	}
	
	public abstract void createCorruptor();
	
	public abstract boolean blockCanPower(BlockPos pos);

	@Override
	public void update() {
		if(world.isRemote) { return; }
		corruptor.setPower(powerlevel);
		if(powerlevel>0) {
			corruptor.onUpdate();
		}
		if (world.getTotalWorldTime()%20==0) {
			determinePower();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.nbt = compound;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        corruptor.writeNBT(compound);
        return compound;
	}
	
	public void determinePower() {
		powerblocks=0;
		Consumer<BlockPos> checkcolumn = pos->{
			for(int y=0;y<3;++y) {
				if(blockCanPower(pos.up(y))) { ++powerblocks; }
			}
		};
		checkcolumn.accept(pos.add(0, -3, 0));
		checkcolumn.accept(pos.add(1, -1, 0));
		checkcolumn.accept(pos.add(-1, -1, 0));
		checkcolumn.accept(pos.add(0, -1, 1));
		checkcolumn.accept(pos.add(0, -1, -1));
		checkcolumn.accept(pos.add(0, 1, 0));
		powerlevel = powerblocks/(double)maxpower;
	}
}
