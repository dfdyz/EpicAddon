package com.jvn.epicaddon.renderer.PostRenderer;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class PostEffect extends PostChain {
    public PostEffect(TextureManager textureManager, ResourceManager resourceManager, RenderTarget renderTarget, ResourceLocation resourceLocation) throws IOException, JsonSyntaxException {
        super(textureManager, resourceManager, renderTarget, resourceLocation);
    }





}
