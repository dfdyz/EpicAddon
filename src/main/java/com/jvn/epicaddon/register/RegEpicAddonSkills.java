package com.jvn.epicaddon.register;


import com.google.common.collect.Maps;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonItemGroup;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.skills.GenShinInternal.GSBasicAtkPatch;
import com.jvn.epicaddon.skills.GenShinInternal.GSBowInternal;
import com.jvn.epicaddon.skills.GenShinInternal.GSFallAttack;
import com.jvn.epicaddon.skills.GenShinInternal.GSSpecialAttack;
import com.jvn.epicaddon.skills.SAO.*;
import com.jvn.epicaddon.skills.SAOInternal.MutiSpecialSkill;
import com.jvn.epicaddon.skills.SAOInternal.SAOBasicAtkPatch;
import com.jvn.epicaddon.skills.SAOInternal.SAOSingleSwordInternal;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;

import java.util.Collection;
import java.util.Map;

import static yesman.epicfight.skill.Skill.Resource.WEAPON_INNATE_ENERGY;

public class RegEpicAddonSkills {

    public static Skill WEAPON_SKILL_RAPIER;
    public static Skill SAO_DUALSWORD;
    public static Skill SAO_SINGLESWORD;
    public static Skill SAO_SINGLESWORD_INTERNAL;
    public static Skill SAO_SINGLESWORD_SA;
    public static Skill SAO_SINGLESWORDGUARD;
    public static Skill SAO_RAPIER_A;
    public static Skill SAO_RAPIER_B;
    public static Skill GS_YOIMIYA_SPECIALATK;

    public static Skill SAOBasicAtkPatched;
    public static Skill GS_Bow_FallAttackPatch;
    public static Skill GS_Bow_BasicAttackPatch;
    public static Skill GS_Bow_Internal;
    public static Skill MUTI_SPECIAL_ATTACK;
    private static final Map<ResourceLocation, Skill> LEARNABLE_SKILLS = Maps.newHashMap();


    public static void registerSkills() {
        SkillManager.register(TagSkill::new,
                TagSkill.createBuilder(
                        new ResourceLocation(EpicAddon.MODID,"sao_single_sword"),
                        EpicAddonSkillCategories.SAO_SINGLE_SWORD
                ).setCreativeTab(EpicAddonItemGroup.ITEMS)
                , EpicAddon.MODID, "sao_single_sword");

        SkillManager.register(DualBladeSkill::new,
                DualBladeSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_dual_sword_skill"))
                        .setCreativeTab(EpicAddonItemGroup.ITEMS)
                , EpicAddon.MODID, "sao_dual_sword_skill");

        SkillManager.register(RapierSkill::new,
                RapierSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_rapier_skill"))
                        .setCreativeTab(EpicAddonItemGroup.ITEMS)
                , EpicAddon.MODID, "sao_rapier_skill");


        SkillManager.register(RapierSpicialAttackSkill::new,
                RapierSpicialAttackSkill.createBuilder(
                                new ResourceLocation(EpicAddon.MODID, "weapon_skill_rapier"))
                        .setResource(WEAPON_INNATE_ENERGY)
                        .setAnimation(EpicAddonAnimations.SAO_RAPIER_SA2)
                        .setAnimation2(EpicAddonAnimations.SAO_RAPIER_SPECIAL_DASH)
                , EpicAddon.MODID, "weapon_skill_rapier");


        SkillManager.register(MutiSpecialSkill::new,
                Skill.createBuilder().setRegistryName(new ResourceLocation(EpicAddon.MODID,"muti_sa"))
                        .setCategory(EpicAddonSkillCategories.MutiSpecialAttack)
                        //.setActivateType(Skill.ActivateType.PASSIVE)
                , EpicAddon.MODID, "muti_sa");


        SkillManager.register(GSSpecialAttack::new,
                (SimpleWeaponInnateSkill.Builder) SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder().setAnimations(new ResourceLocation(EpicAddon.MODID,"biped/gs_yoimiya_sa")).setResource(WEAPON_INNATE_ENERGY)
                                .setRegistryName(new ResourceLocation(EpicAddon.MODID, "gs_yoimiya_sa"))
                , EpicAddon.MODID, "gs_yoimiya_sa");

        SkillManager.register(SAOBasicAtkPatch::new,
                SAOBasicAtkPatch.createBuilder().setCategory(SkillCategories.BASIC_ATTACK)
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.NONE)
                .setRegistryName(new ResourceLocation(EpicAddon.MODID,"sao_basic_attack_patch"))
                , EpicAddon.MODID, "sao_basic_attack_patch");

