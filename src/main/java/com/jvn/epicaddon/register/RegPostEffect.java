package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.PostRenderer.BrokenMask;
import com.jvn.epicaddon.api.PostRenderer.PostEffectBase;
import com.jvn.epicaddon.api.PostRenderer.SpaceBroken;
import com.jvn.epicaddon.api.PostRenderer.WhiteFlush;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.jvn.epicaddon.utils.PostEffectUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;

public class RegPostEffect {
    public static PostEffectEvent.AbstractPostEffectObj WhiteFlush;
    public static PostEffectEvent.AbstractPostEffectObj SpaceBroken;

    public static void Reg(){
        WhiteFlush = new PostEffectEvent.AbstractPostEffectObj() {
            PostEffectBase blit;
            WhiteFlush whiteFlush;

            @Override
            public void Init(){
                if(blit == null || this.whiteFlush == null){
                    Minecraft mc = Minecraft.getInstance();
                    try{
                        blit = new PostEffectBase(new EffectInstance(mc.getResourceManager(), "minecraft:blit"));
                        whiteFlush = new WhiteFlush(mc);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void Process(float remainTime) {
                Minecraft mc = Minecraft.getInstance();
                RenderTarget main = mc.getMainRenderTarget();
                RenderTarget temp = PostEffectBase.createTempTarget(main, main.width, main.height);
                RenderTarget temp2 = PostEffectBase.createTempTarget(main, 1, 1);

                blit.process(main, temp2, 0);
                whiteFlush.process(main, temp2, temp,remainTime);
                blit.process(temp,main, 0);
                temp.destroyBuffers();
                temp2.destroyBuffers();
            }
        };

        SpaceBroken = new PostEffectEvent.AbstractPostEffectObj() {
            PostEffectBase blit;
            BrokenMask brokenMask;
            com.jvn.epicaddon.api.PostRenderer.SpaceBroken spaceBroken;

            PostEffectUtils.OBJ_JSON obj;

            @Override
            public void Init(){
                if(blit == null || this.brokenMask == null){
                    Minecraft mc = Minecraft.getInstance();
                    try{
                        this.blit = new PostEffectBase(new EffectInstance(mc.getResourceManager(), "epicaddon:blit"));
                        this.brokenMask = new BrokenMask(mc.getResourceManager());
                        this.spaceBroken = new SpaceBroken(mc.getResourceManager());
                        obj = PostEffectUtils.LoadOBJ_JSON(new ResourceLocation(EpicAddon.MODID, "models/effect/spacebroken.json"));
                        System.out.println("OBJ Inited");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void Process(float remainTime) {
                Minecraft mc = Minecraft.getInstance();
                RenderTarget org = mc.getMainRenderTarget();
                RenderTarget temp = PostEffectBase.createTempTarget(org, org.width, org.height);
                RenderTarget mask = PostEffectBase.createTempTarget(org, org.width, org.height);

                this.blit.process(org,temp,0);
                this.brokenMask.process(temp,mask,0,obj);
                this.spaceBroken.process(temp,mask,org,0.05f);
                temp.destroyBuffers();
                mask.destroyBuffers();
            }
        };
    }

}
