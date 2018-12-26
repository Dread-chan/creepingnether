package com.cutievirus.creepingnether.block;

import com.cutievirus.creepingnether.item.IModItem;

public interface IModBlock {
	public IModItem getModItem();
	
	public default IModBlock setBurnTime(int time) {
		getModItem().setBurnTime(time);
		return this;
	}
	public default IModBlock setBurnCount(float count) {
		getModItem().setBurnCount(count);
		return this;
	}
}
