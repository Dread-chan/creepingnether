package com.cutievirus.creepingnether;

import java.util.ArrayList;
import java.util.Random;

public class RandomList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	
	private static final Random rand = new Random();
	
	public E getRandom() {
		return this.get(randomIndex());
	}
	
	public E removeRandom() {
		return this.remove(randomIndex());
	}
	
	public int randomIndex() {
		return rand.nextInt(this.size());
	}
	
	public void addAll(@SuppressWarnings("unchecked") E...values) {
		for (E value : values) {
			add(value);
		}
	}

}
