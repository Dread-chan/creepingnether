package com.cutievirus.creepingnether;

import java.util.ArrayList;
import java.util.Random;

public class RandomList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;

	private static final Random rand = new Random();

	public E getRandom() {
		return get(randomIndex());
	}

	public E removeRandom() {
		return this.remove(randomIndex());
	}

	public int randomIndex() {
		return rand.nextInt(size());
	}

	public RandomList<E> addAll(@SuppressWarnings("unchecked") E...values) {
		for (E value : values) {
			add(value);
		}
		return this;
	}

	public RandomList<E> addMany(E item, int count) {
		for (int i=0;i<count;++i) {
			add(item);
		}
		return this;
	}
	public RandomList<E> addOne(E item) {
		add(item);
		return this;
	}

}
