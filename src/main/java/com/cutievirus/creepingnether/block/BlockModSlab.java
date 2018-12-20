package com.cutievirus.creepingnether.block;

import java.util.Random;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.item.ModItemSlab;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockModSlab extends BlockSlab{
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	public Item item;
	protected String name;
	protected Block base;
	protected Block halfVersion;
	protected Block fullVersion;
	
	public BlockModSlab(BlockModBlock base, String name, boolean isDouble){
		super(base.getMaterial(), base.getMapColor());
		this.name=name;
		this.base=base;
		setUnlocalizedName(name+"_slab");
		setRegistryName(name+"_slab"+(isDouble?"_double":""));
		this.fullBlock = isDouble;
		
		base.getDefaultState();
        this.setHardness(base.getHardness());
        this.setResistance(base.getResistance());
        @SuppressWarnings("deprecation")
		SoundType soundType = base.getSoundType();
        this.setSoundType(soundType);
        this.setCreativeTab(Ref.tabcreepingnether);
		
		setTickRandomly(base.getTickRandomly());
	}
	
	public static void createItem(BlockModSlab slab1, BlockModSlab slab2) {
		BlockModSlab.createItem(slab1,slab2,0);
	}
	
	public static void createItem(BlockModSlab slab1, BlockModSlab slab2, int burnTime) {
		slab1.item = new ModItemSlab(slab1,slab1,slab2,burnTime); 
		slab1.item.setRegistryName(slab1.name+"_slab");
		slab2.item = slab1.item;
		slab1.halfVersion=slab2.halfVersion=slab1;
		slab1.fullVersion=slab2.fullVersion=slab2;
	}
	
	public Block getHalfVersion() {
		return this.halfVersion;
	}
	public Block getFullVersion() {
		return this.fullVersion;
	}
	
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.item;
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(this.item, 1, this.damageDropped(state));
    }

	@Override
	public String getUnlocalizedName(int meta) {
		return getUnlocalizedName();
	}

	@Override
	public boolean isDouble() {
		return this.fullBlock;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return EnumType.NORMAL;
	}
	
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = this.getDefaultState();
        if (!this.isDouble()) {
            state = state.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }
        return state;
    }
    
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }
        return i;
    }
    
    protected BlockStateContainer createBlockState() {
    	return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }
	
    public static enum EnumType implements IStringSerializable {
        NORMAL(0, "normal");

        private final String name;

        private EnumType(int meta, String name) {
            this.name = name;
        }

        public int getMetadata()  {
            return 0;
        }

        public static EnumType byMetadata(int meta) {
            return EnumType.NORMAL;
        }
        public String toString() {
            return this.name;
        }
        public String getName() {
            return this.name;
        }
        public String getUnlocalizedName() {
            return this.name;
        }
    }
    
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        base.updateTick(worldIn, pos, state, rand);
    }
    
	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
		base.onEntityWalk(world, pos, entity);
    }
	
	@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity){
    	base.onEntityCollidedWithBlock(world, pos, state, entity);
    }
	
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
    	base.addDestroyEffects(world,pos,manager);
    	return false;
    }
	
}
