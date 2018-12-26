package com.cutievirus.creepingnether;

import com.cutievirus.creepingnether.entity.Corruptor;
import com.cutievirus.creepingnether.entity.Purifier;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid=CreepingNether.MODID)
@Config.LangKey("creepingnether.config.title")
public class Options {

	@Config.Name("Creep Chance")
	@Config.Comment("The chance each tick that each block will spread corruption.")
	@Config.RangeDouble(min=0,max=100)
	public static double creep_chance=5d;

	@Config.Name("Portal Creep Chance")
	@Config.Comment("The chance each tick that the portal will spread corruption.")
	@Config.RangeDouble(min=0,max=100)
	public static double portal_creep_chance=10d;

	@Config.Name("Creep Time")
	@Config.Comment("How many minutes it takes for the corruption to reach max radius.")
	@Config.RangeDouble(min=0,max=500000)
	public static double creep_time=30d;

	@Config.Name("Creep Radius")
	@Config.Comment("How far the corruption will spread. 0 for unlimited.")
	@Config.RangeInt(min=0,max=9999)
	public static  int creep_radius=45;

	@Config.Name("Internal Corruption Chance")
	@Config.Comment("The chance Corruption will happen inside the Nether.")
	@Config.RangeDouble(min=0,max=100)
	public static double internalcorruption=5d;

	@Config.Name("Particles")
	@Config.Comment("Particles will appear above creeping obsidian blocks.")
	public static boolean creepingparticles=true;

	@Config.Name("Lingering Corruption")
	@Config.Comment("Even without the presence of a nether portal, bloodstone will corrupt nearby cobblestone and charred wood will corrupt nearby wood. (this helps prevent uncorrupted logs from being left behind)")
	public static boolean lingeringcorruption=true;

	@Config.Name("Sky and Fog")
	@Config.Comment("Changes sky and fog color within the Creeping Nether.")
	public static boolean skycolor=true;

	@Config.Name("Active Corruption Limit")
	@Config.Comment("How many blocks can be actively corrupting at once.")
	public static int fairylimit=500;

	@Config.Name("Entity Corruption")
	@Config.Comment("The Creeping Nether will transform certain types of mobs.")
	public static EntityOptions entity_corruption = new EntityOptions();
	public static class EntityOptions {

		@Config.Name("Lightning")
		@Config.Comment("Lightning will be summoned when mobs are transformed by the Creeping Nether.")
		public boolean lightning=true;

		@Config.Name("Pigs Zombify")
		@Config.Comment("Pigs will become Zombie Pigmen.")
		public boolean pig_zombies=true;

		@Config.Name("Slimes Magmify")
		@Config.Comment("Slimes will become Magma Cubes.")
		public boolean magma_slime=true;

		@Config.Name("Cows Shroomify")
		@Config.Comment("Cows will be transformed into Mooshrooms")
		public boolean cow_mooshroom=false;

		@Config.Name("Cows Explosify")
		@Config.Comment("Cows will explode.")
		public boolean cowsexplode=true;

		@Config.Name("Sheep Burnify")
		@Config.Comment("Sheep will drop their wool and burst into flames.")
		public boolean sheepfire=true;

		@Config.Name("Horses Skelefy")
		@Config.Comment("Untamed horses will transform into skeletal horses.")
		public boolean horse_skeletons=true;

		@Config.Name("Horses Zombify")
		@Config.Comment("Untamed horses will transform into zombie horses.")
		public boolean horse_zombies=true;

		@Config.Name("Villagers Witchify")
		@Config.Comment("Villagers will become witches.")
		public boolean villager_witches=true;

		@Config.Name("Villagers Zombify")
		@Config.Comment("Villagers will become zombie villagers.")
		public boolean villager_zombies=true;

		@Config.Name("Creepers Electrify")
		@Config.Comment("Creepers will become charged.")
		public boolean creepercharge=true;

		@Config.Name("Skeletons Witherify")
		@Config.Comment("Skeletons will become Wither Skeletons.")
		public boolean wither_skeletons=true;

	}

	@Config.Name("Nether Ores")
	@Config.Comment("Corrupted ores can be generated in the Nether.")
	public static OreOptions nether_ores = new OreOptions();
	public static class OreOptions {

		@Config.Name("Coal")
		@Config.Comment("Generates in large veins throughout the nether.")
		public boolean nethercoal=false;

