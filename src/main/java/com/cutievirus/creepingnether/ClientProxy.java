package com.cutievirus.creepingnether;

import java.awt.Color;

import com.cutievirus.creepingnether.block.BlockCreepingBlock;
import com.cutievirus.creepingnether.block.BlockModSlab;
import com.cutievirus.creepingnether.block.StateMapperCreepingBlock;
import com.cutievirus.creepingnether.entity.EntityPigman;
import com.cutievirus.creepingnether.entity.RenderPigman;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	static final Minecraft minecraft = Minecraft.getMinecraft();

	@Override
	public void preInit(){
		super.preInit();
		RenderingRegistry.registerEntityRenderingHandler(EntityPigman.class, new IRenderFactory<EntityPigman>() {
			@Override
			public Render<EntityPigman> createRenderFor(RenderManager manager) {
				return new RenderPigman(manager);
			}
		});
	}
	@Override
	public void init(){
		super.init();
		registerColorHandlers();
	}
	@Override
	public void postInit(){
		super.postInit();
	}

	@Override
	public void registerModels() {

		for (Block block:Ref.blockList) {
			if (block instanceof BlockCreepingBlock) {
				registerCreepingBlock((BlockCreepingBlock)block);
			}else if (block instanceof BlockModSlab) {
				registerSlab((BlockModSlab)block);
			}else if (block instanceof BlockLeaves) {
				registerLeaves((BlockLeaves)block);
			}else {
				registerBlock(block);
			}
		}
		for(Item item:Ref.itemList) {
			registerItem(item);
		}
	}

	private void registerItem(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	private void registerBlock(Block block){
		Item item = Item.getItemFromBlock(block);
		registerItem(item);
	}

	private static final StateMapperCreepingBlock stateMapperCreepingBlock = new StateMapperCreepingBlock();
	private void registerCreepingBlock(BlockCreepingBlock block){
		Item item = Item.getItemFromBlock(block);
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.itemModel, "inventory"));
		ModelLoader.setCustomStateMapper(block, stateMapperCreepingBlock);
	}

	private static final StateMap stateMapper_slab = new StateMap.Builder().ignore(BlockModSlab.VARIANT).build();
	private static final StateMap stateMapper_slab_double = new StateMap.Builder().ignore(BlockModSlab.VARIANT,BlockModSlab.HALF).build();
	private void registerSlab(BlockModSlab block){
		ModelLoader.setCustomStateMapper(block.getFullVersion(), stateMapper_slab_double);
		registerBlock((Block)block.getHalfVersion());
		ModelLoader.setCustomStateMapper(block.getHalfVersion(), stateMapper_slab);
	}

	private static final StateMap stateMapper_leaves = new StateMap.Builder().ignore(BlockLeaves.CHECK_DECAY).ignore(BlockLeaves.DECAYABLE).build();
	private void registerLeaves(BlockLeaves block) {
		registerBlock(block);
		ModelLoader.setCustomStateMapper(block, stateMapper_leaves);
	}

    public void registerColorHandlers() {
    	BlockColors blockColors = minecraft.getBlockColors();
    	ItemColors itemColors = minecraft.getItemColors();
    	blockColors.registerBlockColorHandler(new IBlockColor(){
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
				return world==null||pos==null
						//?ColorizerGrass.getGrassColor(0.5D, 1.0D)
						?0xc8fc6c
						:BiomeColorHelper.getGrassColorAtPos(world, pos);
			}
    	}, Ref.hallowgrass);
    	itemColors.registerItemColorHandler(new IItemColor(){
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				return 0xc8fc6c;
			}
    	}, Ref.hallowgrass.item);
    	blockColors.registerBlockColorHandler(new IBlockColor(){
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
				if (world==null||pos==null) { return Color.WHITE.hashCode(); }
				Color color = new Color(BiomeColorHelper.getFoliageColorAtPos(world, pos));
				return color.brighter().brighter().hashCode();
			}
    	}, Ref.goldleaves);

    }
}
