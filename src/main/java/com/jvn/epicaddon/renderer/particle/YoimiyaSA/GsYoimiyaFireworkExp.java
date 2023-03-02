package com.jvn.epicaddon.renderer.particle.YoimiyaSA;

import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.TextureSheetParticle;

public class GsYoimiyaFireworkExp extends SingleQuadParticle {
    protected GsYoimiyaFireworkExp(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
    }

    protected GsYoimiyaFireworkExp(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
    }

    @Override
    public void render(VertexConsumer p_107678_, Camera p_107679_, float p_107680_) {
        super.render(p_107678_, p_107679_, p_107680_);

        //todo




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
    public ParticleRenderType getRenderType() {
        return new EpicAddonRenderType.EpicaddonParticle("particle/yoimiyas");
    }
}
