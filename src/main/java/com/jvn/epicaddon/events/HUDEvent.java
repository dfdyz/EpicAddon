package com.jvn.epicaddon.events;

import com.jvn.epicaddon.EpicAddon;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class HUDEvent {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void renderBars(RenderGameOverlayEvent.Pre event) {
        switch (event.getType()){
            case ALL:
                event.setCanceled(true);
            default:
                return;
        }
    }





}
