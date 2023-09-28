package com.jvn.epicaddon.capability;

import com.jvn.epicaddon.capability.JointLinker.AnimatedItemMgr;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;


public class EpicAddonCapabilities {
    public static final Capability<AnimatedItemMgr> ITEM_ANIMATOR = CapabilityManager.get(new CapabilityToken<>(){});

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(AnimatedItemMgr.class);
    }

    public static AnimatedItemMgr getAnimatedItem(LivingEntityPatch<?> entityPatch){
        return getAnimatedItem(entityPatch.getOriginal());
    }

    public static <T extends LivingEntity> AnimatedItemMgr getAnimatedItem(LivingEntity entity){
         return entity.getCapability(EpicAddonCapabilities.ITEM_ANIMATOR).orElse(null);
    }
}
