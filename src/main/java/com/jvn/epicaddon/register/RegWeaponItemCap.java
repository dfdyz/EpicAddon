package com.jvn.epicaddon.register;

import com.jvn.epicaddon.capabilities.LockableWeaponCap;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.resources.EpicAddonStyles;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Function;

public class RegWeaponItemCap {

    //private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();

    public static final Function<Item, CapabilityItem.Builder> SAO_SINGLE_SWORD = (item) -> {
        WeaponCapability.Builder builder = LockableWeaponCap.builder()
                .category(CapabilityItem.WeaponCategories.SWORD)
                .styleProvider((playerpatch) -> {
                    /*
                    if(playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD
                            && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill() != null
                            && ((PlayerPatch)playerpatch).getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill().getRegistryName().getPath().equals("sao_dual_sword_skill")){
                        return (((PlayerPatch)playerpatch).getStamina()/((PlayerPatch)playerpatch).getMaxStamina() >= 0.5F) ? EpicAddonStyles.SAO_DUAL_SWORD : EpicAddonStyles.SAO_DUAL_SWORD_LOCKED;
                    }

                     */

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
                .collider(WeaponCollider.SAO_SWORD)
                .hitSound(EpicFightSounds.BLADE_HIT)
                .newStyleCombo(EpicAddonStyles.SAO_SINGLE_SWORD, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(EpicAddonStyles.SAO_RAPIER,
                        EpicAddonAnimations.SAO_RAPIER_AUTO1,
                        EpicAddonAnimations.SAO_RAPIER_AUTO2,
                        EpicAddonAnimations.SAO_RAPIER_AUTO3,
                        EpicAddonAnimations.SAO_RAPIER_AUTO4,
                        EpicAddonAnimations.SAO_RAPIER_AUTO5,
                        EpicAddonAnimations.SAO_RAPIER_DASH, EpicAddonAnimations.SAO_RAPIER_AIR)
                /*
                .newStyleCombo(EpicAddonStyles.SAO_RAPIER_LOCKED,
                        EpicAddonAnimations.SAO_RAPIER_AUTO1,
                        EpicAddonAnimations.SAO_RAPIER_AUTO2,
                        Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)

                 */
                .ChildStyleProvider(EpicAddonStyles.SAO_DUAL_SWORD ,(entityPatch -> {
                    return !(((PlayerPatch)entityPatch).getStamina()/((PlayerPatch)entityPatch).getMaxStamina() >= 0.5F) ? EpicAddonStyles.SAO_DUAL_SWORD_LOCKED : null;
                }))
                .ChildStyleCombo(EpicAddonStyles.SAO_DUAL_SWORD, EpicAddonStyles.SAO_DUAL_SWORD_LOCKED,
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
                        Animations.SWORD_DASH,
                        Animations.GREATSWORD_AIR_SLASH)
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
                        EpicAddonAnimations.SAO_DOUBLE_CHOPPER,
                        Animations.GREATSWORD_AIR_SLASH)
                /*
                .newStyleCombo(EpicAddonStyles.SAO_DUAL_SWORD_LOCKED,
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
                        Animations.SWORD_DASH,
                        Animations.GREATSWORD_AIR_SLASH)

                 */
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
                    /*
                    else if(entitypatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD){
                        tag = true;
                    }
                    */
                    return tag;
                });
                if (item instanceof TieredItem) {
                    int harvestLevel = ((TieredItem)item).getTier().getLevel();
                    builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
                    builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
                }
                return builder;
    };


    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Loading WeaponCapability");
        event.getTypeEntry().put("sao_single_sword", SAO_SINGLE_SWORD);
        LOGGER.info("WeaponCapability Loaded");
    }
}
