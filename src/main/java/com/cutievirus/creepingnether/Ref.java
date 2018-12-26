package com.cutievirus.creepingnether;

import java.util.Random;

import com.cutievirus.creepingnether.block.BlockBloodStone;
import com.cutievirus.creepingnether.block.BlockCharWood;
import com.cutievirus.creepingnether.block.BlockCharWoodPlank;
import com.cutievirus.creepingnether.block.BlockCreepingObsidian;
import com.cutievirus.creepingnether.block.BlockCreepingObsidianWatery;
import com.cutievirus.creepingnether.block.BlockGoldLeaves;
import com.cutievirus.creepingnether.block.BlockHallowGrass;
import com.cutievirus.creepingnether.block.BlockHallowSand;
import com.cutievirus.creepingnether.block.BlockHallowStone;
import com.cutievirus.creepingnether.block.BlockHallowStoneCharged;
import com.cutievirus.creepingnether.block.BlockHallowStoneCrystal;
import com.cutievirus.creepingnether.block.BlockHallowWood;
import com.cutievirus.creepingnether.block.BlockModBlock;
import com.cutievirus.creepingnether.block.BlockModSlab;
import com.cutievirus.creepingnether.block.BlockModStairs;
import com.cutievirus.creepingnether.block.BlockModWood;
import com.cutievirus.creepingnether.block.BlockNetherOre;
import com.cutievirus.creepingnether.block.BlockNetherOre.BlockHallowOre;
import com.cutievirus.creepingnether.block.BlockNetherRedstone;
import com.cutievirus.creepingnether.block.BlockNetherRedstone.BlockHallowRedstone;
import com.cutievirus.creepingnether.block.BlockNetherRose;
import com.cutievirus.creepingnether.block.BlockSoulStone;
import com.cutievirus.creepingnether.block.BlockSoulStoneCharged;
import com.cutievirus.creepingnether.block.BlockSoulStoneCrystal;
import com.cutievirus.creepingnether.entity.BiomeCreepingNether;
import com.cutievirus.creepingnether.entity.BiomePurified;
import com.cutievirus.creepingnether.item.ItemEssence;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class Ref {

	public static final Random rand = new Random();

	public static BlockCreepingObsidian creepingobsidian;
	public static BlockCreepingObsidianWatery drippyobsidian;
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

	public static BlockModBlock hallowrock;
	public static BlockHallowGrass hallowgrass;
	public static BlockHallowSand hallowsand;
	public static BlockHallowStone hallowstone;
	public static BlockModStairs hallowstone_stairs;
	public static BlockModSlab hallowstone_slab;
	public static BlockModSlab hallowstone_slab2;
	public static BlockHallowStoneCharged hallowstone_charged;
	public static BlockHallowStoneCrystal hallowstone_crystal;
	public static BlockModWood hallowwood;
	public static BlockCharWoodPlank hallowwood_planks;
	public static BlockModStairs hallowwood_stairs;
	public static BlockModSlab hallowwood_slab;
	public static BlockModSlab hallowwood_slab2;
	public static BlockNetherRose netherlight_rose;
	public static BlockModBlock magma_cooled;

	public static BlockNetherOre nethercoal,
								netheriron,
								nethergold,
								netherdiamond,
								netherlapis,
								netherredstone,
								netheremerald,

								hallowcoal,
								hallowiron,
								hallowgold,
								hallowdiamond,
								hallowlapis,
								hallowredstone,
								hallowemerald;

	public static BlockGoldLeaves goldleaves;

	public static ItemEssence netheressence,
							purifiedessence;

	public static BiomeCreepingNether biomeCreepingNether;
	public static BiomePurified biomePurified;

	public static final CreativeTabs tabcreepingnether =  (new CreativeTabs("creepingnether") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(netheressence);
		}
	});

	static RandomList<Block> blockList = new RandomList<>();
	static RandomList<Item> itemList = new RandomList<>();

	public static void defineBlocksAndItems() {
		//blocks
		creepingobsidian = new BlockCreepingObsidian();
		drippyobsidian = new BlockCreepingObsidianWatery();

		bloodstone = new BlockBloodStone();
		bloodstone_stairs = new BlockModStairs(bloodstone, "bloodstone");
		bloodstone_slab = new BlockModSlab(bloodstone, "bloodstone", false);
		bloodstone_slab2 = new BlockModSlab(bloodstone, "bloodstone", true);
		BlockModSlab.createItem(bloodstone_slab, bloodstone_slab2, 0);

		charwood = new BlockCharWood();
		charwood_planks = new BlockCharWoodPlank();
		charwood_stairs = new BlockModStairs(charwood_planks, "charwood");
		charwood_slab = new BlockModSlab(charwood_planks, "charwood", false);
		charwood_slab2 = new BlockModSlab(charwood_planks, "charwood", true);
		BlockModSlab.createItem(charwood_slab, charwood_slab2, 200);

		soulstone = new BlockSoulStone();
		soulstone_stairs = new BlockModStairs(soulstone, "soulstone");
		soulstone_slab = new BlockModSlab(soulstone, "soulstone", false);
		soulstone_slab2 = new BlockModSlab(soulstone, "soulstone", true);
		BlockModSlab.createItem(soulstone_slab, soulstone_slab2, 0);

		soulstone_charged = new BlockSoulStoneCharged();
		soulstone_crystal = new BlockSoulStoneCrystal();

		//ores
		nethercoal = new BlockNetherOre("nethercoal",Blocks.COAL_ORE,0)
		.setEssenceChance(0.1).setDrops(Items.COAL,1,1).setXp(0,2);
		netheriron = new BlockNetherOre("netheriron",Blocks.IRON_ORE,0)
		.setEssenceChance(0.2).setDrops(Items.IRON_NUGGET,8,12).setXp(1,3);
		nethergold = new BlockNetherOre("nethergold",Blocks.GOLD_ORE,1)
		.setEssenceChance(0.35).setDrops(Items.GOLD_NUGGET,8,12).setXp(2,5);
		netherdiamond = new BlockNetherOre("netherdiamond",Blocks.DIAMOND_ORE,2)
		.setEssenceChance(0.75).setDrops(Items.DIAMOND,1,1).setXp(3,7);
		netheremerald = new BlockNetherOre("netheremerald",Blocks.EMERALD_ORE,2)
		.setEssenceChance(0.75).setDrops(Items.EMERALD,1,1).setXp(3,7);
		netherlapis = new BlockNetherOre("netherlapis",Blocks.LAPIS_ORE,0)
		.setDamageDropped(EnumDyeColor.BLUE.getDyeDamage())
		.setEssenceChance(0.5).setDrops(Items.DYE,4,9).setXp(2,5);
		netherredstone = new BlockNetherRedstone()
		.setEssenceChance(0.4);

		hallowcoal = new BlockHallowOre("hallowcoal",0).copyOre(nethercoal);
		hallowiron = new BlockHallowOre("hallowiron",1).copyOre(netheriron);
		hallowgold = new BlockHallowOre("hallowgold",2).copyOre(nethergold);
		hallowdiamond = new BlockHallowOre("hallowdiamond",2).copyOre(netherdiamond);
		hallowemerald = new BlockHallowOre("hallowemerald",2).copyOre(netheremerald);
		hallowlapis = new BlockHallowOre("hallowlapis",1).copyOre(netherlapis);
		hallowredstone = new BlockHallowRedstone();

		//hallow
		hallowgrass = new BlockHallowGrass("hallowgrass");
		hallowrock = new BlockModBlock("hallowrock",Material.ROCK,MapColor.SILVER_STAINED_HARDENED_CLAY);
		hallowrock.setSoundType(SoundType.STONE).setResistance(5.0F).setHardness(1.0F);
		hallowsand = new BlockHallowSand("hallowsand");

		hallowstone = new BlockHallowStone("hallowstone");
		hallowstone_stairs = new BlockModStairs(hallowstone, "hallowstone");
		hallowstone_slab = new BlockModSlab(hallowstone, "hallowstone", false);
		hallowstone_slab2 = new BlockModSlab(hallowstone, "hallowstone", true);
		BlockModSlab.createItem(hallowstone_slab, hallowstone_slab2, 0);
		hallowstone_charged = new BlockHallowStoneCharged("hallowstone_charged");
		hallowstone_crystal = new BlockHallowStoneCrystal("hallowstone_crystal");

		hallowwood = new BlockHallowWood("hallowwood");
		hallowwood.setBurnCount(3);
		hallowwood_planks = new BlockCharWoodPlank("hallowwood_planks",Ref.hallowwood);
		hallowwood_planks.setHardness(2); hallowwood_planks.setBurnCount(1.5f);
		hallowwood_stairs = new BlockModStairs(hallowwood_planks, "hallowwood");
		hallowwood_slab = new BlockModSlab(hallowwood_planks, "hallowwood", false);
		hallowwood_slab2 = new BlockModSlab(hallowwood_planks, "hallowwood", true);
		BlockModSlab.createItem(hallowwood_slab, hallowwood_slab2);

		netherlight_rose = new BlockNetherRose("netherlight_rose");
		magma_cooled = new BlockModBlock("magma_cooled",Material.ROCK,MapColor.OBSIDIAN);
		magma_cooled.setSoundType(SoundType.STONE).setHardness(0.5F);
		goldleaves = new BlockGoldLeaves("goldleaves");

		//items
		netheressence = new ItemEssence("essence_nether");
		purifiedessence = new ItemEssence("essence_purified")
		.setPurified(true);

		CharwoodList = new Block[]{
			charwood, charwood_planks, charwood_slab, charwood_slab2, charwood_stairs
		};

		blockList.addAll(
			creepingobsidian,drippyobsidian,
			bloodstone, bloodstone_stairs, bloodstone_slab,
			charwood, charwood_planks, charwood_stairs, charwood_slab,
			soulstone, soulstone_stairs, soulstone_slab,
			soulstone_charged,soulstone_crystal,
			nethercoal, netheriron, nethergold, netherdiamond,
			netherlapis, netherredstone, netheremerald,
			hallowrock,hallowgrass,hallowsand,
			hallowwood, hallowwood_planks, hallowwood_stairs, hallowwood_slab,
			hallowstone, hallowstone_stairs, hallowstone_slab,
			hallowstone_charged,hallowstone_crystal,
			hallowcoal, hallowiron, hallowgold, hallowdiamond,
			hallowlapis, hallowredstone, hallowemerald,
			magma_cooled, goldleaves,
			netherlight_rose
		);
		itemList.addAll(
			netheressence, purifiedessence
		);
	}

}
