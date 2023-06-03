package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.events.CameraEvent;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.jvn.epicaddon.mobeffects.MobEffectEx;
import com.jvn.epicaddon.mobeffects.MobEffectInstanceEx;
import com.jvn.epicaddon.mobeffects.MobEffectUtils;
import com.jvn.epicaddon.register.RegMobEffect;
import com.jvn.epicaddon.register.RegPostEffect;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

import static com.jvn.epicaddon.resources.EpicAddonAnimations.SAO_RAPIER_SA2_CAM;
import static com.jvn.epicaddon.resources.EpicAddonAnimations.SAO_RAPIER_SA2_CAM2;

public class SAOSkillUtils{

    public static void prevRapierSA2(LivingEntityPatch entityPatch){
        CameraEvent.SetAnim(SAO_RAPIER_SA2_CAM, (LivingEntity) entityPatch.getOriginal(), true);
    }

    public static OpenMatrix4f matrix4f = new OpenMatrix4f();
    public static void RapierSA2(LivingEntityPatch entityPatch){
        float yaw = entityPatch.getOriginal().getYRot();

        if(entityPatch.currentlyAttackedEntity.size() > 0){
            entityPatch.currentlyAttackedEntity.forEach((entity)->{
                if(entity instanceof LivingEntity) {
                    LivingEntity le = (LivingEntity)entity;
                    float dmg = entityPatch.getDamageTo(le,
                            entityPatch.getDamageSource(ExtendedDamageSource.StunType.LONG, EpicAddonAnimations.SAO_RAPIER_SA2, InteractionHand.MAIN_HAND),
                            InteractionHand.MAIN_HAND);
                    MobEffectInstanceEx effectIns = new MobEffectInstanceEx((MobEffectEx) RegMobEffect.WOUND.get(), 13, 5);
                    effectIns.setData(new float[]{dmg});
                    le.addEffect(effectIns);
                    le.addEffect(new MobEffectInstance(RegMobEffect.STOP.get(), 13, 1));
                }
            });
        }

        PostEffectEvent.PushPostEffectHighest(RegPostEffect.SpaceBroken, 0.85f, entityPatch.getOriginal().position());
    }


    public static void postRapierSA2(LivingEntityPatch entityPatch){
        //entityPatch.getOriginal().setInvisible(false);
        CameraEvent.SetAnim(SAO_RAPIER_SA2_CAM2, (LivingEntity) entityPatch.getOriginal(), true);
    }
}
