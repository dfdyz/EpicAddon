package com.jvn.epicaddon.renderer.particle.YoimiyaSA;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public class GsYoimiyaFirework extends TextureSheetParticle {
    public GsYoimiyaFirework(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    public GsYoimiyaFirework(ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy, dz);
    }


    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public ParticleRenderType getRenderType() {
        return null;
    }
}
