package com.cutievirus.creepingnether;

import com.cutievirus.creepingnether.block.BlockCreepingBlock;
import com.cutievirus.creepingnether.block.BlockModSlab;
import com.cutievirus.creepingnether.block.StateMapperCreepingBlock;
import com.cutievirus.creepingnether.entity.CreepingMessage;
import com.cutievirus.creepingnether.entity.CreepingMessage.CreepingMessageHandler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy{
	
	private static StateMapperCreepingBlock stateMapperCreepingBlock = new StateMapperCreepingBlock();

	public void preInit(){
		super.preInit();
		CreepingNether.network.registerMessage(CreepingMessageHandler.class, CreepingMessage.class, 0, Side.CLIENT);
	}
	public void init(){
		super.init();
	}
	public void postInit(){
		super.postInit();
	}
	
	public void registerModels() {
		registerCreepingBlock(Ref.creepingobsidian);
		registerBlock(Ref.bloodstone);
		registerBlock(Ref.bloodstone_stairs);
		registerBlock(Ref.bloodstone_slab);
		registerBlock(Ref.bloodstone_slab2);
		registerBlock(Ref.charwood);
		registerBlock(Ref.charwood_planks);
		registerBlock(Ref.charwood_stairs);
		registerBlock(Ref.charwood_slab);
		registerBlock(Ref.charwood_slab2);
		registerBlock(Ref.soulstone);
		registerBlock(Ref.soulstone_stairs);
		registerBlock(Ref.soulstone_slab);
		registerBlock(Ref.soulstone_slab2);
		registerBlock(Ref.soulstone_charged);
		registerBlock(Ref.soulstone_crystal);
		
		registerBlock(Ref.nethercoal);
		registerBlock(Ref.netheriron);
		registerBlock(Ref.nethergold);
		registerBlock(Ref.netherdiamond);
		registerBlock(Ref.netheremerald);
		registerBlock(Ref.netherlapis);
		registerBlock(Ref.netherredstone);
		
		registerItem(Ref.netheressence);
		registerItem(Ref.purifiedessence);
	}
	
	private void registerItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private void registerBlock(Block block){
		Item item = Item.getItemFromBlock(block);
		registerItem(item);
	}
	private void registerCreepingBlock(BlockCreepingBlock block){
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.itemModel, "inventory"));
		ModelLoader.setCustomStateMapper(block, stateMapperCreepingBlock);
	}
	private final StateMap stateMapper_slab = new StateMap.Builder().ignore(BlockModSlab.VARIANT).build();
	private final StateMap stateMapper_slab_double = new StateMap.Builder().ignore(BlockModSlab.VARIANT,BlockModSlab.HALF).build();
	private void registerBlock(BlockModSlab block){
		if(block.isDouble()) {
			ModelLoader.setCustomStateMapper(block, stateMapper_slab_double);
		}else {
			registerBlock((Block)block);
			ModelLoader.setCustomStateMapper(block, stateMapper_slab);
		}
	}
}
