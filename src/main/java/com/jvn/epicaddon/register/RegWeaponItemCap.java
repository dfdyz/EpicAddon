package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.cap.GenShinBowCap;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.resources.EpicAddonSkillSlots;
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
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.RangedWeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

public class RegWeaponItemCap {

    //private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();
    public static final Function<Item, CapabilityItem.Builder> SAO_SINGLE_SWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicAddonWeaponCategories.SINGLE_SWORD)
                .styleProvider((playerpatch) -> {
                    if(playerpatch instanceof PlayerPatch){
                        if(playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == EpicAddonWeaponCategories.SINGLE_SWORD
                                && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill() != null
                                && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")){
                            return EpicAddonStyles.SAO_DUAL_SWORD;
                        }
                        if(((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill() != null
                                && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_rapier_skill")){
                            return EpicAddonStyles.SAO_RAPIER;
                        }
                    }
                    else if (playerpatch instanceof HumanoidMobPatch) {
                        Set<String> tags = playerpatch.getOriginal().getTags();
                        for (String tag : tags) {
                            String[] arg = tag.split(":");
                            if(arg.length > 2 && arg[0] == EpicAddon.MODID){
                                if(arg[1] == "sao_single_sword"){
                                    switch (arg[3]){
                                        case "dual_sword":
                                            return EpicAddonStyles.SAO_DUAL_SWORD;
                                        case "rapier":
                                            return EpicAddonStyles.SAO_RAPIER;
                                        default:
                                            return EpicAddonStyles.SAO_SINGLE_SWORD;
                                    }
                                }
                            }
                            return CapabilityItem.Styles.ONE_HAND;
                        }
                        return EpicAddonStyles.SAO_SINGLE_SWORD;
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
                .innateSkill(EpicAddonStyles.SAO_SINGLE_SWORD,(itemstack) ->  RegEpicAddonSkills.SAO_SINGLESWORD_SA)
                .innateSkill(EpicAddonStyles.SAO_DUAL_SWORD,(itemstack) ->  EpicFightSkills.DANCING_EDGE)
                .innateSkill(EpicAddonStyles.SAO_RAPIER,(itemstack) ->  RegEpicAddonSkills.WEAPON_SKILL_RAPIER)
                .livingMotionModifier(EpicAddonStyles.SAO_SINGLE_SWORD, LivingMotions.IDLE, Animations.BIPED_IDLE)
                .livingMotionModifier(EpicAddonStyles.SAO_SINGLE_SWORD, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.IDLE, EpicAddonAnimations.SAO_RAPIER_IDLE)
                .livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.WALK, EpicAddonAnimations.SAO_RAPIER_WALK)
                .livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.RUN, EpicAddonAnimations.SAO_RAPIER_RUN)
                //.livingMotionModifier(EpicAddonStyles.SAO_RAPIER, LivingMotions.KNEEL, EpicAddonAnimations.SAO_RAPIER_IDLE)
                //.livingMotionModifier(EpicAddonStyles.SAO_RAPIER_LOCKED, LivingMotions.BLOCK, EpicAddonAnimations.SAO_SINGLE_SWORD_GUARD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.IDLE, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.WALK, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.CHASE, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.RUN, EpicAddonAnimations.SAO_DUAL_SWORD_RUN)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.JUMP, EpicAddonAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.KNEEL, EpicAddonAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.SNEAK, EpicAddonAnimations.SAO_DUAL_SWORD_HOLD)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.SWIM, EpicAddonAnimations.SAO_DUAL_SWORD_NORMAL)
                .livingMotionModifier(EpicAddonStyles.SAO_DUAL_SWORD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .weaponCombinationPredicator((entitypatch) -> {
                    boolean tag = false;
                    if (entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == EpicAddonWeaponCategories.SINGLE_SWORD){
                        if(entitypatch instanceof PlayerPatch){
                            if (((PlayerPatch)entitypatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill() != null
                                    && ((PlayerPatch)entitypatch).getSkill(EpicAddonSkillSlots.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")){
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
                .passiveSkill(RegEpicAddonSkills.GS_Bow_Internal)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        EpicAddonAnimations.GS_Yoimiya_Auto1,
                        EpicAddonAnimations.GS_Yoimiya_Auto2,
                        EpicAddonAnimations.GS_Yoimiya_Auto3,
                        EpicAddonAnimations.GS_Yoimiya_Auto4,
                        EpicAddonAnimations.GS_Yoimiya_Auto5,
                        EpicAddonAnimations.GS_Yoimiya_Auto2, EpicAddonAnimations.GS_Yoimiya_FallAtk_Start)
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemStack) -> RegEpicAddonSkills.GS_YOIMIYA_SPECIALATK)
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

    public static final Function<Item, CapabilityItem.Builder> SR_BaseBallBat = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.SWORD)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.ONE_HAND)
                .collider(ColliderPreset.SWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        EpicAddonAnimations.SR_BBB_Auto1,
                        EpicAddonAnimations.SR_BBB_Auto2,
                        EpicAddonAnimations.SR_BBB_Auto2,
                        Animations.SWORD_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemStack) ->  EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, EpicAddonAnimations.SR_BBB_IDLE)
                .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> SAO_SCYTHE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicAddonWeaponCategories.SCYTHE)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.LONGSWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO1,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO2,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO3,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO4,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO5,
                        EpicAddonAnimations.SAO_SCYTHE_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
                .innateSkill(CapabilityItem.Styles.TWO_HAND,(itemStack) -> EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, EpicAddonAnimations.SAO_SCYTHE_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, EpicAddonAnimations.SAO_SCYTHE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, EpicAddonAnimations.SAO_SCYTHE_RUN)
                .weaponCombinationPredicator((entitypatch) -> false);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> SAO_GREATSWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicAddonWeaponCategories.GREAT_SWORD)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.LONGSWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO1,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO2,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO3,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO4,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO5,
                        EpicAddonAnimations.SAO_SCYTHE_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemStack) -> EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, EpicAddonAnimations.SAO_SCYTHE_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, EpicAddonAnimations.SAO_SCYTHE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, EpicAddonAnimations.SAO_SCYTHE_RUN)
                .weaponCombinationPredicator((entitypatch) -> false);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> SAO_PALADIN = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicAddonWeaponCategories.SAO_PALADIN)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.LONGSWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO1,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO2,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO3,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO4,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO5,
                        EpicAddonAnimations.SAO_SCYTHE_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemStack) -> EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, EpicAddonAnimations.SAO_SCYTHE_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, EpicAddonAnimations.SAO_SCYTHE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, EpicAddonAnimations.SAO_SCYTHE_RUN)
                .weaponCombinationPredicator((entitypatch) -> false);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> ES_WIND_SNEAKER = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(EpicAddonWeaponCategories.ES_WIND_SNEAKER)
                .styleProvider((playerpatch) -> CapabilityItem.Styles.TWO_HAND)
                .collider(ColliderPreset.LONGSWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO1,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO2,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO3,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO4,
                        EpicAddonAnimations.SAO_SCYTHE_AUTO5,
                        EpicAddonAnimations.SAO_SCYTHE_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemStack) -> EpicFightSkills.SWEEPING_EDGE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, EpicAddonAnimations.SAO_SCYTHE_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, EpicAddonAnimations.SAO_SCYTHE_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, EpicAddonAnimations.SAO_SCYTHE_RUN)
                .weaponCombinationPredicator((entitypatch) -> false);
        return builder;
    };


    public enum EpicAddonWeaponCategories implements WeaponCategory {
        SCYTHE,GREAT_SWORD,ES_WIND_SNEAKER,SAO_PALADIN,SINGLE_SWORD;
        final int id;

        private EpicAddonWeaponCategories() {
            this.id = WeaponCategory.ENUM_MANAGER.assign(this);
        }

        public int universalOrdinal() {
            return this.id;
        }
    }



    //@SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Loading WeaponCapability");
        event.getTypeEntry().put("sao_single_sword", SAO_SINGLE_SWORD);
        event.getTypeEntry().put("destiny", DESTINY);
        event.getTypeEntry().put("genshin_bow", GenShin_Bow);
        event.getTypeEntry().put("sr_baseball_bat", SR_BaseBallBat);
        event.getTypeEntry().put("sao_scythe", SAO_SCYTHE);

        event.getTypeEntry().put("sao_greatsword", SAO_GREATSWORD);
        event.getTypeEntry().put("es_wind_sneaker", ES_WIND_SNEAKER);
        event.getTypeEntry().put("sao_paladin", SAO_PALADIN);



        LOGGER.info("WeaponCapability Loaded");
    }

}
