package com.jvn.epicaddon.resources;

import com.google.common.collect.Lists;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.anim.*;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.events.CameraEvent;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.register.WeaponCollider;
import com.jvn.epicaddon.renderer.SwordTrail.IAnimSTOverride;
import com.jvn.epicaddon.skills.GenShin.YoimiyaSkillFunction;
import com.jvn.epicaddon.skills.SAO.SAOSkillUtils;
import com.jvn.epicaddon.utils.Trail;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

public class EpicAddonAnimations {
    public static List<CamAnim> CamAnimRegistry = Lists.newArrayList();
    //public static StaticAnimation Test;
    public static StaticAnimation SAO_SINGLE_SWORD_AUTO1;
    public static StaticAnimation SAO_DUAL_SWORD_HOLD;
    public static StaticAnimation SAO_DUAL_SWORD_NORMAL;
    public static StaticAnimation SAO_DUAL_SWORD_RUN;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO1;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO2;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO3;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO4;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO5;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO6;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO7;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO8;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO9;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO10;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO11;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO12;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO13;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO14;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO15;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO16;
    public static StaticAnimation SAO_SINGLE_SWORD_GUARD;
    public static StaticAnimation SAO_DOUBLE_CHOPPER;
    public static StaticAnimation SAO_RAPIER_IDLE;
    public static StaticAnimation SAO_RAPIER_AUTO1;
    public static StaticAnimation SAO_RAPIER_AUTO2;
    public static StaticAnimation SAO_RAPIER_AUTO3;
    public static StaticAnimation SAO_RAPIER_AUTO4;
    public static StaticAnimation SAO_RAPIER_AUTO5;
    public static StaticAnimation SAO_RAPIER_AIR;
    public static StaticAnimation SAO_RAPIER_SPECIAL_DASH;
    public static StaticAnimation SAO_RAPIER_SA2;
    public static StaticAnimation SAO_RAPIER_DASH;
    public static StaticAnimation DESTINY_AIM;
    public static StaticAnimation DESTINY_SHOT;
    public static StaticAnimation DESTINY_RELOAD;
    public static StaticAnimation GS_Yoimiya_Auto1;
    public static StaticAnimation GS_Yoimiya_Auto2;
    public static StaticAnimation GS_Yoimiya_Auto3;
    public static StaticAnimation GS_Yoimiya_Auto4;
    public static StaticAnimation GS_Yoimiya_Auto5;
    public static StaticAnimation GS_Yoimiya_SA;

    public static StaticAnimation GS_Yoimiya_FallAtk_Start;
    public static StaticAnimation GS_Yoimiya_FallAtk_Last;
    public static StaticAnimation GS_Yoimiya_FallAtk_Loop;

    public static StaticAnimation SR_BBB_IDLE;
    public static StaticAnimation SR_BBB_Auto1;
    public static StaticAnimation SR_BBB_Auto2;
    public static StaticAnimation SR_BBB_SA_CG;
    //public static StaticAnimation GS_BowFallAtk_Test2;
    public static CamAnim Yoimiya;
    public static CamAnim SAO_RAPIER_SA2_CAM;
    public static CamAnim SAO_RAPIER_SA2_CAM2;

    public static void registerAnimations(AnimationRegistryEvent event) {
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoadingEvent");
        event.getRegistryMap().put(EpicAddon.MODID, EpicAddonAnimations::Reg);
    }

