package com.cutievirus.creepingnether;

import com.cutievirus.creepingnether.Options.TransformationLists;
import com.cutievirus.creepingnether.entity.BiomeCreepingNether;
import com.cutievirus.creepingnether.entity.EntityPortal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityEntry;

@Mod(modid=CreepingNether.MODID, version=CreepingNether.VERSION)
@Mod.EventBusSubscriber(modid=CreepingNether.MODID)
public class CreepingNether {
	
	public static final String MODID = "creepingnether";
	public static final String VERSION = "2.0";
	
	@SidedProxy(clientSide="com.cutievirus.creepingnether.ClientProxy", serverSide="com.cutievirus.creepingnether.CommonProxy")
	public static CommonProxy proxy;
	
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("creepingNether");

	public static float miasma = 0f;
	
	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		Ref.biome = new BiomeCreepingNether((new Biome.BiomeProperties("Creeping Nether")).setTemperature(10.0F).setRainfall(0.0F).setRainDisabled());
		Ref.biome.setRegistryName("creeping_nether");
		event.getRegistry().register(Ref.biome);
	}
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		EntityEntry portal = new EntityEntry(EntityPortal.class, "creeping_nether_portal");
		portal.setRegistryName("portal");
		event.getRegistry().register(portal);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			Ref.creepingobsidian,
			Ref.bloodstone, Ref.bloodstone_stairs, Ref.bloodstone_slab, Ref.bloodstone_slab2,
			Ref.charwood,
			Ref.charwood_planks, Ref.charwood_stairs, Ref.charwood_slab, Ref.charwood_slab2,
			Ref.soulstone, Ref.soulstone_stairs, Ref.soulstone_slab, Ref.soulstone_slab2,
			Ref.soulstone_charged,Ref.soulstone_crystal,
			Ref.nethercoal, Ref.netheriron, Ref.nethergold, Ref.netherdiamond,
			Ref.netherlapis, Ref.netherredstone, Ref.netheremerald
	    );
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
	    event.getRegistry().registerAll(
			Ref.creepingobsidian.item,
			Ref.bloodstone.item, Ref.bloodstone_stairs.item, Ref.bloodstone_slab.item,
			Ref.charwood.item, 
			Ref.charwood_planks.item, Ref.charwood_stairs.item, Ref.charwood_slab.item,
			Ref.soulstone.item, Ref.soulstone_stairs.item, Ref.soulstone_slab.item,
			Ref.soulstone_charged.item,Ref.soulstone_crystal.item,
			Ref.nethercoal.item, Ref.netheriron.item, Ref.nethergold.item, Ref.netherdiamond.item,
			Ref.netherlapis.item, Ref.netherredstone.item, Ref.netheremerald.item,
			Ref.netheressence, Ref.purifiedessence
	    );
	}

	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Confg.Confg2.config = new Configuration(event.getSuggestedConfigurationFile());
		//Confg.syncConfig();
		TransformationLists.updateTransformationLists();
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
	@EventHandler
	public void preInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		proxy.registerModels();
	}
	
}
