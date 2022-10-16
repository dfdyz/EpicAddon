package com.jvn.epicaddon.register;


import com.google.common.collect.Maps;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.skills.DualSwordSkill;
import com.jvn.epicaddon.skills.RapierSkill;
import com.jvn.epicaddon.skills.SingleSwordGuardSkill;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.forgeevent.SkillRegistryEvent;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.SimpleSpecialAttackSkill;
import yesman.epicfight.skill.Skill;

import java.util.Collection;
import java.util.Map;

public class RegEpicAddonSkills {

    public static Skill WEAPON_SKILL_RAPIER;


    public static Skill SAO_DUALSWORD;
    public static Skill SAO_SINGLESWORDGUARD;

    public static Skill SAO_RAPIER;

    private static final Map<ResourceLocation, Skill> LEARNABLE_SKILLS = Maps.newHashMap();

    public static void registerSkills(SkillRegistryEvent event) {
        Logger LOGGER = LogUtils.getLogger();
        LOGGER.info("EpicAddon Skill Loading Event");
        SAO_DUALSWORD = event.registerSkill(new DualSwordSkill(DualSwordSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_dual_sword_skill"))),true);
        SAO_SINGLESWORDGUARD = event.registerSkill(new SingleSwordGuardSkill(SingleSwordGuardSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_single_sword_guard_skill"))),true);
        SAO_RAPIER = event.registerSkill(new RapierSkill(RapierSkill.createBuilder(new ResourceLocation(EpicAddon.MODID,"sao_rapier_skill"))),true);

        WEAPON_SKILL_RAPIER = event.registerSkill(new SimpleSpecialAttackSkill(SimpleSpecialAttackSkill.createBuilder(new ResourceLocation(EpicAddon.MODID, "weapon_skill_rapier")).setConsumption(30.0F).setAnimations(EpicAddonAnimations.SAO_RAPIER_DASH)),false);

        LOGGER.info("EpicAddon Skill Loaded");
    }

    public static Collection<Skill> getLearnableSkills() {
        return LEARNABLE_SKILLS.values();
    }
}

