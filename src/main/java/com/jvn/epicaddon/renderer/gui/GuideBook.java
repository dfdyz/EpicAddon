package com.jvn.epicaddon.renderer.gui;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.utils.TranslateUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class GuideBook extends Screen {

    //Now it is W.I.P.

    public static final ResourceLocation texture = new ResourceLocation(EpicAddon.MODID, "textures/gui/guidebook.png");

    public GuideBook() {
        super(TextComponent.EMPTY);
    }

    @Override
    protected void init() {
        super.init();

        Button pageDown = new Button(this.width - 15, this.height -15, 10, 10, TranslateUtils.GUI("button.pagedown"),(button)->{

        });
    }
}
