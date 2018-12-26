package com.cutievirus.creepingnether;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cutievirus.creepingnether.entity.Corruptor;
import com.cutievirus.creepingnether.entity.EntityPortal;
import com.cutievirus.creepingnether.entity.NetherFairy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid=CreepingNether.MODID)
public class CommonEvents {

	private static final Random rand = Ref.rand;

	@SubscribeEvent
	public static void usedItem(RightClickBlock event){
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

	@SubscribeEvent
	public static void onPlayerInteract(PlayerInteractEvent event) {
		World world = event.getWorld();
		if(world.provider.getDimensionType()!=DimensionType.NETHER
		||Options.internalcorruption==0) {
			return;
		}
		BlockPos pos = event.getPos().add(
				rand.nextInt(5) - 2,
				rand.nextInt(5) - 2,
				rand.nextInt(5) - 2 );
		Corruptor.instance.DoCorruption(world, pos, new_fairies);
	}

	static protected List<NetherFairy> fairies = new ArrayList<>();
	static protected List<NetherFairy> new_fairies = new ArrayList<>();

	static MinecraftServer server=null;
	static List<EntityPlayerMP> players = null;

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event) {
    	if(players==null) {
    		server = FMLCommonHandler.instance().getMinecraftServerInstance();
    		players = server.getPlayerList().getPlayers();
    	}
    	//server.profiler.startSection("Creeping Tick");
    	if(players.isEmpty()) { return; }
    	for (EntityPlayerMP player : players) {
    		switch(player.world.provider.getDimensionType()) {
        	case NETHER:{ //internal corruption
        		World nether=player.world;
        		if (Corruptor.doesNetherCreep()) {
        			BlockPos pos = player.getPosition().add(
            				rand.nextInt(19) - 9,
            				rand.nextInt(5) - 2,
            				rand.nextInt(19) - 9 );
            		Corruptor.instance.DoCorruption(nether, pos, new_fairies);
            		Corruptor.instance.corruptEntities(nether, pos);
        		}
        		Corruptor.instance.updateFairies(nether,fairies,new_fairies);
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

		//server.profiler.endSection();
    }

}
