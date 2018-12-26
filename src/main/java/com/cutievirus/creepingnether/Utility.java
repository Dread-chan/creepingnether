package com.cutievirus.creepingnether;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class Utility {
	
	private static String DATAID = CreepingNether.MODID;
	
	public static NBTTagCompound getData(Entity entity) {
		return entity.getEntityData();
	}
	
	public static NBTTagCompound getModData(Entity entity) {
		return getData(entity).getCompoundTag(DATAID);
	}
	public static void setModData(Entity entity, NBTTagCompound data) {
		getData(entity).setTag(DATAID, data);
	}
	
	public static boolean dataHasKey(Entity entity, String key) {
		return getModData(entity).hasKey(key);
	}
	
	public static int getDataInt(Entity entity, String key) {
		return getModData(entity).getInteger(key);
	}
	public static void setDataInt(Entity entity, String key, int value) {
		NBTTagCompound data = getModData(entity);
		data.setInteger(key, value);
		getData(entity).setTag(DATAID, data);
	}
	
	public static String getDataString(Entity entity, String key) {
		return getModData(entity).getString(key);
	}
	public static void setDataString(Entity entity, String key, String value) {
		NBTTagCompound data = getModData(entity);
		data.setString(key, value);
		getData(entity).setTag(DATAID, data);
	}
	
	public static NBTTagCompound getDataCompound(Entity entity, String key) {
		return getModData(entity).getCompoundTag(key);
	}
	public static void setDataCompound(Entity entity, String key, NBTTagCompound value) {
		NBTTagCompound data = getModData(entity);
		data.setTag(key, value);
		getData(entity).setTag(DATAID, data);
	}
	
}
