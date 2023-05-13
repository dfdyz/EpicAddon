package com.jvn.epicaddon.register;

import com.jvn.epicaddon.api.PostRenderer.PostEffectBase;
import com.jvn.epicaddon.api.PostRenderer.WhiteFlush;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;

import java.io.IOException;

public class RegPostEffect {
    public static PostEffectEvent.AbstractPostEffectObj WhiteFlush;

    public static void Reg(){
        WhiteFlush = new PostEffectEvent.AbstractPostEffectObj() {
            PostEffectBase postEffect1;
            PostEffectBase postEffect2;
            WhiteFlush whiteFlush;
            @Override
            public void Process(float remainTime) {
                Minecraft mc = Minecraft.getInstance();
                RenderTarget main = mc.getMainRenderTarget();
                RenderTarget temp = PostEffectBase.createTempTarget(main, main.width, main.height);
                RenderTarget temp2 = PostEffectBase.createTempTarget(main, 1, 1);

                if(postEffect1 == null || postEffect2 == null || whiteFlush == null){
                    try{
                        postEffect1 = new PostEffectBase(new EffectInstance(mc.getResourceManager(), "minecraft:blit"));
                        postEffect2 = new PostEffectBase(new EffectInstance(mc.getResourceManager(), "minecraft:blit"));
                        whiteFlush = new WhiteFlush(mc);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                postEffect1.process(main, temp2, 0);
                whiteFlush.process(main, temp2, temp,remainTime);
                postEffect2.process(temp,main, 0);
                temp.destroyBuffers();
                temp2.destroyBuffers();
            }
        };
    }

}
