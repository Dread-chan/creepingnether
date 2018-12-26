package com.cutievirus.creepingnether;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@Mod.EventBusSubscriber(modid=CreepingNether.MODID)
public class ClientEvents {

    static Minecraft minecraft = Minecraft.getMinecraft();
    static boolean inbiome = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void eventFog(EntityViewRenderEvent.FogColors event) {
    	if (!Options.skycolor) { return; }
    	if (event.getState().getMaterial().isLiquid()) { return; }
    	if (!(event.getEntity() instanceof EntityPlayer)) { return; }
    	EntityPlayer player = (EntityPlayer) event.getEntity();
    	if(player.isPotionActive(MobEffects.BLINDNESS)) { return; }
    	if (player.world.provider.getDimensionType()!=DimensionType.OVERWORLD) {
    		return;
    	}
    	event.setRed(event.getRed()+(0.56f-event.getRed())*CreepingNether.miasma);
    	event.setGreen(event.getGreen()+(0.27f-event.getGreen())*CreepingNether.miasma);
    	event.setBlue(event.getBlue()+(0.23f-event.getBlue())*CreepingNether.miasma);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
    	if (!Options.skycolor) { return; }
    	if(minecraft.world==null) {return;}
    	if(minecraft.world.getTotalWorldTime()%10!=0) {
    		BlockPos pos = minecraft.player.getPosition();
    		inbiome = minecraft.world.getBiome(pos)==Ref.biomeCreepingNether
    		&& minecraft.world.getBiome(pos.add(2,0,2))==Ref.biomeCreepingNether
    		&& minecraft.world.getBiome(pos.add(-2,0,2))==Ref.biomeCreepingNether
    		&& minecraft.world.getBiome(pos.add(2,0,-2))==Ref.biomeCreepingNether
    		&& minecraft.world.getBiome(pos.add(-2,0,-2))==Ref.biomeCreepingNether;

    	}
    	CreepingNether.miasma += ((inbiome?1f:0f)-CreepingNether.miasma)/20;
    }
}
