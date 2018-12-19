package com.cutievirus.creepingnether;

import java.util.HashMap;

public class EasyMap<V> extends HashMap<Object,V> {
	
	private static final long serialVersionUID = 1L;
	
	public EasyMap() {}
	public EasyMap(Object[][] entries) {
		assign(entries);
	}
	
	public EasyMap<V> assign(Object[][] entries) {
		for (Object[] entry : entries) {
			try {
				@SuppressWarnings("unchecked")
				V value = (V)entry[1];
				put(entry[0], value);
			}catch(ClassCastException e) {
				System.out.println(e);
			}
		}
		return this;
	}
	
	public EasyMap<V> assign(Object[] keys, V value) {
		for (Object key : keys) {
			put(key,value);
		}
		return this;
	}
	
	public EasyMap<V> add(Object key, V value) {
		this.put(key, value);
		return this;
	}
	
	public EasyMap<V> delete(Object key) {
		this.remove(key);
		return this;
	}
	
	public V getFrom(Object[] keys) {
		return this.getFrom(keys,null);
	}
	
	public V getFrom(Object[] keys, V dfault) {
		for (Object key : keys) {
			if(this.containsKey(key)) {
				return this.get(key);
			}
		}
		return dfault;
	}
	
	
}
