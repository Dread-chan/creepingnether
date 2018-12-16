package com.cutievirus.creepingnether;

import java.util.Random;

import com.cutievirus.creepingnether.block.BlockBloodStone;
import com.cutievirus.creepingnether.block.BlockCharWood;
import com.cutievirus.creepingnether.block.BlockCharWoodPlank;
import com.cutievirus.creepingnether.block.BlockCreepingObsidian;
import com.cutievirus.creepingnether.block.BlockModSlab;
import com.cutievirus.creepingnether.block.BlockModStairs;
import com.cutievirus.creepingnether.block.BlockNetherOre;
import com.cutievirus.creepingnether.block.BlockSoulStone;
import com.cutievirus.creepingnether.block.BlockSoulStoneCharged;
import com.cutievirus.creepingnether.block.BlockSoulStoneCrystal;
import com.cutievirus.creepingnether.entity.BiomeCreepingNether;
import com.cutievirus.creepingnether.item.ItemEssence;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;


public class Ref {
	
	public static final Random rand = new Random();

	public static BlockCreepingObsidian creepingobsidian;
	public static BlockBloodStone bloodstone;
	public static BlockModStairs bloodstone_stairs;
	public static BlockModSlab bloodstone_slab;
	public static BlockModSlab bloodstone_slab2;
	public static BlockCharWood charwood;
	public static BlockCharWoodPlank charwood_planks;
	public static BlockModStairs charwood_stairs;
	public static BlockModSlab charwood_slab;
	public static BlockModSlab charwood_slab2;
	public static BlockSoulStone soulstone;
	public static BlockModStairs soulstone_stairs;
	public static BlockModSlab soulstone_slab;
	public static BlockModSlab soulstone_slab2;
	public static BlockSoulStoneCharged soulstone_charged;
	public static BlockSoulStoneCrystal soulstone_crystal;
	
	public static Block[] CharwoodList;
	
	public static BlockNetherOre nethercoal;
	public static BlockNetherOre netheriron;
	public static BlockNetherOre nethergold;
	public static BlockNetherOre netherdiamond;
	public static BlockNetherOre netherlapis;
	public static BlockNetherOre netherredstone;
	public static BlockNetherOre netheremerald;
	
	public static ItemEssence netheressence;
	public static ItemEssence purifiedessence;
	
	public static BiomeCreepingNether biome;
	
	public static final CreativeTabs tabcreepingnether =  (new CreativeTabs("creepingnether") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Ref.netheressence);
		}
	});
	
}
