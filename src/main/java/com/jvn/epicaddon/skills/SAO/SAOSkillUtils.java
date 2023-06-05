package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.events.CameraEvent;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.jvn.epicaddon.register.RegMobEffect;
import com.jvn.epicaddon.register.RegPostEffect;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import static com.jvn.epicaddon.resources.EpicAddonAnimations.SAO_RAPIER_SA2_CAM;
import static com.jvn.epicaddon.resources.EpicAddonAnimations.SAO_RAPIER_SA2_CAM2;

public class SAOSkillUtils{

    public static void prevRapierSA2(LivingEntityPatch entityPatch){
        CameraEvent.SetAnim(SAO_RAPIER_SA2_CAM, (LivingEntity) entityPatch.getOriginal(), true);
    }

    public static void giveNoGravity(LivingEntityPatch entityPatch){
        LivingEntity entity = (LivingEntity) entityPatch.getOriginal();
        //entity.addEffect(new MobEffectInstance(RegMobEffect.NO_GRAVITY.get(), 18, 1));
    }

    public static OpenMatrix4f matrix4f = new OpenMatrix4f();
    public static void RapierSA2(LivingEntityPatch entityPatch){
        float yaw = entityPatch.getOriginal().getYRot();

        if(entityPatch.currentlyAttackedEntity.size() > 0){
            entityPatch.currentlyAttackedEntity.forEach((entity)->{
                if(entity instanceof LivingEntity) {
                    LivingEntity le = (LivingEntity)entity;
                    if(le.equals(entity)) return;
                    float dmg = entityPatch.getDamageTo(le,
                            entityPatch.getDamageSource(ExtendedDamageSource.StunType.LONG, EpicAddonAnimations.SAO_RAPIER_SA2, InteractionHand.MAIN_HAND),
                            InteractionHand.MAIN_HAND);
                    le.addEffect(new MobEffectInstance(RegMobEffect.WOUND.get(), 13, (int) (dmg*10)));
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
