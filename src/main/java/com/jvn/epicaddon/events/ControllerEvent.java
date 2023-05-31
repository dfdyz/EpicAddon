package com.jvn.epicaddon.events;


import com.jvn.epicaddon.EpicAddon;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class ControllerEvent {


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void keyboardEvent(InputEvent.MouseScrollEvent event) {

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void keyboardEvent(InputEvent.KeyInputEvent event) {

    }

}
