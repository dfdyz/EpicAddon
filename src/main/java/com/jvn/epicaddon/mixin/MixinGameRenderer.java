package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.PostRenderer.PostEffectBase;
import com.jvn.epicaddon.api.PostRenderer.WhiteFlush;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.jvn.epicaddon.register.RegPostEffect;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(value = GameRenderer.class, priority = -1000)
public class MixinGameRenderer {
    private float ticker = 0f;
    @Inject(method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V",
                    ordinal = 0
            ))
    private void PostRender(float pt, long startTime, boolean tick, CallbackInfo cbi){
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
    }

}
