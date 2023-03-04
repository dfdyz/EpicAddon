package com.jvn.epicaddon.skills.GenShin;

import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.skill.PassiveSkill;
import yesman.epicfight.skill.Skill;

public class Yoimiya extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public Yoimiya(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Skill.Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
        return (new Skill.Builder<PassiveSkill>(resourceLocation)).setCategory(EpicAddonSkillCategories.GenShinImpact_Bow).setConsumption(0.0F).setMaxStack(0).setResource(Skill.Resource.NONE).setRequiredXp(0);
    }

}