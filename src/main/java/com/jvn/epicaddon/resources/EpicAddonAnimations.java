package com.jvn.epicaddon.resources;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.anim.*;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.events.CameraEvent;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.register.WeaponCollider;
import com.jvn.epicaddon.renderer.SwordTrail.IAnimSTOverride;
import com.jvn.epicaddon.skills.GenShin.YoimiyaSkillFunction;
import com.jvn.epicaddon.skills.SAO.SAOSkillAnimUtils;
import com.jvn.epicaddon.utils.Trail;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.List;

public class EpicAddonAnimations {
    public static List<CamAnim> CamAnimRegistry = Lists.newArrayList();
    //public static StaticAnimation Test;
    public static StaticAnimation SAO_SINGLE_SWORD_AUTO1;
    public static StaticAnimation SAO_DUAL_SWORD_HOLD;
    public static StaticAnimation SAO_DUAL_SWORD_NORMAL;
    public static StaticAnimation SAO_DUAL_SWORD_RUN;
    public static StaticAnimation SAO_DUAL_SWORD_WALK;
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
    /*
    public static StaticAnimation SAO_DUAL_SWORD_AUTO13;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO14;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO15;
    public static StaticAnimation SAO_DUAL_SWORD_AUTO16;

     */
    public static StaticAnimation SAO_SINGLE_SWORD_GUARD;
    public static StaticAnimation SAO_DOUBLE_CHOPPER;

    public static StaticAnimation SAO_RAPIER_IDLE;
    public static StaticAnimation SAO_RAPIER_WALK;
    public static StaticAnimation SAO_RAPIER_RUN;
    public static StaticAnimation SAO_RAPIER_AUTO1;
    public static StaticAnimation SAO_RAPIER_AUTO2;
    public static StaticAnimation SAO_RAPIER_AUTO3;
    public static StaticAnimation SAO_RAPIER_AUTO4;
    public static StaticAnimation SAO_RAPIER_AUTO5;
    public static StaticAnimation SAO_RAPIER_AIR;
    public static StaticAnimation SAO_RAPIER_SPECIAL_DASH;
    public static StaticAnimation SAO_RAPIER_SA2;


    public static StaticAnimation SAO_SCYTHE_IDLE;
    public static StaticAnimation SAO_SCYTHE_RUN;
    public static StaticAnimation SAO_SCYTHE_WALK;
    public static StaticAnimation SAO_SCYTHE_AUTO1;
    public static StaticAnimation SAO_SCYTHE_AUTO2;
    public static StaticAnimation SAO_SCYTHE_AUTO3;
    public static StaticAnimation SAO_SCYTHE_AUTO4;
    public static StaticAnimation SAO_SCYTHE_AUTO5;

    public static StaticAnimation SAO_SCYTHE_DASH;
    public static StaticAnimation SAO_SCYTHE_DODGE_ATK;
    public static StaticAnimation SAO_SHIELD_AUTO;

    public static StaticAnimation DMC5_V_JC;
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

    public static CamAnim DMC_V_PREV;

    public static void registerAnimations(AnimationRegistryEvent event) {
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoadingEvent");
        event.getRegistryMap().put(EpicAddon.MODID, EpicAddonAnimations::Reg);
    }

