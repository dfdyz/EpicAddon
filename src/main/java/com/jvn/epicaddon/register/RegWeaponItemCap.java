package com.jvn.epicaddon.register;

import com.jvn.epicaddon.api.cap.GenShinBowCap;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.resources.EpicAddonStyles;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

public class RegWeaponItemCap {

    //private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();
    public static final Function<Item, CapabilityItem.Builder> SAO_SINGLE_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.SWORD)
                .styleProvider((playerpatch) -> {
                    if(playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD
                            && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill() != null
                            && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")){
                        return EpicAddonStyles.SAO_DUAL_SWORD;
                    }
                    if(((PlayerPatch)playerpatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill() != null
                            && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_rapier_skill")){
                            return EpicAddonStyles.SAO_RAPIER;
                    }
                    return EpicAddonStyles.SAO_SINGLE_SWORD;
                })
                .passiveSkill(RegEpicAddonSkills.SAO_SINGLESWORD_INTERNAL)
                .collider(WeaponCollider.SAO_SWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(EpicAddonStyles.SAO_SINGLE_SWORD,
                        EpicAddonAnimations.SAO_SINGLE_SWORD_AUTO1,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO3,
                        Animations.SWORD_DASH, Animations.SWORD_DASH,
                        Animations.SWORD_AIR_SLASH)
                .newStyleCombo(EpicAddonStyles.SAO_RAPIER,
                        EpicAddonAnimations.SAO_RAPIER_AUTO1,
                        EpicAddonAnimations.SAO_RAPIER_AUTO2,
                        EpicAddonAnimations.SAO_RAPIER_AUTO3,
                        EpicAddonAnimations.SAO_RAPIER_AUTO4,
                        EpicAddonAnimations.SAO_RAPIER_AUTO5,
                        EpicAddonAnimations.SAO_RAPIER_DASH, EpicAddonAnimations.SAO_RAPIER_DASH,
                        EpicAddonAnimations.SAO_RAPIER_AIR)
                .newStyleCombo(EpicAddonStyles.SAO_DUAL_SWORD,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO1,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO2,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO3,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO4,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO5,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO6,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO7,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO8,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO9,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO10,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO11,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO12,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO13,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO14,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO15,
                        EpicAddonAnimations.SAO_DUAL_SWORD_AUTO16,
                        EpicAddonAnimations.SAO_DOUBLE_CHOPPER, Animations.SPEAR_DASH,
                        Animations.GREATSWORD_AIR_SLASH)
                .specialAttack(EpicAddonStyles.SAO_SINGLE_SWORD, Skills.SWEEPING_EDGE)
                .specialAttack(EpicAddonStyles.SAO_DUAL_SWORD, Skills.DANCING_EDGE)
                .specialAttack(EpicAddonStyles.SAO_RAPIER, RegEpicAddonSkills.WEAPON_SKILL_RAPIER)
                .livingMotionModifier(EpicAddonStyles.SAO_SINGLE_SWORD, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(EpicAddonStyles.SAO_SINGLE_SWORD, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                //.livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.IDLE, EpicAddonAnimations.SAO_RAPIER_IDLE)
                .livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.KNEEL, EpicAddonAnimations.SAO_RAPIER_IDLE)
                //.livingMotionModifier(EpicAddonStyles.SAO_RAPIER_LOCKED, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.IDLE, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.WALK, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.CHASE, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.RUN, EpicAddonAnimations.SAO_DUAL_SWORD_RUN)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.JUMP, EpicAddonAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.KNEEL, EpicAddonAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.SNEAK, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.SWIM, EpicAddonAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                .weaponCombinationPredicator((entitypatch) -> {
                    boolean tag = false;
                    if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD){
                        if(entitypatch instanceof PlayerPatch){
                            if (((PlayerPatch)entitypatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill() != null
                                    && ((PlayerPatch)entitypatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")){
                                tag = true;
                            }
                        }
                    }
                    return tag;
                });
                if (item instanceof TieredItem) {
                    int harvestLevel = ((TieredItem)item).getTier().getLevel();
                    builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
                    builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
                }
                return builder;
    };


    public static final Function<Item, CapabilityItem.Builder> DESTINY =  (item) -> RangedWeaponCapability.builder()
            .addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.KNEEL, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.SNEAK, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.SWIM, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.FLOAT, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.FALL, Animations.BIPED_HOLD_CROSSBOW)
            .addAnimationsModifier(LivingMotions.RELOAD, EpicAddonAnimations.DESTINY_AIM)
            .addAnimationsModifier(LivingMotions.AIM, EpicAddonAnimations.DESTINY_AIM)
            .addAnimationsModifier(LivingMotions.SHOT, EpicAddonAnimations.DESTINY_SHOT);

    public static final Function<Item, CapabilityItem.Builder> GenShin_Bow = (item) -> {
            WeaponCapability.Builder builder = (WeaponCapability.Builder)WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.RANGED)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.ONE_HAND)
                .collider(WeaponCollider.GenShin_Bow_scan)
                .swingSound(EpicFightSounds.WHOOSH_BIG)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .canBePlacedOffhand(false)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        EpicAddonAnimations.GS_Yoimiya_Auto1,
                        EpicAddonAnimations.GS_Yoimiya_Auto2,
                        EpicAddonAnimations.GS_Yoimiya_Auto3,
                        EpicAddonAnimations.GS_Yoimiya_Auto4,
                        EpicAddonAnimations.GS_Yoimiya_Auto5,
                        EpicAddonAnimations.GS_Yoimiya_Auto2, Animations.GREATSWORD_AIR_SLASH)
                .specialAttack(CapabilityItem.Styles.ONE_HAND, RegEpicAddonSkills.GS_YOIMIYA_SPECIALATK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_JUMP)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_KNEEL)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_SNEAK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_SWIM)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.AIM, Animations.BIPED_BOW_AIM)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.BIPED_BLOCK)
                .constructor(GenShinBowCap::new);

        return builder;
    };

    //@SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Loading WeaponCapability");
        event.getTypeEntry().put("sao_single_sword", SAO_SINGLE_SWORD);
        event.getTypeEntry().put("destiny", DESTINY);
        event.getTypeEntry().put("genshin_bow", GenShin_Bow);
        LOGGER.info("WeaponCapability Loaded");
    }

}
