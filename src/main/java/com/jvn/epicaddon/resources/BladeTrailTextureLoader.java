package com.jvn.epicaddon.resources;

import ca.weblite.objc.Client;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.ConfigVal;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BladeTrailTextureLoader {
    public static final Map<String, EpicAddonRenderType.BladeTrailRenderType> RenderType = Maps.newHashMap();
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void Load(){
        try {
            RenderType.clear();
            String str = "";
            Resource resource = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(EpicAddon.MODID, "bladetrail/registry.json"));

            InputStreamReader isr = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

            int c = 0;
            while((c = isr.read()) != -1){
                str += (char)c;
            }

            Map<String, String> info = null;
            if(!str.equals("")){
                info = ClientConfig.GSON.fromJson(str, new TypeToken<Map<String, String>>(){}.getType());
            }

            if(info == null){
                LOGGER.warn("Could not read epicaddon:bladetrail/registry.json");
            }

            info.forEach((name,file) -> {
                LOGGER.info("Load blade trail: [" + name + "] texture: "+file);
                RenderType.put(name, new EpicAddonRenderType.BladeTrailRenderType(file));
            });
        }
        catch (IOException e) {
            LOGGER.error("Could not read epicaddon:bladetrail/registry.json");
            throw new RuntimeException(e);
        }
    }
}
