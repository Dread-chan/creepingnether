package com.cutievirus.creepingnether.item;

public interface IModItem {
	public IModItem setBurnTime(int time);
	public IModItem setBurnCount(float count);
	public int getBurnTime();
}