    private static void Reg() {
        HumanoidArmature biped = Armatures.BIPED;
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoading");
        //Test = new BasicAttackAnimation(0.2F, 0.4F, 0.6F, 0.8F, null, biped.toolR, "biped/test_anim", biped);

        //Scythe

        SAO_SCYTHE_IDLE = new StaticAnimation(true, "biped/sao_scythe/living/sao_scythe_idle", biped);
        SAO_SCYTHE_RUN = new MovementAnimation(true, "biped/sao_scythe/living/sao_scythe_run", biped);
        SAO_SCYTHE_WALK = new MovementAnimation(true, "biped/sao_scythe/living/sao_scythe_walk", biped);

        SAO_SCYTHE_AUTO1 = new BasicAttackAnimationEx(0.08F, 0.2F, 0.4F, 0.5F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/sao_scythe/sao_scythe_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                        newTF(0f,1.85f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_SCYTHE_AUTO2 = new BasicAttackAnimationEx(0.08F, 0.2F, 0.3F, 0.4F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/sao_scythe/sao_scythe_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.45f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_SCYTHE_AUTO3 = new BasicAttackAnimationEx(0.04F, 0.3F, 0.4F, 0.55F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/sao_scythe/sao_scythe_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.55f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_SCYTHE_AUTO4 = new MultiPhaseBasicAttackAnimation(0.06F,  "biped/sao_scythe/sao_scythe_auto4", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.55F, 0.6F, 0.6F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.6F, 0.9F, 1F, 1.1F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,2.28f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_SCYTHE_AUTO5 = new BasicAttackAnimationEx(0.06F, 0.25F, 0.4F, 0.8F, WeaponCollider.SAO_SWORD, biped.toolR, "biped/sao_scythe/sao_scythe_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.65f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_SCYTHE_DASH = new MultiPhaseBasicAttackAnimation(0.1F,  "biped/sao_scythe/sao_scythe_dash", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD_HUGE_R)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.2F, 0.3F, 0.35F, 0.4F, 0.4F, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD_HUGE_R)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT),
                new AttackAnimation.Phase(0.4F, 0.5F, 0.6F, 0.85F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, WeaponCollider.SAO_SWORD_HUGE_R)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.BLACK_KNIGHT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.17f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );


        //DUAL SWORD
        SAO_DUAL_SWORD_HOLD = new StaticAnimation(true, "biped/living/sao_dual_sword_hold", biped);
        SAO_DUAL_SWORD_NORMAL = new StaticAnimation(true, "biped/living/sao_dual_sword_hold_normal", biped);
        SAO_DUAL_SWORD_RUN = new MovementAnimation(true, "biped/living/sao_dual_sword_hold_run", biped);
        //SAO_DUAL_SWORD_WALK = new MovementAnimation(true, "biped/living/sao_dual_sword_walk", biped);

        SAO_DUAL_SWORD_AUTO1 = new BasicAttackAnimation(0.05F, 0.2F, 0.3F, 0.5F, null, biped.toolR, "biped/sao_dual_sword/sao_dual_sword_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);


        SAO_DUAL_SWORD_AUTO2 = new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.2F, InteractionHand.OFF_HAND ,null, biped.toolL, "biped/sao_dual_sword/sao_dual_sword_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO3 = new BasicAttackAnimation(0.05F, 0.01F, 0.2F, 0.2F, InteractionHand.OFF_HAND ,null, biped.toolL, "biped/sao_dual_sword/sao_dual_sword_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO4 = new MultiPhaseBasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto4", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.15F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.3F, 0.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
        //auto4:右手，antic:0.05F,contact:0.15F
        //auto4:左手，antic:0.15F,contact:0.3F

        SAO_DUAL_SWORD_AUTO5 = new MultiPhaseBasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto5", biped,
                new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.2F, 0.2F, 0.3F, 0.4F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
        //auto5:右手，antic:0.15F,contact:0.2F
        //auto5:左手，antic:0.2F,contact:0.3F
        SAO_DUAL_SWORD_AUTO6 = new MultiPhaseBasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto6", biped,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.1F, 0.2F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
        //auto6:右手，antic:0.1F,contact:0.2F
        //auto6:左手，antic:0.1F,contact:0.2F
        SAO_DUAL_SWORD_AUTO7 = new MultiPhaseBasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto7", biped,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.31F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.15F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
        //auto7:右手，antic:0.01F,contact:0.1F
        //autO7:左手，antic:0.01F,contact:0.1F
        SAO_DUAL_SWORD_AUTO8 = new MultiPhaseBasicAttackAnimation(0.05F, "biped/sao_dual_sword/sao_dual_sword_auto8", biped,
                new AttackAnimation.Phase(0.0F, 0.05F, 0.1F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.05F, 0.1F, 0.15F, 0.15F, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.2F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.15F, 0.15F, 0.2F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.85F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.4F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.9F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
        //auto8:右手，antic:0.05F,contact:0.1F\antic:0.15F,contact:0.2F(单手两段攻击)
        //autO8:左手，antic:0.05F,contact:0.1F\antic:0.15F,contact:0.2F(单手两段攻击)
        SAO_DUAL_SWORD_AUTO9 = new MultiPhaseBasicAttackAnimation(0.05F,  "biped/sao_dual_sword/sao_dual_sword_auto9", biped,
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.0F, 0.01F, 0.1F, 0.2F,  Float.MAX_VALUE, InteractionHand.MAIN_HAND, biped.toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.9F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
        //auto9:右手，antic:0.01F,contact:0.1F
        //autO9:左手，antic:0.01F,contact:0.1F
        SAO_DUAL_SWORD_AUTO10 = new BasicAttackAnimation(0.05F, 0.01F, 0.1F, 0.2F, WeaponCollider.SAO_SWORD_DUAL_AUTO10, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_auto10", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.45F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO11 = new BasicAttackAnimation(0.05F, 0.01F,0.1F, 0.3F, WeaponCollider.SAO_SWORD_AIR, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_auto11", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.08F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO12 = new BasicAttackAnimation(0.05F, 0.01F, 0.1F, 0.5F, WeaponCollider.SAO_SWORD_AIR, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_auto12", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
//1~12，13~16的连击没做。
        /*
        SAO_DUAL_SWORD_AUTO13 = new BasicAttackAnimation(0.16F, 0.002F, 0.35F, 0.28F, null, biped.toolR, "biped/sao_dual_sword/sao_dual_sword_auto13", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO14 = new BasicAttackAnimation(0.02F, 0.001F, 0.2F, 0.19F, null, biped.toolR, "biped/sao_dual_sword/sao_dual_sword_auto14", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO15 = new BasicAttackAnimation(0.3F, 0.26F, 0.41F, 0.23F, null, biped.toolR, "biped/sao_dual_sword/sao_dual_sword_auto15", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);

        SAO_DUAL_SWORD_AUTO16 = new BasicAttackAnimation(0.265F, 0.16F, 0.58F, 0.35F, InteractionHand.OFF_HAND ,null, biped.toolL, "biped/sao_dual_sword/sao_dual_sword_auto16", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.6F);
*/
        SAO_SINGLE_SWORD_GUARD = new StaticAnimation(0.25F, true, "biped/skill/sao_single_sword_guard", biped);

        SAO_DOUBLE_CHOPPER = new SpecailDashAtkAnimation(0.06F, 0.0F, 0.02F, 0.254F, 0.25F, WeaponCollider.SAO_SWORD_DASH, biped.rootJoint, "biped/sao_dual_sword/sao_dual_sword_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(14.7F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
               
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.0F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.0F, (entitypatch, animation, obj) -> {
                            if(entitypatch instanceof PlayerPatch){
                                PlayerPatch pp = (PlayerPatch)entitypatch;
                                float tmp = pp.getStamina() - pp.getMaxStamina()*(0.5f);
                                tmp = (tmp>=0) ? tmp : 0;
                                pp.setStamina(tmp);
                                //LivingEntity entity = (LivingEntity)pp.getOriginal();
                                //.setNoGravity(true);
                                //pp.getAnimator().playAnimation(EpicAddonAnimations.SAO_DUAL_SWORD_HOLD,0.0f);
                            }
                        }, AnimationEvent.Side.SERVER)
                });

        //RAPIER
        SAO_RAPIER_IDLE = new StaticAnimation(true, "biped/living/sao_rapier_idle", biped);
        SAO_RAPIER_WALK = new MovementAnimation(true, "biped/living/sao_rapier_walk", biped);
        SAO_RAPIER_RUN = new MovementAnimation(true, "biped/living/sao_rapier_run", biped);

        SAO_RAPIER_AUTO1 = new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, biped.toolR, "biped/sao_rapier_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.1f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_RAPIER_AUTO2 = new BasicAttackAnimation(0.05F, 0.1F, 0.2F, 0.3F, null, biped.toolR, "biped/sao_rapier_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.3f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_RAPIER_AUTO3 = new BasicAttackAnimation(0.02F, 0.1F, 0.2F, 0.4F, null, biped.toolR, "biped/sao_rapier_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.2f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_RAPIER_AUTO4 = new MultiPhaseBasicAttackAnimation(0.05F,"biped/sao_rapier_auto4", biped,
                new AttackAnimation.Phase(0.0F,0.1F,0.15F,0.2F,0.2F,InteractionHand.MAIN_HAND,biped.toolR,null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT),
                new AttackAnimation.Phase(0.2F,0.25F,0.35F,0.5F,Float.MAX_VALUE,InteractionHand.MAIN_HAND,biped.toolR,null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.8F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT))
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.1f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_RAPIER_AUTO5 = new BasicAttackAnimation(0.02F, 0.2F, 0.3F, 0.65F, null, biped.toolR, "biped/sao_rapier_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(ClientAnimationProperties.TRAIL_EFFECT, newTFL(
                                newTF(0f,1.3f, biped.toolR, InteractionHand.MAIN_HAND)
                        )
                );

        SAO_RAPIER_DASH  = new DashAttackAnimation(0F, 0.2F, 0.1F, 0.3F, 0.4F, WeaponCollider.SAO_RAPIER_DASH_SHORT, biped.rootJoint, "biped/sao_rapier_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(14.7F))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_RAPIER_AIR  = new DashAttackAnimation(0.12F, 0.133F, 0.05F, 0.2F, 0.3F, WeaponCollider.SAO_RAPIER_DASH_SHORT, biped.rootJoint, "biped/sao_rapier_air", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6f)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
               
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT);

        SAO_RAPIER_SPECIAL_DASH  = new SpecailDashAtkAnimation(0.1F, 0.3F, 0.05F, 4.8333F, 4.1F, WeaponCollider.SAO_RAPIER_DASH, biped.rootJoint, "biped/sao_rapier_dash_long", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.adder(14.7F))
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(114514))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.21F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0, (ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(114514.0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(3.5f, (ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER)
                });

        SAO_RAPIER_SA2  = new ScanAttackAnimation(0.0F, 0.3f,0.72f, 1.48F, InteractionHand.MAIN_HAND, false,1000, WeaponCollider.SAO_RAPIER_SCAN, biped.rootJoint, "biped/sao_rapier_sa2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.prev(ep);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.TimeStampedEvent.create(0.65f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.HandleAtk(ep);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.15f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.RapierSA.post(ep);
                        }, AnimationEvent.Side.CLIENT)
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(1.2f));


        DMC5_V_JC  = new ScanAttackAnimationEx(0.02F, 0.334f, 0.43f, 4.48F, InteractionHand.MAIN_HAND, 1000, WeaponCollider.DMC_JC, biped.rootJoint, "biped/dmc5_v_jc", biped)
                .SetInvisiblePhase(0.85f,2.19f).SetMovablePhase(5.35f, Float.MAX_VALUE)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.DMC5_V_JC.prev(ep);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(0.4f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.DMC5_V_JC.HandleAtk(ep);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(0.85f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.DMC5_V_JC.post1(ep);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(4.48f, (ep, anim, objs) -> {
                            SAOSkillAnimUtils.DMC5_V_JC.post(ep);
                        }, AnimationEvent.Side.BOTH)
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2));




        DESTINY_AIM = new AimAnimation(
                false, "biped/destiny_aim_mid", "biped/destiny_aim_up", "biped/destiny_aim_down", "biped/destiny_aim_lying", biped);
        DESTINY_SHOT = new ReboundAnimation(false, "biped/destiny_shoot_mid", "biped/destiny_shoot_up", "biped/destiny_shoot_down", "biped/destiny_shoot_lying", biped);
        DESTINY_RELOAD = new StaticAnimation(false, "biped/destiny_reload", biped);

        SAO_SINGLE_SWORD_AUTO1 = new BasicAttackAnimation(0.12F, 0.25F, 0.625F, 1F, null, biped.toolR, "biped/single_blade_1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(4.5f));

        GS_Yoimiya_Auto1 = new ScanAttackAnimation(0.1F, 0,0.62F, 0.8333F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto1", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.4F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                           }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(0.585F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.75f));

        GS_Yoimiya_Auto2 = new ScanAttackAnimation(0.1F, 0,0.7F, 0.98F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto2", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.6F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolR);
                        }, AnimationEvent.Side.BOTH),
                });

        GS_Yoimiya_Auto3 = new ScanAttackAnimation(0.1F, 0,0.88F, 1.03F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto3", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.95f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.84F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                });

        GS_Yoimiya_Auto4 = new ScanAttackAnimation(0.05F, 0,2.12F, 2.733F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto4", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(1.2083F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(1.7916F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolR);
                        }, AnimationEvent.Side.BOTH),
                        AnimationEvent.TimeStampedEvent.create(2.0416F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep,biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3.1f));

        GS_Yoimiya_Auto5 = new ScanAttackAnimation(0.02F, 0,0.2F, 1.51F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_auto5", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER,MSpeed( 3.1f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.7083F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.BowShoot(ep, biped.toolL);
                        }, AnimationEvent.Side.BOTH),
                });

        GS_Yoimiya_SA = new YoimiyaSAAnimation(0.02F, 0.5F, 4.56F, InteractionHand.MAIN_HAND, WeaponCollider.GenShin_Bow_scan,biped.rootJoint, "biped/gs_yoimiya_sa", biped)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3f))
                .addProperty(AnimationProperty.StaticAnimationProperty.TIME_STAMPED_EVENTS, new AnimationEvent.TimeStampedEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            CameraEvent.SetAnim(Yoimiya, ep.getOriginal(), true);
                        }, AnimationEvent.Side.CLIENT),
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.YoimiyaSAFirework(ep);
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(2.375F, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.YoimiyaSA(ep);
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(0f, (ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(114514.0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER),
                        AnimationEvent.TimeStampedEvent.create(4f, (ep, anim, objs) -> {
                            if(ep instanceof PlayerPatch){
                                ep.setMaxStunShield(0f);
                                ep.setStunShield(ep.getMaxStunShield());
                            }
                        }, AnimationEvent.Side.SERVER)
                });

        //GS_BowFallAtk_Loop = new FallAtkAnimLoop(0.1f,true,"biped/fall_attack_test_l",biped,Animations.SWORD_AIR_SLASH);
        GS_Yoimiya_FallAtk_Last = new FallAtkFinalAnim(0.05F, 0.5F, 0.8F, 2.1F, WeaponCollider.GenShin_Bow_FallAttack, biped.rootJoint, "biped/gs_yoimiya_fall_atk_last", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicAddonSounds.GENSHIN_BOW_FALLATK)
                .addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, EpicAddonSounds.GENSHIN_BOW_FALLATK)
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(114514))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1))
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(7f))
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new AnimationEvent[]{
                        AnimationEvent.TimeStampedEvent.create(0.45f, (ep, anim, objs) -> {
                            YoimiyaSkillFunction.SendParticle(
                                    ep.getOriginal().getLevel(),
                                    RegParticle.GENSHIN_BOW_LANDING.get(),
                                    ep.getOriginal().position()
                            );
                        }, AnimationEvent.Side.SERVER)
                });

        GS_Yoimiya_FallAtk_Loop = new FallAtkLoopAnim(0.1f,"biped/gs_yoimiya_fall_atk_loop", biped, GS_Yoimiya_FallAtk_Last);

        GS_Yoimiya_FallAtk_Start = new FallAtkStartAnim(0.1f,"biped/gs_yoimiya_fall_atk_start", biped, GS_Yoimiya_FallAtk_Loop)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(3.6f));

        //GS_BowFallAtk_Test = new FallAtkAnim(GS_BowFallAtk_Test1, GS_BowFallAtk_Test2, Animations.SWORD_AIR_SLASH);

        SR_BBB_IDLE = new  StaticAnimation(true, "biped/living/sr_bbb_idle", biped);
        SR_BBB_Auto1 = new BasicAttackAnimation(0.12F, 0.35F, 0.73F, 0.9F, WeaponCollider.SR_BBb_Normal, biped.toolR, "biped/sr_bbb_combo1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.2f));

        SR_BBB_Auto2 = new BasicAttackAnimation(0.08F, 0.33F, 0.73F, 0.85F, WeaponCollider.SR_BBb_Normal, biped.toolR, "biped/sr_bbb_combo2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(2.2f));

        SR_BBB_SA_CG = new BasicAttackAnimation(0.12F, 1.6666F, 2.43F, 2.6F, WeaponCollider.SR_BBb_Normal, biped.toolR, "biped/sr_bbb_sa_cg", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.SHORT)
                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, RegParticle.SPARKS_SPLASH_HIT)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS,new AnimationEvent[] {
                        AnimationEvent.TimeStampedEvent.create(0.7083F, (ep, anim, objs) -> {

                        }, AnimationEvent.Side.CLIENT),
                })
                .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, MSpeed(1.8f));

        /*
        ((IAnimSTOverride) GS_Yoimiya_Auto1).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_Auto2).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_Auto3).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_Auto4).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_Auto5).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_SA).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_FallAtk_Last).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_FallAtk_Start).EnableST(false);
        ((IAnimSTOverride) GS_Yoimiya_FallAtk_Loop).EnableST(false);

        if(FMLEnvironment.dist == Dist.CLIENT){
            ((IAnimSTOverride)SAO_RAPIER_SPECIAL_DASH).setLifeTimeOverride(10).setPosOverride(
                    new Trail(0f,0.2f,-0.3f,0f,-0.2f,-0.3f,0,0,0,0));
            ((IAnimSTOverride)Animations.SWORD_AUTO1).setColorOverride(new Trail(0,0,-0.2f,0,-0.2f,-1.6f,255,30,30,120));
        }*/

        LOGGER.info("EpicAddon AnimLoaded");
    }

    public static AnimationProperty.PlaySpeedModifier MSpeed(float t){
        return (a,b,c,d) -> t;
    }
    public static List<TrailInfo> newTFL(TrailInfo... tfs){
        return Lists.newArrayList(tfs);
    }

    public static TrailInfo newTF(float start, float end, Joint joint, InteractionHand hand){
        JsonObject je = new JsonObject();
        je.addProperty("joint", joint.getName());
        je.addProperty("startTime", start);
        je.addProperty("endTime", end);
        je.addProperty("item_skin_hand", hand.toString());

        //System.out.println(je);

        return TrailInfo.deserialize(je);
    }

    public static void RegCamAnims(){
        Yoimiya = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/yoimiya.json"));
        SAO_RAPIER_SA2_CAM = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/sao_rapier_sa2.json"));
        SAO_RAPIER_SA2_CAM2 = regCamAnim(new CamAnim(0.3f ,EpicAddon.MODID, "camanim/sao_rapier_sa2_post.json"));
        DMC_V_PREV = regCamAnim(new CamAnim(0.4f, EpicAddon.MODID, "camanim/dmc_v_prev.json"));
    }

    public static CamAnim regCamAnim(CamAnim anim){
        CamAnimRegistry.add(anim);
        return anim;
    }
}
