package com.jvn.epicaddon.api.renderer;

import com.jvn.epicaddon.EpicAddon;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.InputStreamReader;
import org.slf4j.Logger;

import java.io.*;

public class LocalTexture extends SimpleTexture {
    private final String name;
    public LocalTexture(String name) {
        super(new ResourceLocation(EpicAddon.MODID, "local_"+name));
        this.name = name;
    }
    static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void load(ResourceManager resourceManager) throws IOException {
        SimpleTexture.TextureImage img = this.getTextureImage(resourceManager);
        img.throwIfError();
        TextureMetadataSection texturemetadatasection = img.getTextureMetadata();
        boolean flag;
        boolean flag1;
        if (texturemetadatasection != null) {
            flag = texturemetadatasection.isBlur();
            flag1 = texturemetadatasection.isClamp();
        } else {
            flag = false;
            flag1 = false;
        }

        NativeImage nativeimage = img.getImage();
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
        return load(FMLPaths.CONFIGDIR.get().resolve("EpicAddonTrailTexture/"+name).toString());
    }

    public static SimpleTexture.TextureImage load(String path) {
        try {
            InputStream inputStream = new FileInputStream(path);

            SimpleTexture.TextureImage img;
            try {
                NativeImage nativeimage = NativeImage.read(inputStream);
                TextureMetadataSection texturemetadatasection = null;

                img = new SimpleTexture.TextureImage(texturemetadatasection, nativeimage);
            } catch (Throwable throwable1) {
                throw throwable1;
            }

            inputStream.close();

            return img;
        } catch (IOException ioexception) {
            return new SimpleTexture.TextureImage(ioexception);
        }
    }

}
