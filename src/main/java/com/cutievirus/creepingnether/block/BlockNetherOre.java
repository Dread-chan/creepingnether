package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Ref;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class BlockNetherOre extends Block{
	
	private Block baseore;
	private Item dropitem;
	private int mindrop, maxdrop, minxp, maxxp, dropdamage;
	protected double essenceChance = 0;
	public Item item;

	public BlockNetherOre(String name,Block baseore, Item dropitem, int harvestlevel, int mindrop, int maxdrop, int minxp, int maxxp){
		super(Material.ROCK, MapColor.NETHERRACK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setTickRandomly(true);
		setHarvestLevel("pickaxe",harvestlevel);
		setHardness(3.0F).setResistance(5.0F);
		setCreativeTab(Ref.tabcreepingnether);
		this.baseore=baseore;
		this.dropitem=dropitem;
		this.dropdamage=0;
		this.mindrop=mindrop;
		this.maxdrop=maxdrop;
		this.minxp=minxp;
		this.maxxp=maxxp;
		
		this.item = new ItemBlock(this);
		item.setRegistryName(name);
	}
	
	public BlockNetherOre setDamageDropped(int value){
		dropdamage=value;
		return this;
	}
	
	public BlockNetherOre setEssence(double chance) {
		this.essenceChance=chance;
		return this;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		BlockPos pos2 = pos.add(rand.nextInt(3)-1,rand.nextInt(3)-1,rand.nextInt(3)-1);
		if(canCorrupt(world.getBlockState(pos2))){
			world.setBlockState(pos2, this.getDefaultState());
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
            	spawnAsEntity(world, pos, new ItemStack(Ref.netheressence));
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
