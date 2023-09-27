package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.api.PostEffect.PostEffectBase;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.jvn.epicaddon.register.RegPostEffect;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = -1000)
public class MixinGameRenderer {
    private float ticker = 0f;

    @Shadow()
    private Minecraft minecraft;

    @Inject(method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V",
                    ordinal = 0
            ))
    private void PostRender(float pt, long startTime, boolean tick, CallbackInfo cbi){
        RegPostEffect.depthBuffer = PostEffectBase.createTempTarget(minecraft.getMainRenderTarget());
        RegPostEffect.depthBuffer.copyDepthFrom(minecraft.getMainRenderTarget());

        Vec3 pos = Minecraft.getInstance().player.getPosition(pt);
        PostEffectEvent.effects_highest.removeIf((pair) -> {
            if(pair.timer <= 0)
                return true;
            if(pair.isVisible(pos))
                pair.obj.Process(pair.timer, pair.getDatas.apply(pair.timer));
            return false; });
        PostEffectEvent.effects_mid.removeIf((pair) -> {
            if(pair.timer <= 0)
                return true;
            if(pair.isVisible(pos))
                pair.obj.Process(pair.timer, pair.getDatas.apply(pair.timer));
            return false; });
        PostEffectEvent.effects_lowest.removeIf((pair) -> {
            if(pair.timer <= 0)
                return true;
            if(pair.isVisible(pos))
                pair.obj.Process(pair.timer, pair.getDatas.apply(pair.timer));
            return false; });

        RegPostEffect.depthBuffer.destroyBuffers();
    }

}
