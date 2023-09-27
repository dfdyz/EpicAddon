package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.events.CameraEvent;
import com.jvn.epicaddon.events.PostEffectEvent;
import com.jvn.epicaddon.register.RegMobEffect;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.register.RegPostEffect;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import static com.jvn.epicaddon.resources.EpicAddonAnimations.*;

public class SAOSkillAnimUtils {

    public static class RapierSA {
        public static void prev(LivingEntityPatch entityPatch){
            CameraEvent.SetAnim(SAO_RAPIER_SA2_CAM, (LivingEntity) entityPatch.getOriginal(), true);
        }
        //public static OpenMatrix4f matrix4f = new OpenMatrix4f();
        public static void HandleAtk(LivingEntityPatch entityPatch){
            //float yaw = entityPatch.getOriginal().getYRot();
            //System.out.println("????");
            if(entityPatch.isLogicalClient()){
                PostEffectEvent.PushPostEffectHighest(RegPostEffect.SpaceBroken, 0.75f, entityPatch.getOriginal().position());
            }
            else {
                if(entityPatch.getCurrenltyAttackedEntities().size() > 0){
                    //System.out.println("????");
                    entityPatch.getCurrenltyAttackedEntities().forEach((entity)->{
                        if(entity instanceof LivingEntity) {
                            LivingEntity le = (LivingEntity)entity;
                            if(le.equals(entityPatch.getOriginal())) return;
                            float dmg = entityPatch.getValidItemInHand(InteractionHand.MAIN_HAND).getDamageValue();
                            le.addEffect(new MobEffectInstance(RegMobEffect.WOUND.get(), 13, (int) (dmg*10)));
                            le.addEffect(new MobEffectInstance(RegMobEffect.STOP.get(), 13, 1));
                        }
                    });
                }
            }
        }

        public static void post(LivingEntityPatch entityPatch){
            //entityPatch.getOriginal().setInvisible(false);
            CameraEvent.SetAnim(SAO_RAPIER_SA2_CAM2, (LivingEntity) entityPatch.getOriginal(), true);
        }
    }

    public static class DMC5_V_JC{
        public static void prev(LivingEntityPatch entityPatch){
            if(entityPatch.isLogicalClient()){
                CameraEvent.SetAnim(DMC_V_PREV, (LivingEntity) entityPatch.getOriginal(), true);
            }
            else {
                if(entityPatch instanceof ServerPlayerPatch pp){
                    SkillContainer sc = pp.getSkill(SkillSlots.WEAPON_INNATE);
                    if(sc.getDataManager().hasData(SingleSwordSASkills.Invincible)){
                        sc.getDataManager().setData(SingleSwordSASkills.Invincible, true);
                    }
                }
            }
        }



        public static void HandleAtk(LivingEntityPatch entityPatch){
            //CameraEvent.SetAnim(SAO_RAPIER_SA2_CAM, (LivingEntity) entityPatch.getOriginal(), true);

            if(entityPatch.isLogicalClient()){

            }
            else {
                if(entityPatch.getCurrenltyAttackedEntities().size() > 0){
                    //System.out.println("????");
                    entityPatch.getCurrenltyAttackedEntities().forEach((entity)->{
                        if(entity instanceof LivingEntity) {
                            LivingEntity le = (LivingEntity)entity;
                            if(le.equals(entityPatch.getOriginal())) return;
                            float dmg = entityPatch.getValidItemInHand(InteractionHand.MAIN_HAND).getDamageValue();
                            le.addEffect(new MobEffectInstance(RegMobEffect.WOUND.get(), 33, (int) (dmg*10)));
                            le.addEffect(new MobEffectInstance(RegMobEffect.STOP.get(), 42, 1));
                        }
                    });
                }
            }
        }
        //public static OpenMatrix4f matrix4f = new OpenMatrix4f();
        public static void post1(LivingEntityPatch entityPatch){
            if(entityPatch.isLogicalClient()){
                Level worldIn = entityPatch.getOriginal().getLevel();
                Vec3 pos = entityPatch.getOriginal().position();
                worldIn.addParticle(RegParticle.JudgementCut.get() ,pos.x,pos.y,pos.z,0,0,0);


                PostEffectEvent.PushPostEffectHighest(
                        new PostEffectEvent.PostEffectTimePair(
                                RegPostEffect.SpaceBroken,
                                1.58f,//1.58f,
                                (time) -> {
                                    return new float[]{(float) pos.x, (float) pos.y, (float) pos.z};
                                },
                                pos)
                );
                PostEffectEvent.PushPostEffectMiddle(RegPostEffect.WhiteFlush, 0.25f, pos);
            }
        }


        public static void post(LivingEntityPatch entityPatch){
            if(entityPatch.isLogicalClient()){

            }
            else {
                if(entityPatch instanceof ServerPlayerPatch pp){
                    SkillContainer sc = pp.getSkill(SkillSlots.WEAPON_INNATE);
                    if(sc.getDataManager().hasData(SingleSwordSASkills.Invincible)){
                        sc.getDataManager().setData(SingleSwordSASkills.Invincible, false);
                    }
                }
            }
        }
    }




    public static void giveNoGravity(LivingEntityPatch entityPatch){
        LivingEntity entity = (LivingEntity) entityPatch.getOriginal();
        //entity.addEffect(new MobEffectInstance(RegMobEffect.NO_GRAVITY.get(), 18, 1));
    }
}
