package com.jvn.epicaddon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;

//@Mixin(value = StaticAnimation.class, remap = false, priority = 0)
public abstract class MixinAtkAnimCommon {
    /*
    @Inject(
            method = {"getState"},
            at = @At("HEAD"),
            cancellable = true
    )
    public void patchState(float time, CallbackInfoReturnable<EntityState> cir) {
        if(this instanceof IPatchedState){
            cir.setReturnValue(((IPatchedState)this).getPatchedState(time));
            cir.cancel();
        }
    }*/
}
