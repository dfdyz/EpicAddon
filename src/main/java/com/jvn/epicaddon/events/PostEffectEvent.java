package com.jvn.epicaddon.events;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.register.RegPostEffect;
import com.jvn.epicaddon.renderer.PostRenderer.PostEffect;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT)
public class PostEffectEvent {
    private static float ticker = 0f;
    public static ConcurrentLinkedQueue<PostEffectTimePair> effects_highest = new ConcurrentLinkedQueue<>();
    public static ConcurrentLinkedQueue<PostEffectTimePair> effects_mid = new ConcurrentLinkedQueue<>();
    public static ConcurrentLinkedQueue<PostEffectTimePair> effects_lowest = new ConcurrentLinkedQueue<>();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void PostRenderer(RenderLevelStageEvent event){
        RenderLevelStageEvent.Stage stage = event.getStage();
        if(stage == RenderLevelStageEvent.Stage.AFTER_WEATHER){
            float realTick = event.getPartialTick() + event.getRenderTick();
            float deltaTime = (realTick-ticker)/20f;
            PostEffectEvent.effects_highest.forEach((pair) -> { pair.timer -= deltaTime;});
            PostEffectEvent.effects_mid.forEach((pair) -> { pair.timer -= deltaTime;});
            PostEffectEvent.effects_lowest.forEach((pair) -> { pair.timer -= deltaTime;});
            ticker = realTick;
        }
    }

    public static void PushPostEffectHighest(AbstractPostEffectObj effect, float time, float x, float y, float z){
        if(!effect.IsMultiAble()){
            effects_highest.forEach((p) -> { if(p.obj == effect) p.timer = 0; });
        }
        effects_highest.add(new PostEffectTimePair(effect, time, new Vec3(x,y,z)));
    }

    public static void PushPostEffectHighest(AbstractPostEffectObj effect, float time, Vec3 pos){
        if(!effect.IsMultiAble()){
            effects_highest.forEach((p) -> { if(p.obj == effect) p.timer = 0; });
        }
        effects_highest.add(new PostEffectTimePair(effect, time, pos));
    }

    public static void PushPostEffectHighest(PostEffectTimePair effect){
        if(!effect.obj.IsMultiAble()){
            effects_highest.forEach((p) -> { if(p.obj == effect.obj) p.timer = 0; });
        }
        effects_highest.add(effect);
    }

    public static abstract class AbstractPostEffectObj{
        private static int counter = 0;
        private int id;
        public boolean IsMultiAble(){
            return false;
        }
        public abstract void Process(float deltaTime);
        public void _Process(float dt){
            Init();
            Process(dt);
        }
        public abstract void Init();
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
        public Vec3 position;
        public float distance = 32f;
        public float timer;
        public PostEffectTimePair(AbstractPostEffectObj obj, float time, Vec3 pos){
            this.obj = obj;
            timer = time;
            position = pos;
        }
        public boolean isVisible(Vec3 pos){
            return pos.distanceTo(position) <= distance;
        }
    }
}
