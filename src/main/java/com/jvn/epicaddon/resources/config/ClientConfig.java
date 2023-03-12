package com.jvn.epicaddon.resources.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jvn.epicaddon.utils.GlobalVal;
import com.jvn.epicaddon.utils.HealthBarStyle;
import com.jvn.epicaddon.utils.Trail;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jline.utils.InputStreamReader;
import org.slf4j.Logger;

import java.io.*;
import java.util.Map;

public class ClientConfig {
    public static final Gson GSON = (new GsonBuilder()).create();
    public static ConfigVal cfg = new ConfigVal();

    private static Logger LOGGER = LogUtils.getLogger();

    static {

    }

    public static String ReadString(String FileName) {
        String str = "";

        File file = new File(FileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return "";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                str = readFromInputStream(FileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return str;
    }

    public static void Load(){
        LOGGER.info("EpicAddon:Loading Sword Trail Item");
        String cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonTrailItem.json").toString();
        String json = ReadString(cfgpath);

        //LOGGER.info(json);
        if(json != ""){
            RenderConfig.TrailItem.clear();
            RenderConfig.TrailItem = GSON.fromJson(json, new TypeToken<Map<String,Trail>>(){}.getType());
        }
        else{
            WriteString(cfgpath,GSON.toJson(RenderConfig.TrailItem));
        }

        LOGGER.info("EpicAddon:Loading Health Bar Modifier");
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonHealthBar.json").toString();
        json = ReadString(cfgpath);
        //LOGGER.info(json);
        if(json != ""){
            RenderConfig.HealthBarEntity.clear();
            RenderConfig.HealthBarEntity = GSON.fromJson(json, new TypeToken<Map<String, HealthBarStyle>>(){}.getType());
        }
        else{
            WriteString(cfgpath,GSON.toJson(RenderConfig.HealthBarEntity));
        }

        LOGGER.info("EpicAddon:Loading Common Config");
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonCommon.json").toString();
        json = ReadString(cfgpath);
        //LOGGER.info(json);
        if(json != ""){
            try {
                cfg = GSON.fromJson(json, new TypeToken<ConfigVal>(){}.getType());
            } catch (JsonSyntaxException e) {
                WriteString(cfgpath,GSON.toJson(cfg));
                throw new RuntimeException(e);
            }
        }
        else{
            WriteString(cfgpath,GSON.toJson(cfg));
        }

        GlobalVal.ANG = cfg.InitialAngle;

        //RenderConfig.TrailItem = GSON.fromJson(json, new TypeToken<Map<String,Trail>>(){}.getType());
        //LOGGER.info("JSON JSON\n"+GSON.toJson(RenderConfig.TrailItem)+"\nJSON JSON");
    }

    public static void SaveCommon(){
        String cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonCommon.json").toString();
        LOGGER.info("EpicAddon:Save Common Config");
        WriteString(cfgpath,GSON.toJson(cfg));
    }


    public static void WriteString(String FileName,String str){
        try(FileOutputStream fos = new FileOutputStream(FileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);){
            bw.write(str);
            bw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFromInputStream(String s) throws IOException {
        InputStream inputStream = new FileInputStream(new File(s));
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
