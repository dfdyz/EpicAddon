package com.jvn.epicaddon.renderer.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;

public class EntityDeath extends ColorTriangleParticle{

    public EntityDeath(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return new ParticleRenderType() {
            public void begin(BufferBuilder bufferBuilder, TextureManager p_107470_) {
                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
                RenderSystem.setShader(GameRenderer::getParticleShader);
                bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
            }

            public void end(Tesselator tesselator) {
                tesselator.end();
            }

            public String toString() {
                return "TRIANGLE";
            }
        };
    }
}
