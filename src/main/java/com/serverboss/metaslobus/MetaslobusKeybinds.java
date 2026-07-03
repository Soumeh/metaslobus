package com.serverboss.metaslobus;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class MetaslobusKeybinds {

	private static final Minecraft MINECRAFT = Minecraft.getInstance();

	public static void register() {
		ClientRegistry.registerKeyBinding(CUSTOM_ALPHA_KEY);
		ClientRegistry.registerKeyBinding(CUSTOM_BETA_KEY);
		ClientRegistry.registerKeyBinding(CUSTOM_GAMMA_KEY);
		ClientRegistry.registerKeyBinding(CUSTOM_DELTA_KEY);
		ClientRegistry.registerKeyBinding(CUSTOM_EPSILON_KEY);
	}

	public static final KeyMapping CUSTOM_ALPHA_KEY = new InclusiveKeyMapping(
		"key.metaslobus.custom.alpha",
		InputConstants.Type.MOUSE,
		GLFW.GLFW_MOUSE_BUTTON_4
	);

	public static final KeyMapping CUSTOM_BETA_KEY = new InclusiveKeyMapping(
		"key.metaslobus.custom.beta",
		InputConstants.Type.MOUSE,
		GLFW.GLFW_MOUSE_BUTTON_5
	);

	public static final KeyMapping CUSTOM_GAMMA_KEY = new InclusiveKeyMapping(
		"key.metaslobus.custom.gamma",
		InputConstants.KEY_G
	);

	public static final KeyMapping CUSTOM_DELTA_KEY = new InclusiveKeyMapping(
		"key.metaslobus.custom.delta",
		InputConstants.KEY_H
	);

	public static final KeyMapping CUSTOM_EPSILON_KEY = new InclusiveKeyMapping(
		"key.metaslobus.custom.epsilon",
		InputConstants.KEY_J
	);

	public static final Map<String, KeyMapping> KEY_MAPPINGS = Map.of(
		"alpha", CUSTOM_ALPHA_KEY,
		"beta", CUSTOM_BETA_KEY,
		"gamma", CUSTOM_GAMMA_KEY,
		"delta", CUSTOM_DELTA_KEY,
		"epsilon", CUSTOM_EPSILON_KEY
	);

	@SubscribeEvent
	public static void onKeyInput(InputEvent.KeyInputEvent event) {
		ClientPacketListener connection = MINECRAFT.getConnection();
		if (connection == null) return;

		boolean pressedSpace = event.getAction() == GLFW.GLFW_PRESS
			&& event.getKey() == MINECRAFT.options.keyJump.getKey().getValue()
			&& MINECRAFT.screen == null;
		if (pressedSpace) {
			connection.send(ServerbossCustomPacket.of("keybind.jump"));
		}

		KEY_MAPPINGS.forEach((key, mapping) -> {
			if (mapping.consumeClick()) {
				connection.send(ServerbossCustomPacket.of("keybind." + key));
			}
		});
	}
}
