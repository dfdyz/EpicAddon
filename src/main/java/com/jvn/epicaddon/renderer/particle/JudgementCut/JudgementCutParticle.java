package com.jvn.epicaddon.renderer.particle.JudgementCut;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.renderer.particle.SAO.SparksSplashHitParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JudgementCutParticle extends NoRenderParticle {
    public JudgementCutParticle(ClientLevel level, double x, double y, double z, double rx, double ry, double rz) {
        super(level, x, y, z, rx, ry, rz);

        //level.addParticle(RegParticle.JudgementCutTrail.get(), x, y, z,x, y, z);

        //EpicAddon.LOGGER.info(rx+"");
        lifetime = 10;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }

        for (int i = 0; i<3; i++){
            float r = random.nextFloat(5,8);
            float theta = random.nextFloat(0,360);
            float beta = random.nextFloat(60,90);

            float r2 = 13f - r;
            float theta2 = random.nextFloat(180+theta-45,180+theta+45);
            float beta2 = random.nextFloat(180+beta-45,180+beta-15);

            theta = (float) (theta/180*Math.PI);
            beta = (float) (beta/180*Math.PI);
            theta2 = (float) (theta2/180*Math.PI);
            beta2 = (float) (beta2/180*Math.PI);

            double dr = r*Math.sin(beta);
            double dx = dr*Math.cos(theta);
            double dy = r*Math.cos(beta);
            double dz = dr*Math.cos(theta);

            level.addParticle(RegParticle.JudgementCutTrail.get(),
                    dx + x,
                    dy + y + 5,
                    dz + z,
                    r2*Math.sin(beta2)*Math.cos(theta2) - dx,
                    r2*Math.cos(beta2) - dy,
                    r2*Math.sin(beta2)*Math.sin(theta2) - dz);
        }

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new JudgementCutParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
