package com.serverboss.metaslobus;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ServerbossPacketManager {

	public static final Map<ResourceLocation, Consumer<FriendlyByteBuf>> CONSUMERS = new HashMap<>();

	public static void register(ResourceLocation location, Consumer<FriendlyByteBuf> consumer) {
		CONSUMERS.put(location, consumer);
	}

	public static void processCustomPacket(ResourceLocation id, FriendlyByteBuf buf) {
		CONSUMERS.forEach((location, consumer) -> {
			if (id.equals(location)) try {
				consumer.accept(buf);
			} catch (Exception ignored) {} finally {
				if (buf != null) buf.release();
			}
		});
	}

}
