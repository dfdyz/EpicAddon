package com.jvn.epicaddon.utils;

import com.jvn.epicaddon.EpicAddon;
import net.minecraft.network.chat.TranslatableComponent;

public class TranslateUtils {
    public static TranslatableComponent GUI(String name){
        return new TranslatableComponent(EpicAddon.MODID+".gui."+name);
    }



}
