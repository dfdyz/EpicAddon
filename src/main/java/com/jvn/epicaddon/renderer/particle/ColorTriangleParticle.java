package com.jvn.epicaddon.renderer.particle;

import com.jvn.epicaddon.renderer.HealthBarRenderer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public abstract class ColorTriangleParticle extends Particle {


    protected ColorTriangleParticle(ClientLevel p_107234_, double p_107235_, double p_107236_, double p_107237_) {
        super(p_107234_, p_107235_, p_107236_, p_107237_);
    }

    public ColorTriangleParticle(ClientLevel p_107239_, double p_107240_, double p_107241_, double p_107242_, double p_107243_, double p_107244_, double p_107245_) {
        super(p_107239_, p_107240_, p_107241_, p_107242_, p_107243_, p_107244_, p_107245_);
    }

    protected float size = 1.0f;


    public void render(VertexConsumer vertexBuilder, Camera camera, float partialTicks) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp((double)partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)partialTicks, this.zo, this.z) - vec3.z());
        Quaternion quaternion = camera.rotation();

        float a,b,c;
        a = this.random.nextInt(-30,120);
        b = this.random.nextInt(90,240);
        c = this.random.nextInt(210,360);

        //Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
        //vector3f1.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{
                new Vector3f((float) Math.cos(HealthBarRenderer.getAngF(a)), (float) Math.sin(HealthBarRenderer.getAngF(a)), 0.0F),
                new Vector3f((float) Math.cos(HealthBarRenderer.getAngF(b)), (float) Math.sin(HealthBarRenderer.getAngF(b)), 0.0F),
                new Vector3f((float) Math.cos(HealthBarRenderer.getAngF(c)), (float) Math.sin(HealthBarRenderer.getAngF(c)), 0.0F)
        };
        //float f4 = this.getQuadSize(partialTicks);

        float size_tmp = getSize();
        for(int i = 0; i < 3; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(size_tmp);
            vector3f.add(f, f1, f2);
        }

        vertexBuilder.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).endVertex();
        vertexBuilder.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).endVertex();
        vertexBuilder.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).color(this.rCol, this.gCol, this.bCol, this.alpha).endVertex();
    }

    public float getSize() {
        return this.size;
    }

    public Particle scale(float size) {
        this.size *= size;
        return super.scale(size);
    }

}