		@Config.Name("Iron")
		@Config.Comment("Generates throughout the nether.")
		public boolean netheriron=false;

		@Config.Name("Gold")
		@Config.Comment("Generates throughout the nether.")
		public boolean nethergold=false;

		@Config.Name("Diamond")
		@Config.Comment("Generates near the upper and lower bedrock.")
		public boolean netherdiamond=false;

		@Config.Name("Emerald")
		@Config.Comment("Generates near the upper and lower bedrock.")
		public boolean netheremerald=false;

		@Config.Name("Lapis Lazuli")
		@Config.Comment("Generates at high and low elevations.")
		public boolean netherlapis=false;

		@Config.Name("Redstone")
		@Config.Comment("Generates throughout the nether.")
		public boolean netherredstone=false;

	}

	@Config.Name("Spawning")
	@Config.Comment("Choose which Nether mobs can spawn in the overworld.")
	@Config.RequiresMcRestart
	public static SpawnOptions spawning = new SpawnOptions();
	public static class SpawnOptions {

		@Config.Name("Ghast")
		@Config.RequiresMcRestart
		public boolean ghast=true;

		@Config.Name("Zombie Pigmen")
		@Config.RequiresMcRestart
		public boolean pigmen=true;

		@Config.Name("Magma Cubes")
		@Config.RequiresMcRestart
		public boolean magmacube=true;

		@Config.Name("Blaze")
		@Config.RequiresMcRestart
		public boolean blaze=true;

		@Config.Name("Wither Skeleton")
		@Config.RequiresMcRestart
		public boolean wither=true;

	}

	@Config.Name("Blocks to Corrupt")
	@Config.Comment("Specify additional blocks that should be transformed by the corruption.")
	public static CustomCorruption customCorruption = new CustomCorruption();
	public static class CustomCorruption {
		@Config.Name("To Netherrack")
		public String[] toNetherrack = {
				"biomesoplenty:grass",
				"biomesoplenty:dirt"
		};

		@Config.Name("To Soul Sand")
		public String[] toSoulSand = {
				"biomesoplenty:white_sand",
				"biomesoplenty:mud",
				"biomesoplenty:grass_path",
				"biomesoplenty:farmland_0",
				"biomesoplenty:farmland_1"
		};

		@Config.Name("To Magma")
		public String[] toMagma = {};

		@Config.Name("To Obsidian")
		public String[] toObsidian = {
				"biomesoplenty:hard_ice"
		};

		@Config.Name("To Charred Wood")
		public String[] toCharwood = {
				"biomesoplenty:log_0",
				"biomesoplenty:log_1",
				"biomesoplenty:log_2",
				"biomesoplenty:log_3",
				"biomesoplenty:log_4"
		};

		@Config.Name("To Charred Wood Planks")
		public String[] toCharwood_planks = {
				"biomesoplenty:planks_0"
		};

		@Config.Name("To Charred Wood Stairs")
		public String[] toCharwood_stairs = {
				"biomesoplenty:sacred_oak_stairs",
				"biomesoplenty:cherry_stairs",
				"biomesoplenty:umbran_stairs",
				"biomesoplenty:fir_stairs",
				"biomesoplenty:ethereal_stairs",
				"biomesoplenty:magic_stairs",
				"biomesoplenty:mangrove_stairs",
				"biomesoplenty:palm_stairs",
				"biomesoplenty:redwood_stairs",
				"biomesoplenty:willow_stairs",
				"biomesoplenty:pine_stairs",
				"biomesoplenty:hellbark_stairs",
				"biomesoplenty:jacaranda_stairs",
				"biomesoplenty:mahogany_stairs",
				"biomesoplenty:ebony_stairs",
				"biomesoplenty:eucalyptus_stairs"
		};

		@Config.Name("To Charred Wood Slab")
		public String[] toCharwood_slab = {
				"biomesoplenty:wood_slab_0",
				"biomesoplenty:wood_slab_1",
				"biomesoplenty:double_wood_slab_0",
				"biomesoplenty:double_wood_slab_1"
		};

		@Config.Name("To Soul Stone")
		public String[] toSoulstone = {
				"biomesoplenty:white_sandstone",
				"biomesoplenty:mud_brick_block",
				"biomesoplenty:dried_sand"
		};

