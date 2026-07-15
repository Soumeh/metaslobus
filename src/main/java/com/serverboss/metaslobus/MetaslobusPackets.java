package com.serverboss.metaslobus;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class MetaslobusPackets {

	public static void register() {
		ServerbossPacketManager.register(new ResourceLocation("svbcr", "shader"), MetaslobusPackets::setShader);
	}

	public static void setShader(FriendlyByteBuf buf) throws IndexOutOfBoundsException {
		ResourceLocation shaderPath = ResourceLocation.tryParse("shaders/post/" + buf.readUtf() + ".json");
		if (shaderPath == null) return;

		Minecraft.getInstance().gameRenderer.loadEffect(shaderPath);
	}

}
