package com.jvn.epicaddon.renderer.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.main.EpicFightMod;

public class EpicAddonParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EpicFightMod.MODID);

    public static final RegistryObject<SimpleParticleType> TRIANGLE = PARTICLES.register("triangle", () -> new SimpleParticleType(true));

}
