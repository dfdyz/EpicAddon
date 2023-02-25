package com.jvn.epicaddon.api.cap;

import net.minecraft.world.item.UseAnim;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public class GenShinBowCap extends WeaponCapability {
    public GenShinBowCap(CapabilityItem.Builder builder) {
        super(builder);
    }


    @Override
    public UseAnim getUseAnimation(LivingEntityPatch<?> playerpatch) {
        return UseAnim.NONE;
    }


}
