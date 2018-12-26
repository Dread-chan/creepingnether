package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.Options;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomePurified extends Biome {

	public BiomePurified(BiomeProperties properties) {
		super(properties);
        spawnableMonsterList.clear();
        //this.spawnableCreatureList.clear();
        //this.spawnableWaterCreatureList.clear();
        if(Options.spawning.ghast) {
        	//this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityGhast.class, 10, 4, 4));
        }
        if(Options.spawning.pigmen) {
        	spawnableMonsterList.add(new Biome.SpawnListEntry(EntityPigman.class, 40, 4, 4));
        }
        if(Options.spawning.magmacube) {
        	spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySlime.class, 8, 4, 4));
        }
        if(Options.spawning.blaze) {
        	spawnableMonsterList.add(new Biome.SpawnListEntry(EntityBlaze.class, 8, 4, 4));
        }
        if(Options.spawning.wither) {
        	spawnableMonsterList.add(new Biome.SpawnListEntry(EntityStray.class, 6, 4, 4));
        }
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntitySpider.class, 100, 4, 4));
        spawnableMonsterList.add(new Biome.SpawnListEntry(EntityCreeper.class, 50, 4, 4));
	}

	@Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos) {
        return 0xc8fc6c;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public int getFoliageColorAtPos(BlockPos pos) {
		//return 0xecb300;
		return 0xffd550;
    }

}
