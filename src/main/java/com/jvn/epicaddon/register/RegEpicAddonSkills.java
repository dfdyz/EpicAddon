package com.jvn.epicaddon.register;


import com.google.common.collect.Maps;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.skills.GenShinInternal.GSBasicAtkPatch;
import com.jvn.epicaddon.skills.GenShinInternal.GSBowInternal;
import com.jvn.epicaddon.skills.GenShinInternal.GSFallAttack;
import com.jvn.epicaddon.skills.SAO.DualBladeSkill;
import com.jvn.epicaddon.skills.SAOInternal.SAOSingleSwordInternal;
import com.jvn.epicaddon.skills.SAOInternal.SAOBasicAtkPatch;
import com.jvn.epicaddon.skills.SAO.RapierSkill;
import com.jvn.epicaddon.skills.SAO.SingleSwordGuardSkill;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SimpleSpecialAttackSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;

import java.util.Collection;
import java.util.Map;

public class RegEpicAddonSkills {

    public static Skill WEAPON_SKILL_RAPIER;
    public static Skill SAO_DUALSWORD;

    public static Skill SAO_SINGLESWORD_INTERNAL;

    public static Skill SAO_SINGLESWORDGUARD;
    public static Skill SAO_RAPIER;
    public static Skill GS_YOIMIYA_SPECIALATK;

    public static Skill SAOBasicAtkPatch;
    public static Skill GS_Bow_FallAttackPatch;
    public static Skill GS_Bow_BasicAttackPatch;
    public static Skill GS_Bow_Internal;

    private static final Map<ResourceLocation, Skill> LEARNABLE_SKILLS = Maps.newHashMap();

    public static void registerSkills(SkillRegistryEvent event) {
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon Skill Loading Event");
        SAO_DUALSWORD = event.registerSkill(new DualBladeSkill(DualBladeSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_dual_sword_skill"))), true);
        SAO_SINGLESWORDGUARD = event.registerSkill(new SingleSwordGuardSkill(SingleSwordGuardSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_single_sword_guard_skill"))),true);
        SAO_RAPIER = event.registerSkill(new RapierSkill(RapierSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_rapier_skill"))),true);

        WEAPON_SKILL_RAPIER = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EpicAddon.MODID, "weapon_skill_rapier")).setConsumption(30.0F).setAnimations(EpicAddonAnimations.SAO_RAPIER_SPECIAL_DASH)),false);

        GS_YOIMIYA_SPECIALATK = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EpicAddon.MODID, "gs_yoimiya_sa")).setConsumption(30.0F).setAnimations(EpicAddonAnimations.GS_Yoimiya_SA))
                .newPropertyLine()
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES, ValueCorrector.adder(10))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE, ValueCorrector.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION, ValueCorrector.adder(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT, ValueCorrector.multiplier(5F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, ExtendedDamageSource.StunType.LONG)
                .registerPropertiesToAnimation(),false);

        SAOBasicAtkPatch = event.registerSkill(new SAOBasicAtkPatch(SAOBasicAtkPatch.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_basic_attack_patch")).setCategory(SkillCategories.BASIC_ATTACK).setConsumption(0.0F).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.NONE)),false);
        SAO_SINGLESWORD_INTERNAL = event.registerSkill(new SAOSingleSwordInternal(SAOSingleSwordInternal.createBuilder(new ResourceLocation(EpicAddon.MODID, "sao_single_sword_internal")).setCategory(SkillCategories.WEAPON_PASSIVE).setConsumption(0.0F).setActivateType(Skill.ActivateType.PASSIVE).setResource(Skill.Resource.NONE)),false);

        GS_Bow_FallAttackPatch = event.registerSkill(new GSFallAttack(GSFallAttack.createBuilder(new ResourceLocation(EpicAddon.MODID,"gs_air_attack_patch")).setCategory(SkillCategories.AIR_ATTACK).setConsumption(2.0F).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.STAMINA)),false);
        GS_Bow_BasicAttackPatch = event.registerSkill(new GSBasicAtkPatch(GSBasicAtkPatch.createBuilder(new ResourceLocation(EpicAddon.MODID,"gs_basic_attack_patch")).setCategory(SkillCategories.BASIC_ATTACK).setConsumption(0.0F).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.NONE)),false);

        GS_Bow_Internal = event.registerSkill(new GSBowInternal(GSBowInternal.GetBuilder("gs_bow_internal").setCategory(SkillCategories.WEAPON_PASSIVE).setCategory(SkillCategories.WEAPON_PASSIVE).setConsumption(0.0F).setActivateType(Skill.ActivateType.PASSIVE).setResource(Skill.Resource.NONE)),false);

        LOGGER.info("EpicAddon Skill Loaded");
    }

    public static Collection<Skill> getLearnableSkills() {
        return LEARNABLE_SKILLS.values();
    }
}

