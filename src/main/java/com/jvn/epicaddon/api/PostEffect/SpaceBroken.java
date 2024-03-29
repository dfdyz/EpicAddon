package com.jvn.epicaddon.api.PostEffect;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public class SpaceBroken extends PostEffectBase{
    public SpaceBroken(ResourceManager resmgr) throws IOException {
        super(new EffectInstance(resmgr, "epicaddon:spacebroken"));
    }

    public void process(RenderTarget inTarget, RenderTarget depth, RenderTarget outTarget, float time, float rot){
        inTarget.unbindWrite();
        depth.unbindWrite();
        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);
        this.effect.setSampler("DiffuseSampler2", depth::getColorTextureId);
        Matrix4f shaderOrthoMatrix = Matrix4f.orthographic(0.0F, outTarget.width, outTarget.height, 0.0F, 0.1F, 1000.0F);

        this.effect.safeGetUniform("ProjMat").set(shaderOrthoMatrix);
        this.effect.safeGetUniform("InSize").set((float) inTarget.width, (float) inTarget.height);
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        float cx = ((float) Math.cos((camera.getXRot()+rot)/180*Math.PI)+1) * 0.5f;
        float cy = ((float) Math.sin((camera.getYRot()+rot)/180*Math.PI)+1) * 0.5f;
        this.effect.safeGetUniform("Crot").set(cx,cy);
        this.effect.safeGetUniform("cweight").set(0.02f);
        this.effect.safeGetUniform("Time").set(time);

        Minecraft minecraft = Minecraft.getInstance();
        this.effect.safeGetUniform("ScreenSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
        SetValue();
        this.effect.apply();

        outTarget.clear(Minecraft.ON_OSX);
        outTarget.bindWrite(false);
        RenderSystem.depthFunc(519);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        bufferbuilder.vertex(0.0D, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(inTarget.width, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(inTarget.width, inTarget.height, 500.0D).endVertex();
        bufferbuilder.vertex(0.0D, inTarget.height, 500.0D).endVertex();

        bufferbuilder.end();
        BufferUploader._endInternal(bufferbuilder);
        RenderSystem.depthFunc(515);
        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
        depth.unbindRead();

        //PassUtils.Blit(inTarget, outTarget);

    }
}
