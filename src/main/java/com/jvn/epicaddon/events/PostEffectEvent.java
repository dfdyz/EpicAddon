package com.jvn.epicaddon.events;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.renderer.PostRenderer.PostEffect;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class PostEffectEvent {
    private static float ticker = 0f;

    public static LinkedList<PostEffectTimePair> effects_highest = Lists.newLinkedList();
    public static LinkedList<PostEffectTimePair> effects_mid = Lists.newLinkedList();
    public static LinkedList<PostEffectTimePair> effects_lowest = Lists.newLinkedList();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void PostRenderer(RenderLevelStageEvent event){
        RenderLevelStageEvent.Stage stage = event.getStage();
        if(stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS){
            float realTick = event.getPartialTick() + event.getRenderTick();
            float deltaTime = (realTick-ticker)/20f;
            PostEffectEvent.effects_highest.forEach((pair) -> { pair.timer -= deltaTime;});
            PostEffectEvent.effects_mid.forEach((pair) -> { pair.timer -= deltaTime;});
            PostEffectEvent.effects_lowest.forEach((pair) -> { pair.timer -= deltaTime;});
            ticker = realTick;
        }
    }

    public static void PushPostEffectHighest(AbstractPostEffectObj effect, float time){
        if(!effect.IsMultiAble()){
            effects_highest.forEach((p) -> { if(p.obj == effect) p.timer = 0; });
        }
        effects_highest.add(new PostEffectTimePair(effect, time));
    }

    public static abstract class AbstractPostEffectObj{
        private static int counter = 0;
        private int id;
        public boolean IsMultiAble(){
            return false;
        }
        public abstract void Process(float deltaTime);
        public AbstractPostEffectObj(){
            id = counter++;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    public static class PostEffectTimePair{
        public AbstractPostEffectObj obj;
        public float timer;
        public PostEffectTimePair(AbstractPostEffectObj obj, float time){
            this.obj = obj;
            timer = time;
        }
    }
}
