package com.cutievirus.creepingnether.block;

import java.util.Random;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNetherRedstone extends BlockNetherOre{
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant",EnumType.class);

	public BlockNetherRedstone(){
		super("netherredstone", Blocks.REDSTONE_ORE, Items.REDSTONE,1, 4,6, 1,5);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.LIT));
	}
	
	@Override
	public boolean canCorrupt(IBlockState state){
		return state.getBlock()==Blocks.REDSTONE_ORE || state.getBlock()==Blocks.LIT_REDSTONE_ORE;
	}
	
	@Override
    public int quantityDroppedWithBonus(int fortune, Random rand){
        return this.quantityDropped(rand) + rand.nextInt(fortune + 1);
    }
	
	@Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
		super.updateTick(world, pos, state, rand);
        if (state.getValue(VARIANT)==EnumType.LIT){
            world.setBlockState(pos, state.withProperty(VARIANT, EnumType.UNLIT));
        }
    }
	
	@Override
	public int getLightValue(IBlockState state) {
		return state.getValue(VARIANT)==EnumType.LIT ? 9 : 0;
	}
	
	@Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
		if (state.getValue(VARIANT)==EnumType.LIT){
            this.spawnParticles(world, pos);
        }
    }
	@Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer playerIn){
        this.activate(world, pos);
        super.onBlockClicked(world, pos, playerIn);
    }
	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity){
        this.activate(world, pos);
        super.onEntityWalk(world, pos, entity);
    }
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY){
        this.activate(world, pos);
        return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY);
    }
	
    private void activate(World world, BlockPos pos){
        this.spawnParticles(world, pos);

        if (world.getBlockState(pos).getValue(VARIANT)==EnumType.UNLIT){
        	world.setBlockState(pos, getDefaultState());
        }
    }
    
    private void spawnParticles(World world, BlockPos pos)
    {
        Random rand = world.rand;
        double d0 = 0.0625D;
        int x=pos.getX()
        ,y=pos.getY()
        ,z=pos.getZ();

        for (int i = 0; i < 6; ++i)
        {
            double dx = x + rand.nextFloat();
            double dy = y + rand.nextFloat();
            double dz = z + rand.nextFloat();
            switch(i){
            case 0: // up
            	if(!world.getBlockState(pos.up()).isOpaqueCube()) dy=y+d0+1D;
            	break;
            case 1: //down
            	if(!world.getBlockState(pos.down()).isOpaqueCube()) dy=y-d0;
            	break;
            case 2: //south
            	if(!world.getBlockState(pos.south()).isOpaqueCube()) dz=z+d0+1D;
            	break;
            case 3: //north
            	if(!world.getBlockState(pos.north()).isOpaqueCube()) dz=z-d0;
            	break;
            case 4: //east
            	if(!world.getBlockState(pos.east()).isOpaqueCube()) dx=x+d0+1D;
            	break;
            case 5: //west
            	if(!world.getBlockState(pos.west()).isOpaqueCube()) dx=x-d0;
            	break;
            }

            if(dx<x||dx>x+1 || dy<y||dy>y+1 || dz<z||dz>z+1){//if particle is outside block
                world.spawnParticle(EnumParticleTypes.REDSTONE, dx, dy, dz, 0D, 0D, 0D, new int[0]);
            }
        }
    }
	
	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state){
		return state.getValue(VARIANT).getMetadata();
	}
	
	public static enum EnumType implements IStringSerializable
	{
		LIT(0,"lit"),
		UNLIT(1,"unlit");
		
		
		private final int meta;
		private final String name;
		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		
		private EnumType(int meta, String name){
			this.meta=meta;
			this.name=name;
		}
		
		@Override
		public String getName(){
			return this.name;
		}
		@Override
		public String toString(){
			return getName();
		}
		
		public int getMetadata(){
			return this.meta;
		}
		
		public static EnumType byMetadata(int meta){
			if (meta<0 || meta >= META_LOOKUP.length){
				meta=0;
			}
			return META_LOOKUP[meta];
		}
		
	    static
	    {
	      for (EnumType type : values()) {
	        META_LOOKUP[type.getMetadata()] = type;
	      }
	    }
	}
}
