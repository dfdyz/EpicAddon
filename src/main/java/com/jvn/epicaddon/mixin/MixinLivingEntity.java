package com.jvn.epicaddon.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;

@Mixin(value = LivingEntity.class)
public abstract class MixinLivingEntity {

    @Inject(at = @At("HEAD"),method = "makePoofParticles", cancellable = true)
    private void MixinPoof(CallbackInfo callbackInfo){
        callbackInfo.cancel();
    }
}
