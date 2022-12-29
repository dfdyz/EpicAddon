package com.jvn.epicaddon.resources;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.register.WeaponCollider;
import com.jvn.epicaddon.renderer.SwordTrail.IAnimST;
import com.jvn.epicaddon.tools.Trail;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class EpicAddonAnimations {
    public static StaticAnimation Test;
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

    public static void registerAnimations(AnimationRegistryEvent event) {
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoadingEvent");
        event.getRegistryMap().put(EpicAddon.MODID, EpicAddonAnimations::Reg);
    }

    public static void Reg() {
        Models<?> models = FMLEnvironment.dist == Dist.CLIENT ? ClientModels.LOGICAL_CLIENT : Models.LOGICAL_SERVER;
        Model biped = models.biped;
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon AnimLoading");
        Test = new BasicAttackAnimation(0.2F, 0.4F, 0.6F, 0.8F, null, "Tool_R", "biped/test_anim", biped);

        //DUAL SWORD
        SAO_DUAL_SWORD_HOLD = new StaticAnimation(true, "biped/living/sao_dual_sword_hold", biped);
        SAO_DUAL_SWORD_NORMAL = new StaticAnimation(true, "biped/living/sao_dual_sword_hold_normal", biped);
        SAO_DUAL_SWORD_RUN = new StaticAnimation(true, "biped/living/sao_dual_sword_hold_run", biped);

        SAO_DUAL_SWORD_AUTO1 = new BasicAttackAnimation(0.16F, 0.05F, 0.6F, 0.65F, null, "Tool_R", "biped/sao_dual_sword_auto1", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        SAO_DUAL_SWORD_AUTO2 = new BasicAttackAnimation(0.2F, 0.002F, 0.6F, 0.28F, InteractionHand.OFF_HAND ,null, "Tool_L", "biped/sao_dual_sword_auto2", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        SAO_DUAL_SWORD_AUTO3 = new BasicAttackAnimation(0.13F, 0.05F, 0.45F, 0.28F, InteractionHand.OFF_HAND ,null, "Tool_L", "biped/sao_dual_sword_auto3", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        SAO_DUAL_SWORD_AUTO4 = new BasicAttackAnimation(0.13F, 0.05F, 0.5F, 0.4F, null, "Tool_R", "biped/sao_dual_sword_auto4", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        SAO_DUAL_SWORD_AUTO5 = new BasicAttackAnimation(0.02F, 0.02F, 1.45F, 1.4F,InteractionHand.OFF_HAND , null, "Tool_L", "biped/sao_dual_sword_auto5", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        SAO_DUAL_SWORD_AUTO6 = new BasicAttackAnimation(0.12F, 0.0F, 0.62F, 0.615F, null, "Tool_R", "biped/sao_dual_sword_auto6", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);
        SAO_DUAL_SWORD_AUTO7 = new BasicAttackAnimation(0.11F, 0.85F, 2.5F, 1.7F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto7", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.31F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.15F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG);
        SAO_DUAL_SWORD_AUTO8 = new BasicAttackAnimation(0.05F, 0.4F, 1.8F, 1.2F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto8", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.4F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.9F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG);
        SAO_DUAL_SWORD_AUTO9 = new BasicAttackAnimation(0.113F, 0.20F, 1.3F, 0.9F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto9", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG);

        SAO_DUAL_SWORD_AUTO10 = new BasicAttackAnimation(0.115F, 0.05F, 1.125F, 0.965F, WeaponCollider.SAO_SWORD_DUAL_AUTO10, "Root", "biped/sao_dual_sword_auto10", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.45F)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.05F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG);

        SAO_DUAL_SWORD_AUTO11 = new BasicAttackAnimation(0.0F, 0.02F,0.41F, 0.3F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto11", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.08F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);

        SAO_DUAL_SWORD_AUTO12 = new BasicAttackAnimation(0.09F, 0.02F, 0.45F, 0.3F, WeaponCollider.SAO_SWORD_AIR, "Root", "biped/sao_dual_sword_auto12", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.1F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.SHORT);

        SAO_DUAL_SWORD_AUTO13 = new BasicAttackAnimation(0.16F, 0.002F, 0.35F, 0.28F, null, "Tool_R", "biped/sao_dual_sword_auto13", biped);
        SAO_DUAL_SWORD_AUTO14 = new BasicAttackAnimation(0.02F, 0.001F, 0.2F, 0.19F, null, "Tool_R", "biped/sao_dual_sword_auto14", biped);
        SAO_DUAL_SWORD_AUTO15 = new BasicAttackAnimation(0.3F, 0.26F, 0.41F, 0.23F, null, "Tool_R", "biped/sao_dual_sword_auto15", biped);
        SAO_DUAL_SWORD_AUTO16 = new BasicAttackAnimation(0.265F, 0.16F, 0.58F, 0.35F, InteractionHand.OFF_HAND ,null, "Tool_L", "biped/sao_dual_sword_auto16", biped);

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
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);
        SAO_RAPIER_AUTO2 = new BasicAttackAnimation(0.12F, 0.033F, 0.133F, 0.2F, null, "Tool_R", "biped/sao_rapier_auto2", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);
        SAO_RAPIER_AUTO3 = new BasicAttackAnimation(0.12F, 0.2F, 0.3F, 0.3F, null, "Tool_R", "biped/sao_rapier_auto3", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);
        SAO_RAPIER_AUTO4 = new BasicAttackAnimation(0.12F, 0.033F, 0.2F, 0.2F, null, "Tool_R", "biped/sao_rapier_auto4", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);
        SAO_RAPIER_AUTO5 = new BasicAttackAnimation(0.12F, 0.2F, 0.3F, 0.4F, null, "Tool_R", "biped/sao_rapier_auto5", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F);

        SAO_RAPIER_DASH  = new DashAttackAnimation(0.12F, 0.2F, 0.1F, 0.3F, 0.4F, WeaponCollider.SAO_RAPIER_DASH_SHORT, "Root", "biped/sao_rapier_dash", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(14.7F))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);

        SAO_RAPIER_AIR  = new DashAttackAnimation(0.12F, 0.133F, 0.05F, 0.2F, 0.3F, WeaponCollider.SAO_RAPIER_DASH_SHORT, "Root", "biped/sao_rapier_air", biped)
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(1.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true);


        SAO_RAPIER_SPECIAL_DASH  = new DashAttackAnimation(0.1F, 0.3F, 0.05F, 4.8333F, 4.1F, WeaponCollider.SAO_RAPIER_DASH, "Root", "biped/sao_rapier_dash_long", biped)
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.adder(14.7F))
                .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.21F)
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(30.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.KNOCKDOWN)
                .addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, false)
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.5F))
                .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE,true)
                .addProperty(AnimationProperty.StaticAnimationProperty.EVENTS, new StaticAnimation.Event[] {
                        StaticAnimation.Event.create(0.0F, (ep) -> {
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

        //Client Only
        if(FMLEnvironment.dist == Dist.CLIENT){
            ((IAnimST)(Animations.SWORD_AUTO1)).SetSpecial(true).SetTrail(new Trail(0,0,-0.2f,0,-0.2f,-1.6f,255,30,30,120));
        }
        LOGGER.info("EpicAddon AnimLoaded");
    }



}