		@Config.Name("To Soul Stone Stairs")
		public String[] toSoulstone_stairs = {
				"biomesoplenty:white_sandstone_stairs",
				"biomesoplenty:mud_brick_stairs"
		};

		@Config.Name("To Soul Stone Slab")
		public String[] toSoulstone_slab = {
				"biomesoplenty:other_slab",
				"biomesoplenty:double_other_slab"
		};

		@Config.Name("To Air")
		public String[] toAir = {
				"biomesoplenty:plant_0"
		};

		@Config.Name("To Burn")
		public String[] toBurn = {
				"biomesoplenty:leaves_0",
				"biomesoplenty:leaves_1",
				"biomesoplenty:leaves_2",
				"biomesoplenty:leaves_3",
				"biomesoplenty:leaves_4",
				"biomesoplenty:leaves_5",
				"biomesoplenty:leaves_6"
		};

		@Config.Name("Other")
		@Config.Comment("Use format mod:fromblock>mod:toblock")
		public String[] toCustom = {
				"biomesoplenty:grass@0>null",
				"biomesoplenty:grass@6>null",
				"biomesoplenty:grass@8>null",
				"minecraft:mycelium>biomesoplenty:grass@8",
		};

	}

	@Config.Name("Blocks to Purify")
	@Config.Comment("Specify additional blocks that should be transformed by the purification catalyst.")
	public static CustomPurification customPurification = new CustomPurification();
	public static class CustomPurification {
		@Config.Name("Other")
		@Config.Comment("Use format mod:fromblock>mod:toblock")
		public String[] toCustom = {
				"biomesoplenty:grass@8>creepingnether:hallowgrass",
		};

		@Config.Name("To Hallow Rock")
		public String[] toHallowrock = {};

		@Config.Name("To Hallow Sand")
		public String[] toHallowsand = {};

		@Config.Name("To Hallow Stone")
		public String[] toHallowstone = {};

		@Config.Name("To Hallow Stone Stairs")
		public String[] toHallowstone_stairs = {};

		@Config.Name("To Hallow Stone Slab")
		public String[] toHallowstone_slab = {};

		@Config.Name("To Hallow Wood")
		public String[] toHallowwood = {};

		@Config.Name("To Hallow Wood Planks")
		public String[] toHallowwood_planks = {};

		@Config.Name("To Hallow Wood Stairs")
		public String[] toHallowwood_stairs = {};

		@Config.Name("To Hallow Wood Slab")
		public String[] toHallowwood_slab = {};

	}

	public static class TransformationLists{

		public enum Transformations{
			HORSE_SKELEFY, HORSE_ZOMBIFY,
			VILLAGER_WITCHIFY, VILLAGER_ZOMBIFY,
			COWS_EXPLODIFY, COWS_SHROOMIFY
		}

		public static RandomList<Transformations> horse_transformations = new RandomList<>();
		public static RandomList<Transformations> villager_transformations = new RandomList<>();
		public static RandomList<Transformations> cow_transformations = new RandomList<>();

		public static void updateTransformationLists() {
			horse_transformations.clear();
			if(entity_corruption.horse_skeletons) { horse_transformations.add(Transformations.HORSE_SKELEFY); }
			if(entity_corruption.horse_zombies) { horse_transformations.add(Transformations.HORSE_ZOMBIFY); }

			villager_transformations.clear();
			if(entity_corruption.villager_witches) { villager_transformations.add(Transformations.VILLAGER_WITCHIFY); }
			if(entity_corruption.villager_zombies) { villager_transformations.add(Transformations.VILLAGER_ZOMBIFY); }

			cow_transformations.clear();
			if(entity_corruption.cowsexplode) { cow_transformations.add(Transformations.COWS_EXPLODIFY); }
			if(entity_corruption.cow_mooshroom) { cow_transformations.add(Transformations.COWS_SHROOMIFY); }
		}

	}

	@Mod.EventBusSubscriber(modid = CreepingNether.MODID)
	private static class EventHandler {

		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(CreepingNether.MODID)) {
				ConfigManager.sync(CreepingNether.MODID, Config.Type.INSTANCE);
				updateConfig();
			}
		}
	}

	public static void updateConfig() {
		TransformationLists.updateTransformationLists();
		Corruptor.allocateMaps();
		Purifier.allocateMaps();
	}

}
