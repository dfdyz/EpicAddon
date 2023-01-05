package com.jvn.epicaddon.mixin;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapabilityPresets;

@Mixin(value = EpicFightCapabilities.class,remap = false)
public class MixinNBTCapabilities {

    @Inject(at = @At(value = "RETURN"), method = "getItemStackCapability", cancellable = true)
    private static void onGetItemStackCapability(ItemStack stack, CallbackInfoReturnable<CapabilityItem> cbi) {
        if(stack.hasTag()){
            String typeName = stack.getTag().getString("epicaddon_type");
            if(typeName != ""){
                cbi.setReturnValue(WeaponCapabilityPresets.get(typeName).apply(stack.getItem()).build());
                cbi.cancel();
            }
        }
    }

}
