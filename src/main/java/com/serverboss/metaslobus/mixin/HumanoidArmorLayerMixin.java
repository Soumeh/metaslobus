package com.serverboss.metaslobus.mixin;

import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {

	@Shadow protected abstract boolean usesInnerModel(EquipmentSlot arg);

//	@Redirect(method = "getArmorResource", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;getArmorTexture(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;Ljava/lang/String;Lnet/minecraft/world/entity/EquipmentSlot;Ljava/lang/String;)Ljava/lang/String;"), remap = false)
//	private String onGetArmorTexture(Entity entity, ItemStack stack, String _default, EquipmentSlot slot, String type) {
//		int cmd = readCustomModelData(stack);
//		if (cmd != 0) {
//			return buildTexturePath(stack, cmd, this.usesInnerModel(slot), type);
//		}
//
//		return ForgeHooksClient.getArmorTexture(entity, stack, _default, slot, type);
//	}

	@Unique
	private static int readCustomModelData(ItemStack stack) {
		if (!stack.hasTag()) return 0;

		Item item = stack.getItem();
		if (!(item instanceof ArmorItem)) return 0;

		CompoundTag tag = stack.getTag();
		if (tag == null) return 0;

		if (!tag.contains("CustomModelData")) return 0;
		return tag.getInt("CustomModelData");
	}

//	@Unique
//	private static String buildTexturePath(ItemStack itemStack, int cmdInt, boolean useInnerModel, @Nullable String type) {
//		String cmdString = String.valueOf(cmdInt);
//		String pathHead;
//		if (isIgnoreMaterial(itemStack) || !(itemStack.getItem() instanceof ArmorItem asArmorItem)) {
//			pathHead = cmdString;
//		} else {
//			String materialName = asArmorItem.getMaterial().getName();
//			pathHead = materialName + "_" + cmdString;
//		}
//		return "aacmd:textures/models/armor/" + pathHead + "_layer_" + (useInnerModel ? 2 : 1) + (type == null ? "" : "_" + type) + ".png";
//	}

}
