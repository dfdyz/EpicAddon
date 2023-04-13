package com.jvn.epicaddon.api.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;

public class TextureRT extends SimpleTexture {
    public TextureRT(ResourceLocation resourceLocation, NativeImage imagert) {
        super(resourceLocation);
        this.imagert = imagert;
    }
    static final Logger LOGGER = LogUtils.getLogger();
    public void setImagert(NativeImage imagert){
        this.imagert = imagert;
    }

    public ResourceLocation getRL(){
        return this.location;
    }
    protected NativeImage imagert;

    @Override
    public void load(ResourceManager p_118135_) throws IOException {

        SimpleTexture.TextureImage simpletexture$textureimage = this.getTextureImage(p_118135_);
        simpletexture$textureimage.throwIfError();
        TextureMetadataSection texturemetadatasection = simpletexture$textureimage.getTextureMetadata();
        boolean flag;
        boolean flag1;
        if (texturemetadatasection != null) {
            flag = texturemetadatasection.isBlur();
            flag1 = texturemetadatasection.isClamp();
        } else {
            flag = false;
            flag1 = false;
        }

        NativeImage nativeimage = simpletexture$textureimage.getImage();
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> {
                this.doLoad(nativeimage, flag, flag1);
            });
        } else {
            this.doLoad(nativeimage, flag, flag1);
        }
    }

    protected void doLoad(NativeImage img, boolean p_118138_, boolean p_118139_) {
        TextureUtil.prepareImage(this.getId(), 0, img.getWidth(), img.getHeight());
        img.upload(0, 0, 0, 0, 0, img.getWidth(), img.getHeight(), p_118138_, p_118139_, false, true);
    }

    @Override
    protected SimpleTexture.TextureImage getTextureImage(ResourceManager resourceManager) {
        return new TextureImage(null, this.imagert);
    }

    protected static class Img extends TextureImage{
        public Img(IOException ioException) {
            super(ioException);
        }

        public Img(@Nullable TextureMetadataSection textureMetadataSection, NativeImage image) {
            super(textureMetadataSection, image);
        }
    }

}
