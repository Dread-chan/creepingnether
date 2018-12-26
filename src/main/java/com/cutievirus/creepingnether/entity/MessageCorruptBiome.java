package com.cutievirus.creepingnether.entity;

import com.cutievirus.creepingnether.CreepingNether;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageCorruptBiome implements IMessage{

	public MessageCorruptBiome(){}

	public BlockPos pos;
	public int index;

	public MessageCorruptBiome(BlockPos pos, int index){
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
		pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
		index = buf.readInt();
	}

	public static final BiMap<Integer,CorruptorAbstract> corruptorMap;
	public static final BiMap<CorruptorAbstract,Integer> inverseMap;
	static {
		corruptorMap = HashBiMap.create();
		corruptorMap.put(0, Corruptor.instance);
		corruptorMap.put(1, Purifier.instance);
		inverseMap = corruptorMap.inverse();
	}
	public static final int MESSAGERANGE = 1024;

	public static void sendMessage(World world, BlockPos pos, CorruptorAbstract corruptor) {
		int index = inverseMap.getOrDefault(corruptor,0);
		CreepingNether.network.sendToAllAround(new MessageCorruptBiome(pos,index), new TargetPoint(world.provider.getDimension(),pos.getX()+0.5d,pos.getY()+0.5d,pos.getZ()+0.5d, MESSAGERANGE));
	}


	public static class MessageHandlerCorruptBiome implements IMessageHandler<MessageCorruptBiome, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(MessageCorruptBiome message, MessageContext ctx) {
			World world = Minecraft.getMinecraft().world;
			if (world==null) {
				System.out.println("The world is null!");
				return null;
			}
			CorruptorAbstract corruptor = corruptorMap.get(message.index);
			if(corruptor == null) { return null; }
			corruptor.corruptBiome(world, message.pos);
			return null;
		}
	}

}
