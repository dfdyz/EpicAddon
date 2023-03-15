package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.api.anim.GravityRestter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = StaticAnimation.class, remap = false, priority = 1001)
public class MixinAtkAnimGravityReseter implements GravityRestter {
    private boolean mode = true;
    @Override
    public boolean ShouldResetGravity() {
        return mode;
    }
    @Override
    public void setMode(boolean should) {
        mode = should;
    }

    @Inject(at = @At("HEAD"),method = "begin")
    private void MixinBegin(LivingEntityPatch<?> entitypatch, CallbackInfo cbi){
        if(entitypatch instanceof PlayerPatch && this.mode && entitypatch.getOriginal().isNoGravity()){
            entitypatch.getOriginal().setNoGravity(false);
        }
    }
}
