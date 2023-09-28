package com.jvn.epicaddon.events;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import com.jvn.epicaddon.capability.JointLinker.AnimatedItemMgr;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = EpicAddon.MODID)
public class CapabilityEvent {
    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof LivingEntity le){
            AnimatedItemMgr aimgr = EpicAddonCapabilities.getAnimatedItem(le);

            if (aimgr == null) {
                AnimatedItemMgr.Provider prov = new AnimatedItemMgr.Provider(le);
                AnimatedItemMgr cap = prov.getCapability(EpicAddonCapabilities.ITEM_ANIMATOR).orElse(null);
                cap.onConstruct(le);
                event.addCapability(new ResourceLocation(EpicAddon.MODID, "item_anim_mgr"), prov);
            }
        }
    }
}
