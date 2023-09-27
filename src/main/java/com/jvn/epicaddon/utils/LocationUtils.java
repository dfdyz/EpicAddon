package com.jvn.epicaddon.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jline.utils.InputStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class LocationUtils {


    public static String ReadString(ResourceLocation res) {
        ResourceManager mgr = Minecraft.getInstance().getResourceManager();

        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            Resource r = mgr.getResource(res);
            InputStream is = r.getInputStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultStringBuilder.toString();
    }

}
