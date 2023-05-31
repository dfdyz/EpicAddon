package com.jvn.epicaddon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = PlayerPatch.class, remap = false)
public abstract class MixinPlayerPatch{
    @Inject(method = "initAnimator",at = @At("TAIL"))
    public void PatchedAnimations(ClientAnimator clientAnimator, CallbackInfo callbackInfo){

    }
}
