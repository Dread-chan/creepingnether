package com.cutievirus.creepingnether;

import java.util.ArrayList;
import java.util.Random;

public class RandomList<E> extends ArrayList<E> {

	private static final long serialVersionUID = 1L;
	
	private static final Random rand = new Random();
	
	public E getRandom() {
		return this.get(rand.nextInt(this.size()));
	}

}
