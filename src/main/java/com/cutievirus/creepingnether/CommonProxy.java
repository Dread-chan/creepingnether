package com.cutievirus.creepingnether;

import com.cutievirus.creepingnether.block.WorldGen;
import com.cutievirus.creepingnether.entity.MessageCorruptBiome;
import com.cutievirus.creepingnether.entity.MessageCorruptBiome.MessageHandlerCorruptBiome;
import com.cutievirus.creepingnether.entity.TileEntityHallowCrystal;
import com.cutievirus.creepingnether.entity.TileEntityNetherCrystal;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy{


	public void preInit(){

		Ref.defineBlocksAndItems();

		//tile entities
		GameRegistry.registerTileEntity(TileEntityNetherCrystal.class, new ResourceLocation("creepingnether","nethercrystal"));
		GameRegistry.registerTileEntity(TileEntityHallowCrystal.class, new ResourceLocation("creepingnether","hallowcrystal"));

		//generation
		GameRegistry.registerWorldGenerator(new WorldGen(), 0);

		//messages
		CreepingNether.network.registerMessage(MessageHandlerCorruptBiome.class, MessageCorruptBiome.class, 0, Side.CLIENT);

	}
	public void init(){
		//ore dictionary
		OreDictionary.registerOre("oreCoal", Ref.nethercoal);
		OreDictionary.registerOre("oreIron", Ref.netheriron);
		OreDictionary.registerOre("oreGold", Ref.nethergold);
		OreDictionary.registerOre("oreDiamond", Ref.netherdiamond);
		OreDictionary.registerOre("oreEmerald", Ref.netheremerald);
		OreDictionary.registerOre("oreLapis", Ref.netherlapis);
		OreDictionary.registerOre("oreRedstone", Ref.netherredstone);

		OreDictionary.registerOre("treeWood", Ref.charwood);
		OreDictionary.registerOre("plankWood", Ref.charwood_planks);
		OreDictionary.registerOre("slabWood", Ref.charwood_slab);
		OreDictionary.registerOre("stairWood", Ref.charwood_stairs);

		GameRegistry.addSmelting(Ref.nethercoal, new ItemStack(Items.COAL), 0.1f);
		GameRegistry.addSmelting(Ref.netheriron, new ItemStack(Items.IRON_INGOT), 0.7f);
		GameRegistry.addSmelting(Ref.nethergold, new ItemStack(Items.GOLD_INGOT), 1f);
		GameRegistry.addSmelting(Ref.netherdiamond, new ItemStack(Items.DIAMOND), 1f);
		GameRegistry.addSmelting(Ref.netheremerald, new ItemStack(Items.EMERALD), 1f);
		GameRegistry.addSmelting(Ref.netherlapis, new ItemStack(Items.DYE,1,4), 0.2f);
		GameRegistry.addSmelting(Ref.netherredstone, new ItemStack(Items.REDSTONE), 0.7f);
		GameRegistry.addSmelting(Ref.charwood, new ItemStack(Items.COAL,1,1), 0.15f);

		Blocks.FIRE.setFireInfo(Ref.goldleaves, 0, 60);
	}
	public void postInit(){
		Options.updateConfig();
	}

	public void registerModels() { }

}
