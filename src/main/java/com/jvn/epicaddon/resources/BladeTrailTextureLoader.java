package com.jvn.epicaddon.resources;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.CommonConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
                info = CommonConfig.GSON.fromJson(str, new TypeToken<Map<String, String>>(){}.getType());
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
