package com.jvn.epicaddon.resources;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.CommonConfig;
import com.jvn.epicaddon.utils.LocalTextureUtils;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BladeTrailTextureLoader {
    /*
    public static final Map<String, EpicAddonRenderType.BladeTrailRenderType> RenderType = Maps.newHashMap();
    private static final Logger LOGGER = LogUtils.getLogger();



    public static void ReleaseAll(){
        RenderType.forEach((key,val) -> {val.Release();});
        RenderType.clear();
    }


    public static void Load(){
        Path rootPath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonTrailTexture");
        File dir = rootPath.toFile();
        if(!dir.exists()){
            dir.mkdir();
        }

        File reg = rootPath.resolve("registry.json").toFile();
        if(!reg.exists()){
            LOGGER.warn("registry.json not exists, skip.");
            return;
        }

        String jsonStr = ClientConfig.ReadString(reg.toString());

        Map<String, String> info = null;
        if(!jsonStr.equals("")){
            info = CommonConfig.GSON.fromJson(jsonStr, new TypeToken<Map<String, String>>(){}.getType());
        }

        if(info == null){
            LOGGER.warn("registry.json is void, skip.");
        }

        info.forEach((name,file) -> {
            ResourceLocation rl = null;
            try {
                rl = LocalTextureUtils.LoadBladeTrailTexture(rootPath.resolve(file).toString(), name);
            } catch (IOException e) {
                LOGGER.info("Failed to load blade trail: [" + name + "] texture: "+file);
                throw new RuntimeException(e);
            }
            if(rl == null) return;
            EpicAddonRenderType.BladeTrailRenderType type = new EpicAddonRenderType.BladeTrailRenderType(rl);
            RenderType.put(name, type);
            LOGGER.info("Loaded blade trail: [" + name + "] texture: "+file);
        });


    }

     */
}
