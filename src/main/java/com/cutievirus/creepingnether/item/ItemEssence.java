package com.cutievirus.creepingnether.item;

import com.cutievirus.creepingnether.EasyMap;
import com.cutievirus.creepingnether.Ref;
import com.cutievirus.creepingnether.entity.Corruptor;
import com.cutievirus.creepingnether.entity.CorruptorAbstract.BlockForMeta;
import com.cutievirus.creepingnether.entity.CorruptorAbstract.Corruption;
import net.minecraft.block.Block;
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

public class ItemEssence extends ItemSimpleFoiled {
	
	protected SoundEvent sound;
	private boolean purified;
	
	public ItemEssence(String name) {
		this(name, SoundEvents.ENTITY_ZOMBIE_INFECT);
	}
	
	public ItemEssence(String name, SoundEvent sound) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Ref.tabcreepingnether);
		this.sound=sound;
	}
	
	public ItemEssence setPurified(boolean isPurified) {
		this.purified = isPurified;
		return this;
	}
	
	public ItemEssence setSound(SoundEvent sound) {
		this.sound = sound;
		return this;
	}
	
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	boolean transformed = transformBlock(world,pos);
    	if(!transformed) { return EnumActionResult.FAIL; }
    	for (int x=-1; x<=1; x++)for (int y=-1; y<=1; y++)for (int z=-1; z<=1; z++) {
    		if(x==0&&y==0&&z==0) { continue; }
    		transformBlock(world,pos.add(x, y, z));
    	}
    	Corruptor.corruptEntities(world, pos);
    	ItemStack itemstack = player.getHeldItem(hand);
    	itemstack.shrink(1);
    	world.playSound((EntityPlayer)null, pos, this.sound, SoundCategory.BLOCKS, 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
    	return EnumActionResult.SUCCESS;
    }
    
    
    protected boolean transformBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        String blockname = block.getRegistryName().toString();
        Object[] keys = {blockname,block};
        Object corruptto = getCorruptionMap().getFrom(keys);
        if(corruptto instanceof BlockForMeta) {
        	corruptto = ((BlockForMeta)corruptto).apply(block.getMetaFromState(state));
        }
        if(corruptto != null
        ||getCorruptionSpecial().getFrom(keys) != null ) {
        	doCorruption(world, pos);
        	corruptionFinal(world,pos);
        	return true;
        }
        return false;
    }
    
    protected EasyMap<Object> getCorruptionMap(){
    	if(purified) {
    		return Corruptor.corruptionMap;
    	}
    	return Corruptor.corruptionMap;
    }
    protected EasyMap<Corruption> getCorruptionSpecial(){
    	if(purified) {
    		return Corruptor.corruptionSpecial;
    	}
    	return Corruptor.corruptionSpecial;
    }
    protected void doCorruption(World world, BlockPos pos) {
    	if(purified) {
    		Corruptor.DoCorruption(world, pos);
    	}else {
    		Corruptor.DoCorruption(world, pos);
    	}
    }
    protected void corruptEntities(World world, BlockPos pos) {
    	if(purified) {
    		Corruptor.corruptEntities(world, pos, 1d);
    	}else {
    		Corruptor.corruptEntities(world, pos, 1d);
    	}
    }
    protected void corruptionFinal(World world, BlockPos pos) {
    	if(purified) {
    		Corruptor.corruptionFinal(world, pos);
    	}else {
    		Corruptor.corruptionFinal(world, pos);
    	}
    }

}
