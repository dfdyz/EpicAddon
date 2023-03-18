package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.api.anim.FallAtkStartAnim;
import com.jvn.epicaddon.api.anim.GravityRestter;
import com.jvn.epicaddon.api.anim.MixedActionAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;

@Mixin(value = StaticAnimation.class, remap = false, priority = 1001)
public abstract class MixinAtkAnimCommon implements GravityRestter {
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

    /*
    @Shadow
    private int namespaceId;
    @Shadow
    private int animationId;
    @Inject(at = @At("RETURN"), method = "getState", cancellable = true)
    public void MixinState(float t, CallbackInfoReturnable<EntityState> cbi){
        StaticAnimation animation = EpicFightMod.getInstance().animationManager.findAnimationById(namespaceId,animationId);
        if (animation instanceof MixedActionAnimation){
            cbi.setReturnValue(((MixedActionAnimation)animation).getStateEx(t));
            cbi.cancel();
        }
    }

     */
}
