package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.item.Destiny.DestinyWeaponItem;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;
import java.util.Random;

public class RegModels {

    public static void RegItemModelOverride(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            ItemProperties.register(RegItems.Destiny.get(), new ResourceLocation(EpicAddon.MODID, "style"), (itemStack, clientWorld, livingEntity,i) -> {
                CompoundTag tags = itemStack.getOrCreateTag();
                String type = tags.getString("epicaddon_type");
                int t = tags.getShort("epicaddon_typeidx");
                if(t == 0){
                    if(type == DestinyWeaponItem.types[0]) return 0;
                    else return 3;
                }
                else{
                    if(type == DestinyWeaponItem.types[0]) return 2;
                    else return 1;
                }
            });
        });

    }



}
