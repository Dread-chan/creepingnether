package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.CreepingNether;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CreepingMessage implements IMessage{
	
	public CreepingMessage(){}
	
	public BlockPos pos;
	public int index;
	
	public CreepingMessage(BlockPos pos, int index){
		this.pos=pos;
		this.index=index;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(index);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
		this.index = buf.readInt();
	}
	
	public static final int MESSAGERANGE = 512;
	public static final int CORRUPTBIOME = 0;
	public static void sendMessage(World world, BlockPos pos, int index) {
		CreepingNether.network.sendToAllAround(new CreepingMessage(pos,index), new TargetPoint(world.provider.getDimension(),pos.getX()+0.5d,pos.getY()+0.5d,pos.getZ()+0.5d, MESSAGERANGE));
	}

	
	public static class CreepingMessageHandler implements IMessageHandler<CreepingMessage, IMessage> {

		@Override
		public IMessage onMessage(CreepingMessage message, MessageContext ctx) {
			//EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			//EntityPlayer player = Minecraft.getMinecraft().player;
			World world = Minecraft.getMinecraft().world;
			if (world==null) {
				System.out.println("The world is null!");
				return null;
			}
			//Fairy.onMessage(world, message.pos, message.index);
			switch(message.index) {
			case CORRUPTBIOME:
				EntityPortal.corruptBiome(world,message.pos);
				break;
			}
			return null;
		}
	}
	
}
