package com.cutievirus.creepingnether;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cutievirus.creepingnether.block.BlockBloodStone;
import com.cutievirus.creepingnether.block.BlockCharWood;
import com.cutievirus.creepingnether.block.BlockCharWoodPlank;
import com.cutievirus.creepingnether.block.BlockCreepingObsidian;
import com.cutievirus.creepingnether.block.BlockModSlab;
import com.cutievirus.creepingnether.block.BlockModStairs;
import com.cutievirus.creepingnether.block.BlockNetherOre;
import com.cutievirus.creepingnether.block.BlockNetherRedstone;
import com.cutievirus.creepingnether.block.BlockSoulStone;
import com.cutievirus.creepingnether.block.BlockSoulStoneCharged;
import com.cutievirus.creepingnether.block.BlockSoulStoneCrystal;
import com.cutievirus.creepingnether.block.WorldGen;
import com.cutievirus.creepingnether.entity.Corruptor;
import com.cutievirus.creepingnether.entity.EntityPortal;
import com.cutievirus.creepingnether.entity.NetherFairy;
import com.cutievirus.creepingnether.entity.TileEntityNetherCrystal;
import com.cutievirus.creepingnether.item.ItemEssence;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CommonProxy{
	
	private static final Random rand = new Random();

	public void preInit(){
		
		//blocks
		Ref.creepingobsidian = new BlockCreepingObsidian();
		
		Ref.bloodstone = new BlockBloodStone();
		Ref.bloodstone_stairs = new BlockModStairs(Ref.bloodstone, "bloodstone");
		Ref.bloodstone_slab = new BlockModSlab(Ref.bloodstone, "bloodstone", false);
		Ref.bloodstone_slab2 = new BlockModSlab(Ref.bloodstone, "bloodstone", true);
		BlockModSlab.createItem(Ref.bloodstone_slab, Ref.bloodstone_slab2, 0);
		
		Ref.charwood = new BlockCharWood();
		Ref.charwood_planks = new BlockCharWoodPlank();
		Ref.charwood_stairs = new BlockModStairs(Ref.charwood_planks, "charwood");
		Ref.charwood_slab = new BlockModSlab(Ref.charwood_planks, "charwood", false);
		Ref.charwood_slab2 = new BlockModSlab(Ref.charwood_planks, "charwood", true);
		BlockModSlab.createItem(Ref.charwood_slab, Ref.charwood_slab2, 200);
		
		Ref.soulstone = new BlockSoulStone();
		Ref.soulstone_stairs = new BlockModStairs(Ref.soulstone, "soulstone");
		Ref.soulstone_slab = new BlockModSlab(Ref.soulstone, "soulstone", false);
		Ref.soulstone_slab2 = new BlockModSlab(Ref.soulstone, "soulstone", true);
		BlockModSlab.createItem(Ref.soulstone_slab, Ref.soulstone_slab2, 0);
		
		Ref.soulstone_charged = new BlockSoulStoneCharged();
		Ref.soulstone_crystal = new BlockSoulStoneCrystal();
		
		Ref.CharwoodList = new Block[]{Ref.charwood, Ref.charwood_planks, Ref.charwood_slab, Ref.charwood_slab2, Ref.charwood_stairs};
		
		//ores
		Ref.nethercoal = new BlockNetherOre("nethercoal",Blocks.COAL_ORE, Items.COAL,0, 1,1, 0,2)
		.setEssence(0.1);
		Ref.netheriron = new BlockNetherOre("netheriron",Blocks.IRON_ORE, Items.IRON_NUGGET,0, 8,12, 1,3)
		.setEssence(0.2);
		Ref.nethergold = new BlockNetherOre("nethergold",Blocks.GOLD_ORE, Items.GOLD_NUGGET,1, 8,12, 2,5)
		.setEssence(0.35);
		Ref.netherdiamond = new BlockNetherOre("netherdiamond",Blocks.DIAMOND_ORE, Items.DIAMOND,2, 1,1, 3,7)
		.setEssence(0.75);
		Ref.netheremerald = new BlockNetherOre("netheremerald",Blocks.EMERALD_ORE, Items.EMERALD,2, 1,1, 3,7)
		.setEssence(0.75);
		Ref.netherlapis = new BlockNetherOre("netherlapis",Blocks.LAPIS_ORE, Items.DYE,0, 4,9, 2,5)
		.setDamageDropped(EnumDyeColor.BLUE.getDyeDamage())
		.setEssence(0.5);
		Ref.netherredstone = new BlockNetherRedstone()
		.setEssence(0.4);
		
		//items
		Ref.netheressence = new ItemEssence("essence_nether");
		Ref.purifiedessence = new ItemEssence("essence_purified")
		.setPurified(true);
		
		//tile entities
		GameRegistry.registerTileEntity(TileEntityNetherCrystal.class, new ResourceLocation("cutievirus_nethercrystal"));

		//generation
		GameRegistry.registerWorldGenerator(new WorldGen(), 0);
		
    	MinecraftForge.EVENT_BUS.register(this);
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
	}
	public void postInit(){
		Corruptor.allocateMaps();
	}
	
	public void registerModels() { }
	
	@SubscribeEvent
	public void usedItem(RightClickBlock event){
		World world = event.getWorld();
		BlockPos pos = event.getPos();
    	if (world.provider.getDimensionType()==DimensionType.NETHER) {
    		return;
    	}
		// detect lighting portal
		BlockPos pos2 = pos.offset(event.getFace());
		if(world.isRemote
		||event.getItemStack().getItem()!=Items.FLINT_AND_STEEL
		||world.getBlockState(pos).getBlock()!=Blocks.OBSIDIAN
		||world.getBlockState(pos2).getBlock()!=Blocks.AIR) {
			return;
		}
		EntityPortal.createPortal(world, pos2);
	}
	
	protected List<NetherFairy> fairies = new ArrayList<>();
	protected List<NetherFairy> new_fairies = new ArrayList<>();
	
	int playerindex = 0;
	
    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event) {
    	MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
    	List<EntityPlayerMP> playerlist = server.getPlayerList().getPlayers();
    	if(playerlist.isEmpty()) { return; }
    	if(playerindex>=playerlist.size()) { playerindex=0; }
    	EntityPlayerMP player = playerlist.get(playerindex++);
		switch(player.world.provider.getDimensionType()) {
    	case NETHER:{ //internal corruption
    		if (!Options.internalcorruption) { break; }
    		World nether=player.world;
    		if (!Corruptor.doesPortalCreep()) {
        		BlockPos pos = player.getPosition().add(
        				rand.nextInt(19) - 9,
        				rand.nextInt(5) - 2,
        				rand.nextInt(19) - 9 );
        		Corruptor.DoCorruption(nether, pos, new_fairies);
        		Corruptor.corruptEntities(nether, pos);
    		}
    		Corruptor.updateFairies(nether,fairies,new_fairies);
    		break;
    	}default:{ // find portal blocks and start creeping
    		BlockPos pos = player.getPosition();
        	Block block = player.world.getBlockState(pos).getBlock();
        	if (block==Blocks.PORTAL) {
        		EntityPortal.createPortal(player.world, pos);
        	}
        	pos = pos.add(rand.nextInt(19) - 9, rand.nextInt(5) - 2, rand.nextInt(19) - 9);
        	block = player.world.getBlockState(pos).getBlock();
          	if (block==Blocks.PORTAL) {
        		EntityPortal.createPortal(player.world, pos);
        	}
    		break;
		}}
    }
    
    Minecraft minecraft = Minecraft.getMinecraft();
    boolean inbiome = false;
    float miasma = 0f;
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
    	if (!Options.skycolor) { return; }
    	if(minecraft.world==null) {return;}
    	if(minecraft.world.getTotalWorldTime()%10!=0) {
    		BlockPos pos = minecraft.player.getPosition();
    		inbiome = minecraft.world.getBiome(pos)==Ref.biome
    		&& minecraft.world.getBiome(pos.add(2,0,2))==Ref.biome
    		&& minecraft.world.getBiome(pos.add(-2,0,2))==Ref.biome
    		&& minecraft.world.getBiome(pos.add(2,0,-2))==Ref.biome
    		&& minecraft.world.getBiome(pos.add(-2,0,-2))==Ref.biome;
    		
    	}
    	CreepingNether.miasma += ((inbiome?1f:0f)-CreepingNether.miasma)/20;
    }
    
    @SubscribeEvent
    public void eventFog(EntityViewRenderEvent.FogColors event) {
    	if (!Options.skycolor) { return; }
    	if (event.getState().getMaterial().isLiquid()) { return; }
    	if (!(event.getEntity() instanceof EntityPlayer)) { return; }
    	EntityPlayer player = (EntityPlayer) event.getEntity();
    	if(player.isPotionActive(MobEffects.BLINDNESS)) { return; }
    	if (player.world.provider.getDimensionType()!=DimensionType.OVERWORLD) {
    		return;
    	}
    	event.setRed(event.getRed()+(0.56f-event.getRed())*CreepingNether.miasma);
    	event.setGreen(event.getGreen()+(0.27f-event.getGreen())*CreepingNether.miasma);
    	event.setBlue(event.getBlue()+(0.23f-event.getBlue())*CreepingNether.miasma);
    }
    
    
}
