package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.renderer.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.particle.HitParticleType;

//@OnlyIn(Dist.CLIENT)
//@Mod.EventBusSubscriber(modid = EpicAddon.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegParticle {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EpicAddon.MODID);
    public static final RegistryObject<SimpleParticleType> BLADE_TRAIL = PARTICLES.register("blade_trail", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPARKS_SPLASH = PARTICLES.register("sparks_splash",() -> new SimpleParticleType(true));
    public static final RegistryObject<HitParticleType> SPARKS_SPLASH_HIT = PARTICLES.register("sparks_splash_hit",() -> new HitParticleType(true, HitParticleType.RANDOM_WITHIN_BOUNDING_BOX, EpicAddonHitParticalType.Atker2Tar));
    public static final RegistryObject<SimpleParticleType> GENSHIN_BOW = PARTICLES.register("genshin_bow", () -> new SimpleParticleType(true));

    @Mod.EventBusSubscriber(modid = EpicAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class ParticleRegister{
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registryParticles(ParticleFactoryRegisterEvent event){
            ParticleEngine PE = Minecraft.getInstance().particleEngine;
            PE.register(BLADE_TRAIL.get(), BladeTrailParticle.Provider::new);
            PE.register(SPARKS_SPLASH.get(), SparksSplashParticle.Provider::new);
            PE.register(SPARKS_SPLASH_HIT.get(), SparksSplashHitParticle.Provider::new);
            PE.register(GENSHIN_BOW.get(), GenShinBowShootParticle.Provider::new);
        }
    }
}
