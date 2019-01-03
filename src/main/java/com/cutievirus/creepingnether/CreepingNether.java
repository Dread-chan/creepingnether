package com.cutievirus.creepingnether;

import com.cutievirus.creepingnether.block.BlockModSlab;
import com.cutievirus.creepingnether.block.IModBlock;
import com.cutievirus.creepingnether.entity.BiomeCreepingNether;
import com.cutievirus.creepingnether.entity.BiomePurified;
import com.cutievirus.creepingnether.entity.EntityPigman;
import com.cutievirus.creepingnether.entity.EntityPortal;
import com.cutievirus.creepingnether.item.IModItem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid=CreepingNether.MODID, version=CreepingNether.VERSION)
@Mod.EventBusSubscriber(modid=CreepingNether.MODID)
public class CreepingNether {

	public static final String MODID = "creepingnether";
	public static final String VERSION = "2.2.2";

	@SidedProxy(clientSide="com.cutievirus.creepingnether.ClientProxy", serverSide="com.cutievirus.creepingnether.CommonProxy")
	public static CommonProxy proxy;

	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("creepingnether");

	public static float miasma = 0f;

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		Ref.biomeCreepingNether = new BiomeCreepingNether((new Biome.BiomeProperties("Creeping Nether"))
			.setTemperature(10.0F).setRainfall(0.0F).setRainDisabled());
		Ref.biomeCreepingNether.setRegistryName("creeping_nether");
		Ref.biomePurified = new BiomePurified((new Biome.BiomeProperties("Purified Nether")));
		Ref.biomePurified.setRegistryName("purified_nether");
		IForgeRegistry<Biome> registry = event.getRegistry();
		registry.registerAll(Ref.biomeCreepingNether, Ref.biomePurified);
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		EntityEntry portal = new EntityEntry(EntityPortal.class, "creeping_portal");
		portal.setRegistryName("creeping_portal");
		EntityEntry pigman = new EntityEntry(EntityPigman.class, "pigman");
		pigman.setRegistryName("pigman");
		IForgeRegistry<EntityEntry> registry = event.getRegistry();
		registry.register(portal);
		registry.register(pigman);
		EntityRegistry.registerEgg(new ResourceLocation("creepingnether:pigman"), 0xf2999a, 0x71402f);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		IForgeRegistry<Block> registry = event.getRegistry();
		for (Block block:Ref.blockList) {
			if (block instanceof BlockModSlab) {
				registry.register(((BlockModSlab) block).getHalfVersion());
				registry.register(((BlockModSlab) block).getFullVersion());
			}else {
				registry.register(block);
			}
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		for (Block block:Ref.blockList) {
			if (block instanceof IModBlock) {
				IModItem item = ((IModBlock)block).getModItem();
				if(item instanceof Item) {
					registry.register((Item)item);
				}
			}
		}
		for (Item item:Ref.itemList) {
			registry.register(item);
		}
	}


	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
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
