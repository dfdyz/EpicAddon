package com.jvn.epicaddon.renderer.particle;

import com.jvn.epicaddon.register.RegParticle;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.jvn.epicaddon.renderer.EpicAddonRenderType.SAO_DEATH_PARTICLE;

public class SAODeathParticle extends NoRenderParticle {
    protected SAODeathParticle(ClientLevel level, double x, double y, double z, double ex, double ey, double ez) {

        super(level, x, y, z,ex,ey,ez);
        this.lifetime = 3;

        Vec3 o = new Vec3((x + ex)/2, (y + ey)/2, (z + ez)/2);
        float per = 0.2f;
        int cx = (int) ((ex - x)/per);
        int cy = (int) ((ey - y)/per);
        int cz = (int) ((ez - z)/per);
        //System.out.format("%d %d %d", cx, cy, cz);
        for (int i = 0; i <= cx; i++) {
            for (int j = 0; j <= cy; j++){
                for (int k = 0; k <= cz; k++){
                    Vec3 vec3 = new Vec3(
                            x+(ex-x)*1.0f/cx*i,
                            y+(ey-y)*1.0f/cy*j,
                            z+(ez-z)*1.0f/cz*k);
                    Vec3 spd = vec3.subtract(o).normalize();
                    vec3 = vec3.subtract(spd.scale(0.3f));
                    spd = spd.scale(5);
                    level.addParticle(RegParticle.SAO_DEATH_I.get(), vec3.x, vec3.y, vec3.z, spd.x, spd.y, spd.z);
                }
            }
        }

        /*
        int cnt = this.random.nextInt() % 5 + 15;
        for (int i = 0; i < cnt; i++) {
            double ox = random.nextDouble()*2;
            double oy = random.nextDouble()*2;
            double oz = random.nextDouble()*2;
            level.addParticle(RegParticle.SAO_DEATH_I.get(), this.x, this.y, this.z, 0+ox, 3+oy, 0+oz);
        }*/
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SAO_DEATH_PARTICLE;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            SAODeathParticle particle = new SAODeathParticle(worldIn, x, y, z, xSpeed, ySpeed ,zSpeed);
            return particle;
        }
    }
}
