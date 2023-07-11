package com.jvn.epicaddon.utils;

import com.jvn.epicaddon.EpicAddon;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class LocalTextureUtils {

    public static ResourceLocation LoadBladeTrailTexture(String path, String registryName) throws IOException {
        InputStream org = new FileInputStream(path);
        // Convert to png
        BufferedImage bufferedImage = ImageIO.read(org);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        NativeImage nativeImage = NativeImage.read(inputStream);
        DynamicTexture dynamicTexture = new DynamicTexture(nativeImage);
        ResourceLocation rl = new ResourceLocation(EpicAddon.MODID, registryName);
        Minecraft.getInstance().getTextureManager().register(rl, dynamicTexture);
        return rl;
    }


}
