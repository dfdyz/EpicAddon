package com.jvn.epicaddon.api.PostEffect;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;

public class PassUtils {

    public static void Blit(RenderTarget in, RenderTarget out){

        ShaderProgram.Blit.Active();
        ShaderProgram.Blit.SetSampler("DiffuseSampler", in.getColorTextureId());
        ShaderProgram.Blit.SetVec4f("ColorModulate", 1,1,1,1);
        ShaderProgram.Blit.SetVec2f("OutSize", (float) out.width, (float) out.height);

        Matrix4f shaderOrthoMatrix = Matrix4f.orthographic(0.0F, out.width, out.height, 0.0F, 0.1F, 1000.0F);
        ShaderProgram.Blit.SetMat4x4f("ProjMat", shaderOrthoMatrix);

        out.clear(Minecraft.ON_OSX);
        out.bindWrite(false);
        RenderSystem.depthFunc(519);

        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        bufferbuilder.vertex(0.0D, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(in.width, 0.0D, 500.0D).endVertex();
        bufferbuilder.vertex(in.width, in.height, 500.0D).endVertex();
        bufferbuilder.vertex(0.0D, in.height, 500.0D).endVertex();

        bufferbuilder.end();
        BufferUploader.end(bufferbuilder);
        RenderSystem.depthFunc(515);
        out.unbindWrite();
        in.unbindRead();
    }

}
