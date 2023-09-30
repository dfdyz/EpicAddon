package com.jvn.epicaddon.renderer.Item;

import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import com.jvn.epicaddon.capability.JointLinker.AnimatedItemMgr;
import com.jvn.epicaddon.register.RegItems;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class AnimatedItemRender extends RenderItemBase {

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, HumanoidArmature armature, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        AnimatedItemMgr itemMgr = EpicAddonCapabilities.getAnimatedItem(entitypatch);


        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(stack, entitypatch, hand);
        boolean isInMainhand = hand == InteractionHand.MAIN_HAND;
        Joint holdingHand = isInMainhand ? armature.toolR : armature.toolL;
        OpenMatrix4f jointTransform = poses[holdingHand.getId()];

        modelMatrix.mulFront(jointTransform);

        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);

        if (itemMgr != null && itemMgr.isEnabled(stack)){
            RegItems.AnimatedItems.AnimatedItemModel model = itemMgr.getCurrModel();

            model.getArmature().getPose(0.5f);

        }
        else {
            ItemTransforms.TransformType transformType = isInMainhand ? ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND : ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
            Minecraft.getInstance().getItemInHandRenderer().renderItem((LivingEntity)entitypatch.getOriginal(), stack, transformType, !isInMainhand, poseStack, buffer, packedLight);
        }

        poseStack.popPose();
    }
}
