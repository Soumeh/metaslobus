package com.serverboss.metaslobus.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity> {

	@Unique private static final List<Item> GUN_ITEMS = List.of(
		Items.LEATHER_HORSE_ARMOR,
		Items.IRON_HORSE_ARMOR,
		Items.GOLDEN_HORSE_ARMOR,
		Items.DIAMOND_HORSE_ARMOR
	);

	@Unique private static final List<Integer> BLACKLISTED_CMD = List.of(
		13374334, 13374401, 13374402
	);

	@Shadow @Final public ModelPart head;
	@Shadow @Final public ModelPart leftArm;
	@Shadow @Final public ModelPart rightArm;

	@Inject(
		method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/world/entity/LivingEntity;F)V"
		)
	)
	void test(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		ItemStack stack = ItemStack.EMPTY;
		for (InteractionHand hand : InteractionHand.values()) {
			ItemStack possibleStack = entity.getItemInHand(hand);
			if (GUN_ITEMS.contains(possibleStack.getItem())) {
				stack = possibleStack;
				break;
			}
		}
		if (!GUN_ITEMS.contains(stack.getItem())) return;

		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains("CustomModelData")) {
			int cmd = tag.getInt("CustomModelData");
			if (BLACKLISTED_CMD.contains(cmd)) return;
		}

		this.rightArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
		this.leftArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;

		boolean rightHanded = entity.getMainArm() == HumanoidArm.RIGHT;
		if (rightHanded) {
			this.rightArm.yRot = -0.1F + this.head.yRot;
			this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
		} else {
			this.rightArm.yRot = -0.1F + this.head.yRot - 0.4F;
			this.leftArm.yRot = 0.1F + this.head.yRot;
		}
	}

}
