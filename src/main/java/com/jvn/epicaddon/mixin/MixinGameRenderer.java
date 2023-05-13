package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.api.PostRenderer.PostEffectBase;
import com.jvn.epicaddon.api.PostRenderer.WhiteFlush;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(value = GameRenderer.class)
public class MixinGameRenderer {
    private float ticker = 0f;
    @Inject(method = "render", at = @At("TAIL"))
    private void PostRender(float pt, long startTime, boolean tick, CallbackInfo cbi){
        PostEffectEvent.effects_highest.removeIf((pair) -> { if(pair.timer <= 0) return true; pair.obj.Process(pair.timer); return false; });
        PostEffectEvent.effects_mid.removeIf((pair) -> { if(pair.timer <= 0) return true; pair.obj.Process(pair.timer); return false; });
        PostEffectEvent.effects_lowest.removeIf((pair) -> { if(pair.timer <= 0) return true; pair.obj.Process(pair.timer); return false; });
    }
}
