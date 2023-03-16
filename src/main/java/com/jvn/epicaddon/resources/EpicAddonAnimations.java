package com.jvn.epicaddon.resources;

import com.google.common.collect.Lists;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.anim.BowAtkAnim;
import com.jvn.epicaddon.api.anim.GravityRestter;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.jvn.epicaddon.entity.projectile.YoimiyaSAArrow;
import com.jvn.epicaddon.events.CameraEvent;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.register.WeaponCollider;
import com.jvn.epicaddon.renderer.SwordTrail.IAnimSTOverride;
import com.jvn.epicaddon.utils.GlobalVal;
import com.jvn.epicaddon.utils.Trail;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
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
    public static CamAnim Yoimiya;

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

        SAO_DOUBLE_CHOPPER = new DashAttackAnimation(0.06F, 0.0F, 0.02F, 0.254F, 0.25F, WeaponCollider.SAO_SWORD_DASH, "Root", "biped/sao_dual_sword_dash", biped)
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
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5F))
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

        DESTINY_AIM = new AimAnimation(false, "biped/destiny_aim_mid", "biped/destiny_aim_up", "biped/destiny_aim_down", "biped/destiny_aim_lying", biped);
        DESTINY_SHOT = new ReboundAnimation(false, "biped/destiny_shoot_mid", "biped/destiny_shoot_up", "biped/destiny_shoot_down", "biped/destiny_shoot_lying", biped);
        DESTINY_RELOAD = new StaticAnimation(false, "biped/destiny_reload", biped);

        SAO_SINGLE_SWORD_AUTO1 = new BasicAttackAnimation(0.12F, 0.25F, 0.625F, 1F, null, "Tool_R", "biped/single_blade_1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 4.5f);

        GS_Yoimiya_Auto1 = new BowAtkAnim(0.1F, 0.62F, 0.8333F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.4F, (ep) -> {
                            BowShoot(ep, "Tool_L");
                           }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(0.585F, (ep) -> {
                            BowShoot(ep, "Tool_L");
                        }, StaticAnimation.Event.Side.SERVER),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.75f);

        GS_Yoimiya_Auto2 = new BowAtkAnim(0.1F, 0.7F, 0.98F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.6F, (ep) -> {
                            BowShoot(ep, "Tool_R");
                        }, StaticAnimation.Event.Side.SERVER),
                });

        GS_Yoimiya_Auto3 = new BowAtkAnim(0.1F, 0.88F, 1.03F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 2.95f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.84F, (ep) -> {
                            BowShoot(ep, "Tool_L");
                        }, StaticAnimation.Event.Side.SERVER),
                });

        GS_Yoimiya_Auto4 = new BowAtkAnim(0.05F, 2.12F, 2.733F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto4", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(1.2083F, (ep) -> {
                            BowShoot(ep, "Tool_L");
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(1.7916F, (ep) -> {
                            BowShoot(ep, "Tool_R");
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(2.0416F, (ep) -> {
                            BowShoot(ep, "Tool_L");
                        }, StaticAnimation.Event.Side.SERVER),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3.1f);

        GS_Yoimiya_Auto5 = new BowAtkAnim(0.02F, 0.2F, 1.51F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_auto5", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3.1f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.7083F, (ep) -> {
                            BowShoot(ep, "Tool_L");
                        }, StaticAnimation.Event.Side.SERVER),
                });

        GS_Yoimiya_SA = new BowAtkAnim(0.02F, 0.5F, 4.56F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,"Root", "biped/gs_yoimiya_sa", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED, 3f)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            CameraEvent.SetAnim(Yoimiya, ep.getOriginal());
                        }, StaticAnimation.Event.Side.CLIENT),
                        StaticAnimation.Event.create(StaticAnimation.Event.ON_BEGIN, (ep) -> {
                            YoimiyaSAFirework(ep);
                        }, StaticAnimation.Event.Side.SERVER),
                        StaticAnimation.Event.create(2.375F, (ep) -> {
                            YoimiyaSA(ep);
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
    }

    public static CamAnim regCamAnim(CamAnim anim){
        CamAnimRegistry.add(anim);
        return anim;
    }

    private static void BowShoot(LivingEntityPatch<?> entitypatch, String joint){
        if(entitypatch.currentlyAttackedEntity.size() > 0){
            Entity target = entitypatch.currentlyAttackedEntity.get(0);
            Level worldIn = entitypatch.getOriginal().getLevel();
            Vec3 handPos = getPosByTick(entitypatch,0.4f,joint);
            if(target.equals(entitypatch.getOriginal())){
                float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
                Vec3 shootVec = new Vec3(Math.cos(ang), 0 , Math.sin(ang));
                Vec3 shootPos = handPos.add(shootVec.x,0,shootVec.z);

                GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(shootVec.x(), 0.1f, shootVec.z(), 4.2f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }
            else {
                Vec3 shootPos = new Vec3(handPos.x, entitypatch.getOriginal().getEyeY() ,handPos.z);
                Vec3 shootTarget = target.position();
                shootTarget = new Vec3(shootTarget.x,target.getEyeY(),shootTarget.z);
                Vec3 shootVec = shootTarget.subtract(shootPos);
                shootPos = shootPos.add((new Vec3(shootVec.x,0,shootVec.z)).normalize());

                GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 4.2f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }

            if(worldIn instanceof ServerLevel){
                //Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
                ((ServerLevel)worldIn).sendParticles(RegParticle.GENSHIN_BOW.get() ,handPos.x,handPos.y,handPos.z,0,1D,1D,0.9019607D,1D);
            }
            entitypatch.playSound(EpicAddonSounds.GENSHIN_BOW, 0.0F, 0.0F);
        }
    }

    public static final Vec3[] Positions = new Vec3[]{
            new Vec3(-5,1.1,-2),
            new Vec3(-4.5,0.2,0.3),
            new Vec3(-3,0.8,3),
            new Vec3(-3,1.3,-3),

            new Vec3(-4,1.2,-2),
            new Vec3(-3,0.4,2),
            new Vec3(-4.5,0.2,0.3),
            new Vec3(-4,1.2,-2),
    };

    public static final int[] lifetimes = {
            3,3,3,3,1,2,2,1
    };
    public static void YoimiyaSAFirework(LivingEntityPatch<?> entitypatch){
        Entity entity = entitypatch.getOriginal();
        Level worldIn = entity.getLevel();

        if (worldIn instanceof ServerLevel){
            float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);

            Vec3 Center = entity.position();

            for (int i=0; i<Positions.length; ++i) {
                Vec3 spos = Center.add(Positions[i].yRot(-ang));
                ((ServerLevel) worldIn).sendParticles(RegParticle.GS_YOIMIYA_SA.get(),spos.x,spos.y+0.2f,spos.z,0,lifetimes[i],i,1,1D);
            }
        }

        SoundEvent[] sounds = new SoundEvent[]{
                EpicAddonSounds.Yoimiya_Skill1,
                EpicAddonSounds.Yoimiya_Skill2,
                EpicAddonSounds.Yoimiya_Skill3
        };

        entitypatch.playSound(sounds[Math.abs(GlobalVal.random.nextInt())%3],0.0F, 0.0F);
    }

    public static void YoimiyaSA(LivingEntityPatch<?> entitypatch){
        Level worldIn = entitypatch.getOriginal().getLevel();
        Vec3 handPos = getPosByTick(entitypatch,0.4f,"Tool_L");

        float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
        Vec3 shootVec = new Vec3(Math.cos(ang), -1.2, Math.sin(ang));
        Vec3 shootPos = handPos.add(shootVec.x,0,shootVec.z);

        YoimiyaSAArrow projectile = new YoimiyaSAArrow(worldIn, shootPos, entitypatch.getOriginal());
        projectile.shoot(shootVec.x*2, shootVec.y*2, shootVec.z*2, 4.2f, 1.0f);

        float dmg = (float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE);
        projectile.setDmg(dmg);
        projectile.setExpRadio(5.5F);
        worldIn.addFreshEntity(projectile);
    }

    public static Vec3 getPosByTick(LivingEntityPatch entitypatch, float partialTicks, String joint){
        Animator animator = entitypatch.getAnimator();
        Armature armature = entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        Pose pose = animator.getPose(partialTicks);
        Vec3 pos = entitypatch.getOriginal().getPosition(partialTicks);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entitypatch.getModelMatrix(partialTicks)));
        OpenMatrix4f JointTf = Animator.getBindedJointTransformByName(pose, armature, joint).mulFront(modelTf);
        return OpenMatrix4f.transform(JointTf,Vec3.ZERO);
    }
}
