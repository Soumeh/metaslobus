package com.serverboss.metaslobus;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ServerboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;

public class ServerbossCustomPacket {

	public static ServerboundCustomPayloadPacket of(String name) {
		return of(name, new FriendlyByteBuf(Unpooled.EMPTY_BUFFER));
	}

	public static ServerboundCustomPayloadPacket of(String name, String data) {
		FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
		buf.writeUtf(data);
		return of(name, buf);
	}

	public static ServerboundCustomPayloadPacket of(String name, FriendlyByteBuf buf) {
		return new ServerboundCustomPayloadPacket(new ResourceLocation("svbcr", name), buf);
	}

}
