package com.jvn.epicaddon.renderer.efmItemRenderer;

import com.jvn.epicaddon.api.anim.BowAtkAnim;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.model.ClientModel;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class GSI_BowRenderer extends RenderItemBase {

    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, MultiBufferSource buffer, PoseStack poseStack, int packedLight) {
        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(stack, entitypatch, hand);

        String joint = "Tool_L";

        DynamicAnimation anim = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer.getAnimation();
        if(anim instanceof BowAtkAnim){
            //joint
        }

        OpenMatrix4f jointTransform = ((ClientModel)entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT)).getArmature().searchJointByName(joint).getAnimatedTransform();
        modelMatrix.mulFront(jointTransform);
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        Minecraft.getInstance().getItemInHandRenderer().renderItem((LivingEntity)entitypatch.getOriginal(), stack, ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, false, poseStack, buffer, packedLight);
        poseStack.popPose();
        GlStateManager._enableDepthTest();
    }

}
