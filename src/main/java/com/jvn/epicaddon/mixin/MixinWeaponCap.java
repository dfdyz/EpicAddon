package com.jvn.epicaddon.mixin;


import com.jvn.epicaddon.capabilities.LockableWeaponCap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Mixin(value = WeaponCapability.class, remap = false)
public abstract class MixinWeaponCap {
    protected Map<Style, Map<Style,List<StaticAnimation>>> ChildAutoAttackMotions;
    protected Map<Style, Map<Style,StaticAnimation>> ChildSpecialAttack;
    protected Map<Style, Function<LivingEntityPatch<?>, Style>> LockedProvider;

    protected boolean Lockable = false;

    @Shadow
    abstract Style getStyle(LivingEntityPatch<?> entitypatch);

    @Inject(at = @At("RETURN"),method = "<init>")
    protected void WeaponCapability(CapabilityItem.Builder builder,CallbackInfo cb) {
        if (builder instanceof LockableWeaponCap.Builder){
            this.LockedProvider = ((LockableWeaponCap.Builder)builder).LockedProvider;
            this.ChildAutoAttackMotions = ((LockableWeaponCap.Builder)builder).LockedAutoAttackMotions;
            this.ChildSpecialAttack = ((LockableWeaponCap.Builder)builder).ChildSpecialAttack;
            this.Lockable = true;
        }
        //super(builder);
    }

    @Inject(at = @At("RETURN"),method = "getAutoAttckMotion", cancellable = true)
    public void MixinGetAutoAttack(PlayerPatch<?> playerpatch, CallbackInfoReturnable<List<StaticAnimation>> callbackInfo) {
        if(!this.Lockable) return;
        Style style = this.getStyle(playerpatch);
        Function<LivingEntityPatch<?>, Style> check = LockedProvider.get(style);
        if (check != null && this.ChildAutoAttackMotions.get(style) != null){
            Style child = check.apply(playerpatch);
            if(child != null && this.ChildAutoAttackMotions.get(style).get(child) != null){
                callbackInfo.setReturnValue(this.ChildAutoAttackMotions.get(style).get(child));
                callbackInfo.cancel();
            }
        }
    }

    @Inject(at = @At("RETURN"),method = "getSpecialAttack", cancellable = true)
    public void MixinGetSpecialAttack(PlayerPatch<?> playerpatch, CallbackInfoReturnable<StaticAnimation> callbackInfo){
        if(!this.Lockable) return;
        Style style = this.getStyle(playerpatch);
        Function<LivingEntityPatch<?>, Style> check = LockedProvider.get(style);
        if (check != null){
            Style child = check.apply(playerpatch);
            if(child != null && this.ChildSpecialAttack.get(style) != null){
                callbackInfo.setReturnValue(this.ChildSpecialAttack.get(style).get(child));
                callbackInfo.cancel();
            }
        }
    }



}
