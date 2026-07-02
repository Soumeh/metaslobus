package com.serverboss.metaslobus.mixin;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

	@Redirect(
		method = "handleResourcePack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/multiplayer/ServerData;getResourcePackStatus()Lnet/minecraft/client/multiplayer/ServerData$ServerPackStatus;"
		)
	)
	private ServerData.ServerPackStatus acceptAllResourcePacks(ServerData instance) {
		return ServerData.ServerPackStatus.ENABLED;
	}

}
