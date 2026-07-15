package com.serverboss.metaslobus.mixin;

import com.serverboss.metaslobus.ServerbossPacketManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

	@Shadow @Final private Minecraft minecraft;

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

	@Inject(
		method = "handleCustomPayload",
		at = @At("TAIL")
	)
	private void handleServerbossPayload(ClientboundCustomPayloadPacket packet, CallbackInfo ci) {
		PacketUtils.ensureRunningOnSameThread(packet, (ClientPacketListener)(Object)this, this.minecraft);
		ResourceLocation id = packet.getIdentifier();
		FriendlyByteBuf buf = packet.getData();

		ServerbossPacketManager.processCustomPacket(id, buf);
	}

}
