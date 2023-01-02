package com.jvn.epicaddon.mixin;


import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


@Mixin(value = PatchedItemInHandLayer.class, remap = false)
public abstract class MixinPlayerSwordTrail<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>> extends PatchedLayer<E, T, M, RenderLayer<E, M>> {

    /*
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
    Trail t1 = new Trail(),t2 = new Trail();
    MutiSwordTrail tr = new MutiSwordTrail(300);
    private void renderTrail(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, float playTime, float partialTicks, AttackAnimation animation){

        //Modifier
        if(((IAnimSTOverride)animation).isSpecial()) {
            t1.Clear();
            t2.Clear();
            Trail tt1 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString());
            Trail tt2 = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString());
            Trail tm = ((IAnimSTOverride) animation).getTrail();
            if (tt1 != null) t1.Mix(tt1, tm);
            if (tt2 != null) t2.Mix(tt2, tm);
            tr.draw(poseStack, buffer, entitypatch, animation, 0.0f, playTime, partialTicks, animation.getPlaySpeed(entitypatch),
                    t1, t2);
        }
        else{
            tr.draw(poseStack, buffer, entitypatch, animation, 0.0f, playTime, partialTicks, animation.getPlaySpeed(entitypatch),
                    RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString()),
                    RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(InteractionHand.MAIN_HAND).getItem().getRegistryName().toString()));
        }

    }

     */

}

