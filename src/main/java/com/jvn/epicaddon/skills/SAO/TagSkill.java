package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.skill.PassiveSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class TagSkill extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public TagSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Skill.Builder<TagSkill> createBuilder(ResourceLocation resourceLocation, SkillCategory category) {
        return (new Skill.Builder<TagSkill>(resourceLocation)).setCategory(category).setConsumption(0.0F).setMaxStack(0).setResource(Resource.NONE).setRequiredXp(0).setActivateType(ActivateType.PASSIVE);
    }

}
