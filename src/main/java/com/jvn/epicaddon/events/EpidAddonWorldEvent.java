package com.jvn.epicaddon.events;

import com.google.common.collect.Lists;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.data.loot.EpicAddonLootTables;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.utils.GlobalVal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.data.loot.EpicFightLootTables;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID)
public class EpidAddonWorldEvent {
    @SubscribeEvent
    public static void onLootTableRegistry(final LootTableLoadEvent event) {
        EpicAddonLootTables.modifyVanillaLootPools(event);
    }


    @SubscribeEvent
    public static void OnEntityDeath(LivingDeathEvent event){
        LivingEntity entity = event.getEntityLiving();
        Level level = entity.getLevel();
        if(!level.isClientSide()) return;
        //System.out.println(entity.getType().getRegistryName().toString());
        DeathEntities.add(entity);

            //System.out.println(box.toString());

    }

    public static final LinkedList<LivingEntity> DeathEntities = Lists.newLinkedList();
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderWorldLast(TickEvent.RenderTickEvent event) {
        if(event.phase == TickEvent.Phase.START) return;
        DeathEntities.forEach((entity) -> {
            if(entity.deathTime >= 18){
                ShootDeathParticle(entity);
            }
        });


        DeathEntities.removeIf((entity) -> {
            return entity.deathTime >= 18;
        });
    }

    private static void ShootDeathParticle(LivingEntity entity){
        //System.out.println("dddddd");
        Level level = entity.getLevel();
        Vec3 pos = entity.position();
        AABB box = entity.getBoundingBox();
        level.addParticle(RegParticle.SAO_DEATH.get(),
                box.minX,box.minY,box.minZ,
                box.maxX,box.maxY,box.maxZ);
    }
}
