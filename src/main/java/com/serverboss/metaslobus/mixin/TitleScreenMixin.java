package com.serverboss.metaslobus.mixin;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

	@Redirect(
		method = "createNormalMenuOptions",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/screens/TitleScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
			ordinal = 2
		)
	)
	private GuiEventListener cancelRealmsButton(TitleScreen instance, GuiEventListener guiEventListener) {
		return new Button(0, 0, 0, 0, Component.nullToEmpty(null), b -> {});
	}

	@ModifyArg(
		method = "init",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/components/Button;<init>(IIIILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)V"
		),
		index = 2,
		slice = @Slice(
			to = @At(
				value = "INVOKE",
				target = "Lnet/minecraftforge/client/gui/NotificationModUpdateScreen;init(Lnet/minecraft/client/gui/screens/TitleScreen;Lnet/minecraft/client/gui/components/Button;)Lnet/minecraftforge/client/gui/NotificationModUpdateScreen;"
			)
		)
	)
	private int widenModsButton(int width) {
		return 200;
	}

}
