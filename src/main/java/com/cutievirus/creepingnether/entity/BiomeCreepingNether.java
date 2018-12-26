package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.Options;
import com.cutievirus.creepingnether.CreepingNether;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeCreepingNether extends Biome {

	public BiomeCreepingNether(BiomeProperties properties) {
		super(properties);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        if(Options.spawning.ghast) {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 15, 4, 4));
        }
        if(Options.spawning.pigmen) {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigZombie.class, 40, 4, 4));
        }
        if(Options.spawning.magmacube) {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityMagmaCube.class, 8, 4, 4));
        }
        if(Options.spawning.blaze) {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBlaze.class, 8, 4, 4));
        }
        if(Options.spawning.wither) {
        	this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitherSkeleton.class, 6, 4, 4));
        }
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCaveSpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCreeper.class, 50, 4, 4));
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityWitch.class, 5, 1, 1));
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos) {
        return 0x726c31;
    	//return 0x3f403e;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos) {
        //return 0x726c31;
    	//return 0x3f403e;
		return 0x654F2A;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float currentTemperature) {
		int color = super.getSkyColorByTemp(currentTemperature);
		if (!Options.skycolor) { return color; }
		int red = color>>16;
		int green = color>>8&255;
		int blue = color&255;
		red = (int) (red+(0xa4-red)*CreepingNether.miasma);
		green = (int) (green+(0x71-green)*CreepingNether.miasma);
		blue = (int) (blue+(0x71-blue)*CreepingNether.miasma);
		return red<<16|green<<8|blue;
		
        //return 0xce7b7b;
		//return 0x86add0;
		//return 0x9888ff;
    }
    
}