        SkillManager.register(SAOSingleSwordInternal::new,
                SAOSingleSwordInternal.createBuilder()
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        .setRegistryName(new ResourceLocation(EpicAddon.MODID, "sao_single_sword_internal"))
                        //.setActivateType(Skill.ActivateType.PASSIVE)
                        .setResource(Skill.Resource.NONE)
                , EpicAddon.MODID, "sao_single_sword_internal");


        SkillManager.register(SingleSwordSASkills::new,
                SingleSwordSASkills.createBuilder(new ResourceLocation(EpicAddon.MODID, "single_sword_sa"))
                        .setCategory(SkillCategories.WEAPON_INNATE)
                , EpicAddon.MODID, "single_sword_sa");


        SkillManager.register(GSFallAttack::new,
                GSFallAttack.createBuilder()
                        .setCategory(SkillCategories.AIR_ATTACK)
                        .setRegistryName(new ResourceLocation(EpicAddon.MODID,"gs_air_attack_patch"))
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.STAMINA)
                , EpicAddon.MODID, "gs_air_attack_patch");

        SkillManager.register(GSBasicAtkPatch::new,
                GSBasicAtkPatch.createBuilder()
                        .setCategory(SkillCategories.BASIC_ATTACK)
                        .setRegistryName(new ResourceLocation(EpicAddon.MODID,"gs_basic_attack_patch"))
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.NONE)
                , EpicAddon.MODID, "gs_basic_attack_patch");

        SkillManager.register(GSBowInternal::new,
                GSBowInternal.GetBuilder("gs_bow_internal")
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        //.setActivateType(Skill.)
                        .setResource(Skill.Resource.NONE)
                , EpicAddon.MODID, "gs_bow_internal");
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Register EpicAddon Skill");

        //SAO_SINGLESWORD = new TagSkill(TagSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_single_sword"), EpicAddonSkillCategories.SAO_SINGLE_SWORD)), false);
        //SAO_DUALSWORD = new DualBladeSkill(DualBladeSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_dual_sword_skill"))), false);
        //SAO_SINGLESWORDGUARD = event.registerSkill(new SingleSwordGuardSkill(SingleSwordGuardSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_single_sword_guard_skill"))),true);
        //SAO_RAPIER_A = new RapierSkill(RapierSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_rapier_skill"))),false);
        //SAO_RAPIER_B = event.registerSkill(new RapierSkill(RapierSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_rapier_skill_b"))),false);

        /*
        WEAPON_SKILL_RAPIER = new RapierSpicialAttackSkill(RapierSpicialAttackSkill.createBuilder(
                new ResourceLocation(EpicAddon.MODID, "weapon_skill_rapier"))
                .setConsumption(30.0F)
                .setAnimation(EpicAddonAnimations.SAO_RAPIER_SA2).setAnimation2(EpicAddonAnimations.SAO_RAPIER_SPECIAL_DASH)),false);

        MUTI_SPECIAL_ATTACK = new MutiSpecialSkill(Skill.createBuilder(
                        new ResourceLocation(EpicAddon.MODID, "muti_sa")).setCategory(EpicAddonSkillCategories.MutiSpecialAttack)),false);

        GS_YOIMIYA_SPECIALATK = new GSSpecialAttack(,false);

        SAOBasicAtkPatched = new SAOBasicAtkPatch(SAOBasicAtkPatch.(EpicAddon.MODID,"sao_basic_attack_patch")).setCategory(SkillCategories.BASIC_ATTACK).setConsumption(0.0F).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.NONE)),false);
        SAO_SINGLESWORD_INTERNAL = new SAOSingleSwordInternal(
                SAOSingleSwordInternal.createBuilder()
                        .setCategory(SkillCategories.WEAPON_PASSIVE)
                        .setRegistryName(new ResourceLocation(EpicAddon.MODID, "sao_single_sword_internal"))
                        .setActivateType(Skill.ActivateType.PASSIVE)
                        .setResource(Skill.Resource.NONE)),false);

        SAO_SINGLESWORD_SA = new SingleSwordSASkills(SingleSwordSASkills.createBuilder(
                new ResourceLocation(EpicAddon.MODID, "single_sword_sa")
        )), false);

        GS_Bow_FallAttackPatch = new GSFallAttack(GSFallAttack.createBuilder(new ResourceLocation(EpicAddon.MODID,"gs_air_attack_patch")).setCategory(SkillCategories.AIR_ATTACK).setConsumption(0.0F).setActivateType(Skill.ActivateType.ONE_SHOT).setResource(Skill.Resource.STAMINA)),false);
        GS_Bow_BasicAttackPatch = event.registerSkill(new GSBasicAtkPatch(
                GSBasicAtkPatch.createBuilder(new ResourceLocation(EpicAddon.MODID,"gs_basic_attack_patch"))
                        .setCategory(SkillCategories.BASIC_ATTACK).setConsumption(0.0F)
                        .setActivateType(Skill.ActivateType.ONE_SHOT)
                        .setResource(Skill.Resource.NONE)),false);

        GS_Bow_Internal = event.registerSkill(new GSBowInternal(GSBowInternal.GetBuilder("gs_bow_internal").setCategory(SkillCategories.WEAPON_PASSIVE).setCategory(SkillCategories.WEAPON_PASSIVE).setConsumption(0.0F).setActivateType(Skill.ActivateType.PASSIVE).setResource(Skill.Resource.NONE)),false);

        LOGGER.info("EpicAddon Skill Loaded");

         */
    }

    public static void BuildSkills(SkillBuildEvent event){
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("Build EpicAddon Skill");

        SAO_SINGLESWORD = event.build(EpicAddon.MODID, "sao_single_sword");
        SAO_DUALSWORD = event.build(EpicAddon.MODID, "sao_dual_sword_skill");
        SAO_RAPIER_A = event.build(EpicAddon.MODID,"sao_rapier_skill");
        WEAPON_SKILL_RAPIER = event.build(EpicAddon.MODID, "weapon_skill_rapier");
        MUTI_SPECIAL_ATTACK = event.build(EpicAddon.MODID, "muti_sa");
        GS_YOIMIYA_SPECIALATK = event.build(EpicAddon.MODID, "gs_yoimiya_sa");
        SAOBasicAtkPatched = event.build(EpicAddon.MODID,"sao_basic_attack_patch");
        SAO_SINGLESWORD_INTERNAL = event.build(EpicAddon.MODID, "sao_single_sword_internal");
        SAO_SINGLESWORD_SA = event.build(EpicAddon.MODID, "single_sword_sa");
        GS_Bow_FallAttackPatch = event.build(EpicAddon.MODID,"gs_air_attack_patch");
        GS_Bow_BasicAttackPatch = event.build(EpicAddon.MODID,"gs_basic_attack_patch");
        GS_Bow_Internal = event.build(EpicAddon.MODID,"gs_bow_internal");
    }

    public static Collection<Skill> getLearnableSkills() {
        return LEARNABLE_SKILLS.values();
    }
}

