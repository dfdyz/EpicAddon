package com.jvn.epicaddon.renderer.particle.JudgementCut;

import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class JCBladeTrail extends SingleQuadParticle {
    public JCBladeTrail(ClientLevel level, double x, double y, double z, double rx, double ry, double rz) {
        super(level, x, y, z, rx, ry, rz);
        this.lifetime = 60;
        this.xd = rx;
        this.yd = ry;
        this.zd = rz;
    }

    @Override
    protected float getU0() {
        return 0;
    }

    @Override
    protected float getU1() {
        return 0;
    }

    @Override
    protected float getV0() {
        return 0;
    }

    @Override
    protected float getV1() {
        return 0;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float pt) {
        Vec3 camPos = camera.getPosition();
        //Quaternion quaternion = camera.rotation();
        //float t = Math.min((age+pt)/lifetime*2,1f);

        Vec3 Start = (new Vec3(this.x, this.y, this.z)).subtract(camPos);
        Vec3 DirOrEnd = (new Vec3(this.xd, this.yd, this.zd));  //Dir

        //System.out.println(111);

        //Vec3 Cam2Start = Start.subtract(camPos);

        float w = 0.3f;
        Vec3 Right = Start.cross(DirOrEnd).normalize().multiply(w,w,w);

        //System.out.println(t);

        DirOrEnd = Start.add(DirOrEnd);
        Vec3 p1 = Start.add(1,0,1);
        Vec3 p2 = Start.subtract(1,0,1);
        Vec3 p3 = DirOrEnd.subtract(1,0,1);
        Vec3 p4 = DirOrEnd.add(1,0,1);

        int j = this.getLightColor(pt);
        buffer.vertex(p1.x, p1.y, p1.z).color(1,1,1,1).uv(1, 1).uv2(j).endVertex();
        buffer.vertex(p2.x, p2.y, p2.z).color(1,1,1,1).uv(1, 0).uv2(j).endVertex();
        buffer.vertex(p3.x, p3.y, p3.z).color(1,1,1,1).uv(0, 0).uv2(j).endVertex();
        buffer.vertex(p4.x, p4.y, p4.z).color(1,1,1,1).uv(0, 1).uv2(j).endVertex();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return EpicAddonRenderType.BladeTrail;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new JCBladeTrail(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}
