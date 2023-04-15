package com.jvn.epicaddon.events;

import com.google.common.collect.Lists;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.data.loot.EpicAddonLootTables;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.utils.GlobalVal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;

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
        GlobalVal.ANGInternal += 0.05f * ClientConfig.cfg.RotSpeed;
        GlobalVal.WANG += 0.05f * 0.7f;
        GlobalVal.WX = (float) Math.cos(GlobalVal.WANG);
        GlobalVal.WZ = -(float) Math.sin(GlobalVal.WANG);

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


        String regName = entity.getType().getRegistryName().toString();

        DeathParticleHandler.ParticleTransform transformType = DeathParticleHandler.TransformType.get(regName);

        if(transformType == null){
            Vec3 pos = entity.position();
            AABB box = entity.getBoundingBox();
            //type == 1
            float rot = entity.yBodyRot;

            Vec3 minVec = (new Vec3(box.minX,box.minY,box.minZ)).subtract(pos);
            Vec3 maxVec = (new Vec3(box.maxX,box.maxY,box.maxZ)).subtract(pos);

            int eid = entity.getId();
            double patchedEid = Double.longBitsToDouble(eid);

            Vec3 rotation = new Vec3(0, -rot, 0);

            DeathParticleHandler.TransformPool.putIfAbsent(eid,
                    new DeathParticleHandler.ParticleTransformed(Vec3.ZERO, minVec, maxVec, rotation));

            //System.out.format("put %s\n", pos.toString());

            level.addParticle(RegParticle.SAO_DEATH.get(),
                    pos.x,pos.y,pos.z,
                    patchedEid,0,0);
        }
        else {
            Vec3 pos = entity.position();

            Vec3 minVec, maxVec;
            if(transformType.type <= 1){
                AABB box = entity.getBoundingBox();
                minVec = (new Vec3(box.minX,box.minY,box.minZ)).subtract(pos);
                maxVec = (new Vec3(box.maxX,box.maxY,box.maxZ)).subtract(pos);
            }
            else {
                minVec = transformType.minV;
                maxVec = transformType.maxV;
            }

            float rx ,ry ,rz;
            rx = 0;
            ry = 0;
            rz = 0;
            if(transformType.type == 1 || transformType.type == 2 || transformType.type == 4){
                ry -= entity.yBodyRot;
            }
            if(transformType.type >= 3){
                rx += (float) transformType.rot.x;
                ry += (float) transformType.rot.y;
                rz += (float) transformType.rot.z;
            }
            //type == 1
            Vec3 rotation = new Vec3(rx, ry, rz);

            int eid = entity.getId();
            double patchedEid = Double.longBitsToDouble(eid);

            if(transformType.type >= 2){
                DeathParticleHandler.TransformPool.putIfAbsent(eid,
                        new DeathParticleHandler.ParticleTransformed(transformType.offset, minVec, maxVec, rotation));
            }
            else {
                DeathParticleHandler.TransformPool.putIfAbsent(eid,
                        new DeathParticleHandler.ParticleTransformed(Vec3.ZERO, minVec, maxVec, rotation));
            }
            //System.out.format("put %s\n", pos.toString());
            level.addParticle(RegParticle.SAO_DEATH.get(),
                    pos.x,pos.y,pos.z,
                    patchedEid,0,0);
        }
    }
}