    private static void Reg() {
        Models<?> models = FMLEnvironment.dist == Dist.CLIENT ? ClientModels.LOGICAL_CLIENT : Models.LOGICAL_SERVER;
        Model biped = models.biped;
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoading");
        //Test = new BasicAttackAnimation(0.2F, 0.4F, 0.6F, 0.8F, null, "Tool_R", "biped/test_anim", biped);

        //DUAL SWORD
        SAO_DUAL_SWORD_HOLD = new StaticAnimation(true, "biped/living/sao_dual_sword_hold", biped);
        SAO_DUAL_SWORD_NORMAL = new StaticAnimation(true, "biped/living/sao_dual_sword_hold_normal", biped);
        SAO_DUAL_SWORD_RUN = new MovementAnimation(true, "biped/living/sao_dual_sword_hold_run", biped);

        SAO_DUAL_SWORD_AUTO1 = new BasicAttackAnimation(0.16F, 0.05F, 0.6F, 0.65F, null, "Tool_R", "biped/sao_dual_sword_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO2 = new BasicAttackAnimation(0.2F, 0.002F, 0.6F, 0.28F, InteractionHand.OFF_HAND ,null, "Tool_L", "biped/sao_dual_sword_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO3 = new BasicAttackAnimation(0.13F, 0.05F, 0.45F, 0.28F, InteractionHand.OFF_HAND ,null, "Tool_L", "biped/sao_dual_sword_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO4 = new BasicAttackAnimation(0.13F, 0.05F, 0.5F, 0.4F, null, "Tool_R", "biped/sao_dual_sword_auto4", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO5 = new BasicAttackAnimation(0.02F, 0.02F, 1.45F, 1.4F,InteractionHand.OFF_HAND , null, "Tool_L", "biped/sao_dual_sword_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO6 = new BasicAttackAnimation(0.12F, 0.0F, 0.62F, 0.615F, null, "Tool_R", "biped/sao_dual_sword_auto6", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO7 = new BasicAttackAnimation(0.11F, 0.85F, 2.5F, 1.7F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto7", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.31F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.15F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO8 = new BasicAttackAnimation(0.05F, 0.4F, 1.8F, 1.2F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto8", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.4F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.9F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO9 = new BasicAttackAnimation(0.113F, 0.20F, 1.3F, 0.9F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto9", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_DUAL_SWORD_AUTO10 = new BasicAttackAnimation(0.115F, 0.05F, 1.125F, 0.965F, WeaponCollider.SAO_SWORD_DUAL_AUTO10, "Root", "biped/sao_dual_sword_auto10", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.45F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_DUAL_SWORD_AUTO11 = new BasicAttackAnimation(0.0F, 0.02F,0.41F, 0.3F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto11", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.08F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_DUAL_SWORD_AUTO12 = new BasicAttackAnimation(0.09F, 0.02F, 0.45F, 0.3F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto12", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_DUAL_SWORD_AUTO13 = new BasicAttackAnimation(0.16F, 0.002F, 0.35F, 0.28F, null, "Tool_R", "biped/sao_dual_sword_auto13", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO14 = new BasicAttackAnimation(0.02F, 0.001F, 0.2F, 0.19F, null, "Tool_R", "biped/sao_dual_sword_auto14", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO15 = new BasicAttackAnimation(0.3F, 0.26F, 0.41F, 0.23F, null, "Tool_R", "biped/sao_dual_sword_auto15", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_DUAL_SWORD_AUTO16 = new BasicAttackAnimation(0.265F, 0.16F, 0.58F, 0.35F, InteractionHand.OFF_HAND ,null, "Tool_L", "biped/sao_dual_sword_auto16", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_SINGLE_SWORD_GUARD = new StaticAnimation(0.25F, true, "biped/skill/sao_single_sword_guard", biped);

        SAO_DOUBLE_CHOPPER = new SpecailDashAtkAnimation(0.06F, 0.0F, 0.02F, 0.254F, 0.25F, WeaponCollider.SAO_SWORD_DASH, "Root", "biped/sao_dual_sword_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(14.7F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(4.0F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.0F, (entitypatch) -> {
                            if(entitypatch instanceof PlayerPatch){
                                PlayerPatch pp = (PlayerPatch)entitypatch;
                                float tmp = pp.getStamina() - pp.getMaxStamina()*(0.5f);
                                tmp = (tmp>=0) ? tmp : 0;
                                pp.setStamina(tmp);
                                LivingEntity entity = (LivingEntity)pp.getOriginal();
                                entity.setNoGravity(true);
                                //pp.getAnimator().playAnimation(EpicAddonAnimations.SAO_DUAL_SWORD_HOLD,0.0f);
                            }
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_END, (ep) -> {
                            if(ep instanceof PlayerPatch){
                                LivingEntity entity = (LivingEntity)((PlayerPatch)ep).getOriginal();
                                entity.setNoGravity(false);
                            }
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_END, (ep) -> {
                            //PostEffectEvent.PushPostEffectHighest(RegPostEffect.SpaceBroken, 3f);
                        }, StaticAnimation.Event.Side.SERVER)
                });

        //RAPIER
        SAO_RAPIER_IDLE = new StaticAnimation(true, "biped/living/sao_rapier_idle", biped);

        SAO_RAPIER_AUTO1 = new BasicAttackAnimation(0.12F, 0.067F, 0.167F, 0.2F, null, "Tool_R", "biped/sao_rapier_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);
        SAO_RAPIER_AUTO2 = new BasicAttackAnimation(0.12F, 0.033F, 0.133F, 0.2F, null, "Tool_R", "biped/sao_rapier_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);;
        SAO_RAPIER_AUTO3 = new BasicAttackAnimation(0.12F, 0.2F, 0.3F, 0.3F, null, "Tool_R", "biped/sao_rapier_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);;
        SAO_RAPIER_AUTO4 = new BasicAttackAnimation(0.12F, 0.033F, 0.2F, 0.2F, null, "Tool_R", "biped/sao_rapier_auto4", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);;
        SAO_RAPIER_AUTO5 = new BasicAttackAnimation(0.12F, 0.2F, 0.3F, 0.4F, null, "Tool_R", "biped/sao_rapier_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);;

        SAO_RAPIER_DASH  = new DashAttackAnimation(0.12F, 0.2F, 0.1F, 0.3F, 0.4F, WeaponCollider.SAO_RAPIER_DASH_SHORT, "Root", "biped/sao_rapier_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(14.7F))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_RAPIER_AIR  = new DashAttackAnimation(0.12F, 0.133F, 0.05F, 0.2F, 0.3F, WeaponCollider.SAO_RAPIER_DASH_SHORT, "Root", "biped/sao_rapier_air", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_RAPIER_SPECIAL_DASH  = new DashAttackAnimation(0.1F, 0.3F, 0.05F, 4.8333F, 4.1F, WeaponCollider.SAO_RAPIER_DASH, "Root", "biped/sao_rapier_dash_long", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(14.7F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(114514))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.21F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            if(ep instanceof PlayerPatch){
                                Entity entity = ((PlayerPatch)ep).getOriginal();
                                entity.setNoGravity(true);
                                ep.setMaxStunShield(114514.0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_END, (ep) -> {
                            if(ep instanceof PlayerPatch){
                                LivingEntity entity = (LivingEntity)((PlayerPatch)ep).getOriginal();
                                entity.setNoGravity(false);
                                ep.setMaxStunShield(0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, StaticAnimation.Event.Side.SERVER)
                });

        SAO_RAPIER_SA2  = new ScanAttackAnimation(0.0F, 0.5f, 1.48F, InteractionHand.MAIN_HAND, false,1000, WeaponCollider.SAO_RAPIER_SCAN, "Root", "biped/sao_rapier_sa2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(0))
                //.addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                //.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                //.addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            SAOSkillUtils.prevRapierSA2(ep);
                        }, StaticAnimation.Event.Side.CLIENT),
                        StaticAnimation.Event.create(0.65f, (ep) -> {
                            SAOSkillUtils.RapierSA2(ep);
                        }, StaticAnimation.Event.Side.BOTH),
                        StaticAnimation.Event.create(1.15f, (ep) -> {
                            SAOSkillUtils.postRapierSA2(ep);
                        }, StaticAnimation.Event.Side.CLIENT)
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.2f);

        DESTINY_AIM = new AimAnimation(false, "biped/destiny_aim_mid", "biped/destiny_aim_up", "biped/destiny_aim_down", "biped/destiny_aim_lying", biped);
        DESTINY_SHOT = new ReboundAnimation(false, "biped/destiny_shoot_mid", "biped/destiny_shoot_up", "biped/destiny_shoot_down", "biped/destiny_shoot_lying", biped);
        DESTINY_RELOAD = new StaticAnimation(false, "biped/destiny_reload", biped);

        SAO_SINGLE_SWORD_AUTO1 = new BasicAttackAnimation(0.12F, 0.25F, 0.625F, 1F, null, "Tool_R", "biped/single_blade_1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 4.5f);

        GS_Yoimiya_Auto1 = new ScanAttackAnimation(0.1F, 0.62F, 0.8333F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.4F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_L");
                           }, StaticAnimation.Event.Side.BOTH),
                        StaticAnimation.Event.create(0.585F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_L");
                        }, StaticAnimation.Event.Side.BOTH),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.75f);

        GS_Yoimiya_Auto2 = new ScanAttackAnimation(0.1F, 0.7F, 0.98F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.6F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_R");
                        }, StaticAnimation.Event.Side.BOTH),
                });

        GS_Yoimiya_Auto3 = new ScanAttackAnimation(0.1F, 0.88F, 1.03F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.95f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.84F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_L");
                        }, StaticAnimation.Event.Side.BOTH),
                });

        GS_Yoimiya_Auto4 = new ScanAttackAnimation(0.05F, 2.12F, 2.733F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto4", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(1.2083F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_L");
                        }, StaticAnimation.Event.Side.BOTH),
                        StaticAnimation.Event.create(1.7916F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_R");
                        }, StaticAnimation.Event.Side.BOTH),
                        StaticAnimation.Event.create(2.0416F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep,"Tool_L");
                        }, StaticAnimation.Event.Side.BOTH),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3.1f);

        GS_Yoimiya_Auto5 = new ScanAttackAnimation(0.02F, 0.2F, 1.51F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto5", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.7083F, (ep) -> {
                            YoimiyaSkillFunction.BowShoot(ep, "Tool_L");
                        }, StaticAnimation.Event.Side.BOTH),
                });

        GS_Yoimiya_SA = new ScanAttackAnimation(0.02F, 0.5F, 4.56F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_sa", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            CameraEvent.SetAnim(Yoimiya, ep.getOriginal(), true);
                        }, StaticAnimation.Event.Side.CLIENT),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            YoimiyaSkillFunction.YoimiyaSAFirework(ep);
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(2.375F, (ep) -> {
                            YoimiyaSkillFunction.YoimiyaSA(ep);
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            if(ep instanceof PlayerPatch){
                                Player player = (Player) ((PlayerPatch)ep).getOriginal();
                                player.setDeltaMovement(0,0,0);
                                player.setPos(player.position());
                                player.setSpeed(0);
                                player.setNoGravity(true);
                                ep.setMaxStunShield(114514.0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_END, (ep) -> {
                            if(ep instanceof PlayerPatch){
                                LivingEntity entity = (LivingEntity)((PlayerPatch)ep).getOriginal();
                                entity.setNoGravity(false);
                                ep.setMaxStunShield(0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, StaticAnimation.Event.Side.SERVER)
                });

        //GS_BowFallAtk_Loop = new FallAtkAnimLoop(0.1f,true,"biped/fall_attack_test_l",biped,Animations.SWORD_AIR_SLASH);
        GS_Yoimiya_FallAtk_Last = new FallAtkFinalAnim(0.05F, 0.5F, 0.8F, 2.1F, WeaponCollider.GenShin_Bow_FallAttack, "Root", "biped/gs_yoimiya_fall_atk_last", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(0.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true)
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicAddonSounds.GENSHIN_BOW_FALLATK)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicAddonSounds.GENSHIN_BOW_FALLATK)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.setter(114514))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.setter(1))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 7f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[]{
                        StaticAnimation.Event.create(0.45f, (ep) -> {
                            YoimiyaSkillFunction.SendParticle(
                                    ep.getOriginal().getLevel(),
                                    RegParticle.GENSHIN_BOW_LANDING.get(),
                                    ep.getOriginal().position()
                            );
                        }, StaticAnimation.Event.Side.SERVER)
                })
        ;

        GS_Yoimiya_FallAtk_Loop = new FallAtkLoopAnim(0.1f,"biped/gs_yoimiya_fall_atk_loop", biped, GS_Yoimiya_FallAtk_Last);

        GS_Yoimiya_FallAtk_Start = new FallAtkStartAnim(0.1f,"biped/gs_yoimiya_fall_atk_start", biped, GS_Yoimiya_FallAtk_Loop)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3.6f);

        //GS_BowFallAtk_Test = new FallAtkAnim(GS_BowFallAtk_Test1, GS_BowFallAtk_Test2, Animations.SWORD_AIR_SLASH);

        SR_BBB_IDLE = new  StaticAnimation(true, "biped/living/sr_bbb_idle", biped);
        SR_BBB_Auto1 = new BasicAttackAnimation(0.12F, 0.35F, 0.73F, 0.9F, WeaponCollider.SR_BBb_Normal, "Tool_R", "biped/sr_bbb_combo1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.2f);

        SR_BBB_Auto2 = new BasicAttackAnimation(0.08F, 0.33F, 0.73F, 0.85F, WeaponCollider.SR_BBb_Normal, "Tool_R", "biped/sr_bbb_combo2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.2f);

        SR_BBB_SA_CG = new BasicAttackAnimation(0.12F, 1.6666F, 2.43F, 2.6F, WeaponCollider.SR_BBb_Normal, "Tool_R", "biped/sr_bbb_sa_cg", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS,new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.7083F, (ep) -> {

                        }, StaticAnimation.Event.Side.CLIENT),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 1.8f);

        ((GravityRestter) GS_Yoimiya_FallAtk_Start).setMode(false);
        ((GravityRestter) SAO_RAPIER_SPECIAL_DASH).setMode(false);
        ((GravityRestter) GS_Yoimiya_SA).setMode(false);

        if(FMLEnvironment.dist == Dist.CLIENT){
            ((IAnimSTOverride)SAO_RAPIER_SPECIAL_DASH).setLifeTimeOverride(10).setPosOverride(
                    new Trail(0f,0.2f,-0.3f,0f,-0.2f,-0.3f,0,0,0,0));
            ((IAnimSTOverride)Animations.SWORD_AUTO1).setColorOverride(new Trail(0,0,-0.2f,0,-0.2f,-1.6f,255,30,30,120));
        }

        LOGGER.info("EpicAddon AnimLoaded");
    }

    public static void RegCamAnims(){
        Yoimiya = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/yoimiya.json"));
        SAO_RAPIER_SA2_CAM = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/sao_rapier_sa2.json"));
        SAO_RAPIER_SA2_CAM2 = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/sao_rapier_sa2_post.json"));
    }

    public static CamAnim regCamAnim(CamAnim anim){
        CamAnimRegistry.add(anim);
        return anim;
    }
}
