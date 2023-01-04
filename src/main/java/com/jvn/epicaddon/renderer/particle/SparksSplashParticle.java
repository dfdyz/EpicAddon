package com.jvn.epicaddon.renderer.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.particle.EpicFightParticleRenderTypes;
import yesman.epicfight.client.particle.HitBluntParticle;
import yesman.epicfight.client.particle.HitParticle;

public class SparksSplashParticle extends HitParticle {
    protected SparksSplashParticle(ClientLevel level, double x, double y, double z, double xd, double yd, double zd,SpriteSet spriteSet) {
        super(level, x, y, z,spriteSet);

    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float tick) {

    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicFightParticleRenderTypes.LIGHTNING;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SparksSplashParticle particle = new SparksSplashParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            return particle;
        }
    }

}
