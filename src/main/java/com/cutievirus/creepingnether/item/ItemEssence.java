package com.cutievirus.creepingnether.item;

import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.Corruptor;
import com.cutievirus.creepingnether.entity.CorruptorAbstract;
import com.cutievirus.creepingnether.entity.Purifier;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemEssence extends ItemSimpleFoiled implements IModItem {
	
	protected SoundEvent sound;
	private boolean purified=false;
	public CorruptorAbstract corruptor = Corruptor.instance;
	
	public ItemEssence(String name) {
		this(name, SoundEvents.ENTITY_ZOMBIE_INFECT);
	}
	
	public ItemEssence(String name, SoundEvent sound) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);
		this.sound=sound;
	}
	
	@Override
	public ItemEssence setBurnTime(int time) { return this; }
	@Override
	public ItemEssence setBurnCount(float count) { return this; }
	@Override
	public int getBurnTime() { return 0; }
	
	public ItemEssence setPurified(boolean isPurified) {
		purified = isPurified;
		if(purified) {
			corruptor = Purifier.instance;
		}else {
			corruptor = Corruptor.instance;
		}
		return this;
	}
	
	public ItemEssence setSound(SoundEvent sound) {
		this.sound = sound;
		return this;
	}
	
	public static boolean using = false;
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	boolean transformed = transformBlock(world,pos);
    	if(!transformed) { return EnumActionResult.FAIL; }
    	if(world.isRemote) { return EnumActionResult.SUCCESS; }
    	using=true;
    	for (int x=-1; x<=1; x++)for (int y=-1; y<=1; y++)for (int z=-1; z<=1; z++) {
    		if(x==0&&y==0&&z==0) { continue; }
    		transformBlock(world,pos.add(x, y, z));
    	}
    	corruptor.corruptEntities(world, pos);
    	ItemStack itemstack = player.getHeldItem(hand);
    	itemstack.shrink(1);
    	world.playSound((EntityPlayer)null, pos, this.sound, SoundCategory.BLOCKS, 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
    	using=false;
    	return EnumActionResult.SUCCESS;
    }
    
    
    protected boolean transformBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Object[] keys = CorruptorAbstract.getKeys(state);
        IBlockState corruptto = corruptor.getCorruptState(world,pos);
        if(corruptto != null
        ||corruptor.getCorruptionSpecial().getFrom(keys) != null ) {
        	if(world.isRemote) { return true; }
        	corruptBlock(world,pos);
        	return true;
        }
        return false;
    }
    
    public void corruptBlock(World world, BlockPos pos) {
    	corruptor.DoCorruption(world, pos);
    	corruptor.corruptionFinal(world,pos);
    }

}
