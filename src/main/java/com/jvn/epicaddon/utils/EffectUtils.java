package com.jvn.epicaddon.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EffectUtils {
    public static class Triangle {
        public int x,y,z;
    }

    public static class vec3f {
        public float x,y,z;
        public Vector3f toBugJumpFormat(){
            return new Vector3f(x,y,z);
        }
    }

    public static class OBJ_JSON{
        public List<vec3f> Positions = new ArrayList<>();
        public List<Triangle> Face = new ArrayList<>();
    }

    public static OBJ_JSON LoadOBJ_JSON(ResourceLocation location){
        OBJ_JSON obj = null;
        try {
            String str = "";
            Resource resource = Minecraft.getInstance().getResourceManager().getResource(location);

            InputStreamReader isr = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);

            int c = 0;
            while((c = isr.read()) != -1){
                str += (char)c;
            }

            Gson gson = new Gson();
            obj = gson.fromJson(str,new TypeToken<OBJ_JSON>(){}.getType());

            EpicAddon.LOGGER.info(gson.toJson(obj));

        }catch(IOException e) {
            throw new RuntimeException(e);
        }

        return obj;
    }
}
