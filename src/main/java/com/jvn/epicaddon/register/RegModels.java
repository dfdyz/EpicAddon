package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.item.Destiny.DestinyWeaponItem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.item.RenderBow;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.item.SkillBookItem;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegModels {
    private static final Logger LOGGER = LogUtils.getLogger();
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RegItemModelOverride(FMLClientSetupEvent event){

        event.enqueueWork(() -> {
            LOGGER.info("RegItemModelOverride");
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
                    else{
                        if(t>7) return 1;
                        else return 3;
                    }
                }
            });


            ItemProperties.register(RegItems.TrainingBow.get(), new ResourceLocation(EpicAddon.MODID,"pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
                if (p_174637_ == null) {
                    return 0.0F;
                } else {
                    return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            ItemProperties.register(RegItems.TrainingBow.get(), new ResourceLocation(EpicAddon.MODID,"pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
            });

            /*
            ItemProperties.register(RegItems.SKILLBOOK.get(), new ResourceLocation(EpicAddon.MODID,"skill"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                Skill skill = SkillBookItem.getContainSkill(p_174630_);

                if (skill != null) {
                    if (skill.getRegistryName().equals(RegEpicAddonSkills.SAO_DUALSWORD.getRegistryName())) {
                        return 1;
                    } else if (skill.getRegistryName().equals(RegEpicAddonSkills.SAO_RAPIER_A.getRegistryName())) {
                        return 2;
                    }
                }

                return 0;
            });
             */
        });
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RegItemEFMRenderer(PatchedRenderersEvent.Add event){
        LOGGER.info("Reg Item EFM Renderer Override");
        event.addItemRenderer(RegItems.TrainingBow.get(), new RenderBow());





    }
}
