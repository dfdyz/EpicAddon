package com.jvn.epicaddon.mixin;


import com.jvn.epicaddon.register.RegMobEffect;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mixin(value = Mob.class)
public abstract class MixinMob {
    @Inject(at = @At(value = "HEAD"), method = "serverAiStep()V", cancellable = true)
    private void epicfight_serverAiStep(CallbackInfo info) {
        Mob self = (Mob)((Object)this);
        MobEffectInstance e = self.getEffect(RegMobEffect.STOP.get());
        if(e != null){
            info.cancel();
        }
    }
}
