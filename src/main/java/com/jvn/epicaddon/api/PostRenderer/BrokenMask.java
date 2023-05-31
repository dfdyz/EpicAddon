package com.jvn.epicaddon.api.PostRenderer;

import com.jvn.epicaddon.utils.EffectUtils;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;

public class BrokenMask extends PostEffectBase{
    public BrokenMask(EffectInstance effect) {
        super(effect);
    }

    public void process(RenderTarget inTarget, RenderTarget outTarget, float time, EffectUtils.OBJ_JSON obj){
        inTarget.unbindWrite();
        RenderSystem.viewport(0, 0, outTarget.width, outTarget.height);
        //this.effect.setSampler("DiffuseSampler", inTarget::getColorTextureId);
        Matrix4f shaderOrthoMatrix = Matrix4f.orthographic(0.0F, outTarget.width, outTarget.height, 0.0F, 0.1F, 1000.0F);

        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();

        this.effect.safeGetUniform("ProjMat").set(shaderOrthoMatrix);
        this.effect.safeGetUniform("InSize").set((float) inTarget.width, (float) inTarget.height);
        this.effect.safeGetUniform("OutSize").set((float) outTarget.width, (float) outTarget.height);

        float cx = ((float) Math.cos(camera.getXRot()/180*Math.PI)+1) * 0.5f;
        float cy = ((float) Math.sin(camera.getYRot()/180*Math.PI)+1) * 0.5f;

        this.effect.safeGetUniform("Crot").set(cx,cy);
        this.effect.safeGetUniform("Time").set(time);

        Minecraft minecraft = Minecraft.getInstance();
        this.effect.safeGetUniform("ScreenSize").set((float)minecraft.getWindow().getWidth(), (float)minecraft.getWindow().getHeight());
        this.effect.apply();

        outTarget.clear(Minecraft.ON_OSX);
        outTarget.bindWrite(false);
        RenderSystem.depthFunc(519);
        RenderSystem.disableCull();
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION);

        Quaternion quaternion = camera.rotation();

        float sss = inTarget.height/2.8f;

        for(int i = 0; i < obj.Face.size(); ++i) {
            EffectUtils.Triangle triangle = obj.Face.get(i);
            Vector3f v1 = obj.Positions.get(triangle.x-1).copy();
            Vector3f v2 = obj.Positions.get(triangle.y-1).copy();
            Vector3f v3 = obj.Positions.get(triangle.z-1).copy();

            v1.mul(sss,sss,sss);
            v2.mul(sss,sss,sss);
            v3.mul(sss,sss,sss);
            v1.transform(quaternion);
            v2.transform(quaternion);
            v3.transform(quaternion);
            v1.add(inTarget.width/2.0f, inTarget.height/2.0f,600);
            v2.add(inTarget.width/2.0f, inTarget.height/2.0f,600);
            v3.add(inTarget.width/2.0f, inTarget.height/2.0f,600);

            bufferbuilder.vertex(v1.x(), v1.y(), v1.z()).endVertex();
            bufferbuilder.vertex(v2.x(), v2.y(), v2.z()).endVertex();
            bufferbuilder.vertex(v3.x(), v3.y(), v3.z()).endVertex();
        }

        bufferbuilder.end();
        BufferUploader._endInternal(bufferbuilder);
        RenderSystem.depthFunc(515);
        this.effect.clear();
        outTarget.unbindWrite();
        inTarget.unbindRead();
        RenderSystem.enableCull();
    }
}
