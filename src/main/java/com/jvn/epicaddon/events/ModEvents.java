package com.jvn.epicaddon.events;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.camera.CamAnimLoader;
import com.jvn.epicaddon.command.CmdMgr;
import com.jvn.epicaddon.renderer.HealthBarRenderer;
import com.jvn.epicaddon.resources.BladeTrailTextureLoader;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.RenderConfig;
import com.jvn.epicaddon.utils.GlobalVal;
import com.jvn.epicaddon.utils.HealthBarStyle;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import yesman.epicfight.api.client.forgeevent.RenderEnderDragonEvent;
import yesman.epicfight.world.gamerule.EpicFightGamerules;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class ModEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    /*
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void playerChat(ClientChatEvent event) {

    }
     */

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CmdMgr.registerClientCommands(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void HealthBarRender(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
        LivingEntity livingentity = event.getEntity();

        if(!ClientConfig.cfg.EnableHealthBar
                || livingentity.distanceToSqr(Minecraft.getInstance().getCameraEntity()) > ClientConfig.cfg.HealthBarRenderDistance*8
                || livingentity.isInvisible()
                || !livingentity.isAlive()){
            return;
        }

        HealthBarStyle healthBarStyle = RenderConfig.HealthBarEntity.get(livingentity.getType().getRegistryName().toString());

        if (healthBarStyle == null) {
            healthBarStyle = new HealthBarStyle();
        }
        if (livingentity instanceof LocalPlayer && !ClientConfig.cfg.RenderHealthBarSelf) {
            return;
        }

        if (!Minecraft.getInstance().options.hideGui) {
            HealthBarRenderer.draw(livingentity, event.getPoseStack(), event.getMultiBufferSource(), event.getPartialTick(),healthBarStyle);
        }
    }

    /*
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void IndicatorRender(RenderLivingEvent.Pre<? extends LivingEntity, ? extends EntityModel<? extends LivingEntity>> event) {
    }
     */

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void RenderEnderDragonEvent(RenderEnderDragonEvent event){
        LivingEntity entityIn = event.getEntity();
        if(!ClientConfig.cfg.EnableHealthBar
                || entityIn.isInvisible()
                || !entityIn.isAlive()
                || entityIn.distanceToSqr(Minecraft.getInstance().getCameraEntity()) > 400 * 8
        ) return;
        HealthBarStyle healthBarStyle = RenderConfig.HealthBarEntity.get("minecraft:ender_dragon");
        if(healthBarStyle == null){
            healthBarStyle = new HealthBarStyle(6.0f,1,0.0f,60f);
        }
        if (!Minecraft.getInstance().options.hideGui) {
            HealthBarRenderer.draw(entityIn,event.getPoseStack(),event.getBuffers(),event.getPartialRenderTick(),healthBarStyle);
        }
    }


    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderWorldLast(RenderLevelStageEvent event) {
        GlobalVal.ANG +=event.getPartialTick()*ClientConfig.cfg.RotSpeed;
        while (GlobalVal.ANG >= 360.0f){
            GlobalVal.ANG-=360.0f;
        }
    }

}
