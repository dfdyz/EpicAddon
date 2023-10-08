package com.jvn.epicaddon.events.reloader;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.RenderConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.api.client.model.ItemSkins;

import java.lang.reflect.Field;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class Config2SkinReloader extends SimpleJsonResourceReloadListener {
    private static Map<Item, ItemSkin> itemMap;
    public Config2SkinReloader(){
        super((new GsonBuilder()).create(), "item_skins");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> r_j_map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        if(ClientConfig.cfg.EnableAutoMerge) Merge();
    }

    public static void Merge(){
        GetItemMap();
        if (itemMap != null){
            EpicAddon.LOGGER.info("Merge TrailItem into ItemSkins");
            RenderConfig.TrailItem.forEach((id, skin) -> {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
                itemMap.put(item, skin.toItemSkin());
            });
        }
        else {
            EpicAddon.LOGGER.info("Merge TrailItem failed");
        }
    }

    public static Map<Item, ItemSkin> GetItemMap(){
        if(itemMap == null) itemMap = ReflectItemMap();
        return itemMap;
    }

    private static Map<Item, ItemSkin> ReflectItemMap() {

        try{
            Field field = ItemSkins.class.getDeclaredField("ITEM_SKIN_MAP");
            field.setAccessible(true);
            Map<Item, ItemSkin> map = (Map<Item, ItemSkin>)field.get(null);
            return map;
        }
        catch (Exception e){
            EpicAddon.LOGGER.info(e.toString());
        }
        return null;
    }

}
