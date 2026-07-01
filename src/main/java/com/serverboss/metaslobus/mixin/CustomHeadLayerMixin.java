package com.serverboss.metaslobus.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public class CustomHeadLayerMixin<T extends LivingEntity> {

	@Shadow public static void translateToHead(PoseStack arg, boolean bl) {}

	@Inject(
		method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/geom/ModelPart;translateAndRotate(Lcom/mojang/blaze3d/vertex/PoseStack;)V",
			shift = At.Shift.AFTER
		)
	)
	void forceCustomHelmetRendering(PoseStack arg, MultiBufferSource arg2, int i, T entity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
		ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);
		if (!stack.hasTag()) return;

		Item item = stack.getItem();
		if (!(item instanceof ArmorItem)) return;

		CompoundTag tag = stack.getTag();
		if (tag == null) return;

		if (!tag.contains("CustomModelData")) return;

//		int cmd = tag.getInt("CustomModelData");
//		if (cmd % 2 == 0) return;

		translateToHead(arg, entity instanceof Villager || entity instanceof ZombieVillager);
		Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemTransforms.TransformType.HEAD, false, arg, arg2, i);
	}

}
