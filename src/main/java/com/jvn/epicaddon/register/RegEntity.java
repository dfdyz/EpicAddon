package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.jvn.epicaddon.renderer.particle.BladeTrailParticle;
import com.jvn.epicaddon.renderer.particle.GenShinBowShootParticle;
import com.jvn.epicaddon.renderer.particle.SparksSplashHitParticle;
import com.jvn.epicaddon.renderer.particle.SparksSplashParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.entity.AreaEffectBreath;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, EpicAddon.MODID);


    public static final RegistryObject<EntityType<GenShinArrow>> GENSHIN_ARROW = ENTITIES.register("genshin_arrow", () ->
            EntityType.Builder.<GenShinArrow>of(GenShinArrow::new, MobCategory.MISC)
                .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("genshin_arrow")
    );

    @Mod.EventBusSubscriber(modid = EpicAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class EntityRegister{
        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(GENSHIN_ARROW.get(), TippableArrowRenderer::new);
        }
    }

}
