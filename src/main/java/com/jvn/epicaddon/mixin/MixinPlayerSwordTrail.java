package com.jvn.epicaddon.mixin;


import com.jvn.epicaddon.renderer.SwordTrail.IAnimST;
import com.jvn.epicaddon.renderer.SwordTrail.MutiSwordTrail;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.ConfigVal;
import com.jvn.epicaddon.resources.config.RenderConfig;
import com.jvn.epicaddon.tools.Trail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


@Mixin(value = PatchedItemInHandLayer.class, remap = false)
public abstract class MixinPlayerSwordTrail<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, RenderLayer<E, M>> {

    @Inject(at = @At("TAIL"),method = "renderLayer")
    public void EpicAddon_renderLayer(T entitypatch, E entityliving, RenderLayer<E, M> originalRenderer, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn, OpenMatrix4f[] poses, float netYawHead, float pitchHead, float partialTicks, CallbackInfo callbackInfo){
        if(ClientConfig.cfg.EnableSwordTrail){
            Minecraft mc = Minecraft.getInstance();
            poseStack.pushPose();
            for (Layer.Priority priority : Layer.Priority.values()) {
                AnimationPlayer animPlayer = entitypatch.getClientAnimator().getCompositeLayer(priority).animationPlayer;
                float playTime = animPlayer.getElapsedTime();
                DynamicAnimation animation = animPlayer.getAnimation();

                if(animation instanceof AttackAnimation){
                    renderTrail(poseStack, buffer, entitypatch, playTime, partialTicks,(AttackAnimation)animation);
                }
                //animPlayer.getAnimation().renderDebugging(poseStack, buffer, entitypatch, playTime, partialTicks);
            }
            poseStack.popPose();
        }
    }
    Trail t1,t2;
    private void renderTrail(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, float playTime, float partialTicks, AttackAnimation animation){
        t1 = null;
        t2 = null;

        //Modifier
        if(((IAnimST)animation).isSpecial()){
            Trail tt1 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString());
            Trail tt2 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString());
            Trail tm = ((IAnimST)animation).getTrail();
            if(tt1 != null) t1 = new Trail(tt1,tm);
            if(tt2 != null) t2 = new Trail(tt2,tm);
        }
        else{
            t1 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString());
            t2 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.OFF_HAND).getItem().getRegistryName().toString());
        }

        MutiSwordTrail Trail = new MutiSwordTrail(300);
        Trail.draw(poseStack, buffer, entitypatch, animation, 0.0f, playTime, partialTicks, animation.getPlaySpeed(entitypatch), t1, t2);
    }

}

