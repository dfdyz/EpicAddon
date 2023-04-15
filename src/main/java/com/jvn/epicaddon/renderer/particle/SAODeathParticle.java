package com.jvn.epicaddon.renderer.particle;

import com.jvn.epicaddon.events.DeathParticleHandler;
import com.jvn.epicaddon.register.RegParticle;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
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
    protected SAODeathParticle(ClientLevel level, double x, double y, double z, double _0, double _1, double _2) {
        super(level, x, y, z,0,0,0);
        this.lifetime = 3;

        int eid = (int) Double.doubleToLongBits(_0);
        DeathParticleHandler.ParticleTransformed transform = DeathParticleHandler.TransformPool.get(eid);
        DeathParticleHandler.TransformPool.remove(eid);

        float per = 0.2f;

        Vec3 rect3d = transform.maxV.subtract(transform.minV);

        int cx = (int) ((rect3d.x)/per);
        int cy = (int) ((rect3d.y)/per);
        int cz = (int) ((rect3d.z)/per);
        Vec3 center = transform.minV.add(rect3d.scale(0.5));
        //System.out.format("%d %d %d", cx, cy, cz);
        for (int i = 0; i <= cx; i++) {
            for (int j = 0; j <= cy; j++){
                for (int k = 0; k <= cz; k++){
                    Vec3 vec3 = new Vec3(
                            transform.minV.x + rect3d.x/cx*i,
                            transform.minV.y + rect3d.y/cy*j,
                            transform.minV.z + rect3d.z/cz*k);
                    Vec3 spd = vec3.subtract(center).normalize();
                    vec3 = vec3.subtract(spd.scale(0.4f))
                            .yRot((float) (transform.rot.z/180*Math.PI))
                            .xRot((float) (transform.rot.x/180*Math.PI))
                            .yRot((float) (transform.rot.y/180*Math.PI))
                            .add(transform.offset).add(x,y,z);

                    float spdr = 0.8f * random.nextFloat(0.4f);

                    spd = spd.scale(spdr)
                            .yRot((float) (transform.rot.z/180*Math.PI))
                            .xRot((float) (transform.rot.x/180*Math.PI))
                            .yRot((float) (transform.rot.y/180*Math.PI));
                            //.add(new Vec3(0.2,0,0.2));


                    level.addParticle(RegParticle.SAO_DEATH_I.get(), vec3.x, vec3.y, vec3.z, spd.x, spd.y*0.5f, spd.z);
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
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double _0, double _1, double _2, double _3, double _4, double _5) {
            SAODeathParticle particle = new SAODeathParticle(worldIn, _0, _1, _2, _3, 0 ,0);
            return particle;
        }
    }
}
