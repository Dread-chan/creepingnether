package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.ItemEssence;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class BlockNetherOre extends BlockModBlock{
	
	protected Block baseore;
	protected Item dropitem;
	protected int mindrop, maxdrop, minxp, maxxp, dropdamage;
	protected double essenceChance = 0;
	
	public BlockNetherOre(String name, Material material, MapColor mapColor) {
		super(name,material,mapColor);
		setTickRandomly(true);
		setHardness(3.0F).setResistance(5.0F);
	}
	public BlockNetherOre(String name) {
		this(name, Material.ROCK, MapColor.NETHERRACK);
	}
	public BlockNetherOre(String name, int harvestlevel) {
		this(name);
		setHarvestLevel("pickaxe",harvestlevel);
	}
	public BlockNetherOre(String name, Block baseore, int harvestlevel) {
		this(name,harvestlevel);
		this.baseore=baseore;
	}
	public BlockNetherOre(String name,Block baseore, Item dropitem, int harvestlevel, int mindrop, int maxdrop, int minxp, int maxxp){
		this(name,baseore,harvestlevel);
		setXp(minxp,maxxp);
		setDrops(dropitem,mindrop,maxdrop,0);
	}
	
	public static class BlockHallowOre extends BlockNetherOre {

		public BlockHallowOre(String name) {
			super(name,Material.ROCK,MapColor.SILVER_STAINED_HARDENED_CLAY);
			setHardness(3.0F).setResistance(10.0F);
		}public BlockHallowOre(String name, int harvestlevel) {
			this(name);
			setHarvestLevel("pickaxe",harvestlevel);
		}public BlockHallowOre(String name, Block baseore, int harvestlevel) {
			this(name,harvestlevel);
			this.baseore=baseore;
		}
		
		@Override
		public ItemEssence getEssence() {
			return Ref.purifiedessence;
		}
		
		@Override
		public BlockHallowOre copyOre(BlockNetherOre source) {
			super.copyOre(source);
			setBase(source);
			return this;
		}
	}
	
	public BlockNetherOre copyOre(BlockNetherOre source) {
		setXp(source.minxp,source.maxxp);
		setDrops(source.dropitem,source.mindrop,source.maxdrop,source.dropdamage);
		this.baseore=source.baseore;
		setEssenceChance(source.essenceChance);
		return this;
	}

	public BlockNetherOre setBase(Block base) {
		this.baseore = base;
		return this;
	}
	
	public BlockNetherOre setXp(int minxp, int maxxp) {
		this.minxp=minxp;
		this.maxxp=maxxp;
		return this;
	}

	public BlockNetherOre setEssenceChance(double chance) {
		this.essenceChance=chance;
		return this;
	}
	
	public ItemEssence getEssence() {
		return Ref.netheressence;
	}
	
	public BlockNetherOre setDrops(Item dropitem, int mindrop, int maxdrop, int damage){
		setDrops(dropitem,mindrop,maxdrop);
		setDamageDropped(damage);
		return this;
	}
	public BlockNetherOre setDrops(Item dropitem, int mindrop, int maxdrop){
		this.dropitem=dropitem;
		this.mindrop=mindrop;
		this.maxdrop=maxdrop;
		return this;
	}
	
	public BlockNetherOre setDamageDropped(int damage){
		dropdamage=damage;
		return this;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		BlockPos pos2 = pos.add(rand.nextInt(3)-1,rand.nextInt(3)-1,rand.nextInt(3)-1);
		if(canCorrupt(world.getBlockState(pos2))){
			getEssence().corruptBlock(world, pos2);
		}
	}
	
	public boolean canCorrupt(IBlockState state){
		return state.getBlock()==baseore;
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
    	return dropitem;
    }
	@Override
    public int damageDropped(IBlockState state){
        return dropdamage;
    }
	
	@Override
    public int quantityDropped(Random rand){
        return rand.nextInt(1+maxdrop-mindrop)+mindrop;
    }
	@Override
    public int quantityDroppedWithBonus(int fortune, Random rand){
		int i = rand.nextInt(fortune + 2);
		if (i<1){ i=1; }
		return this.quantityDropped(rand)*i;
    }
	
	//drop experience
	@Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune){
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

        if (this.getItemDropped(state, world.rand, fortune) != Item.getItemFromBlock(this)){
        	// xp
            this.dropXpOnBlockBreak(world, pos, getXpCount(world.rand));
            // essence
            if(world.provider.getDimensionType() != DimensionType.NETHER
            && world.rand.nextDouble() < this.essenceChance) {
            	spawnAsEntity(world, pos, new ItemStack(getEssence()));
            }
        }
    }
	public int getXpCount(Random rand){
		return rand.nextInt(1+maxxp-minxp)+minxp;
	}
	
	@Override
    public ItemStack getItem(World world, BlockPos pos, IBlockState state){
        return new ItemStack(this);
    }
}
