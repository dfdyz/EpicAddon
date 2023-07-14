package com.jvn.epicaddon.resources;

import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum EpicAddonSkillSlots implements SkillSlot {
    SKILL_SELECTOR(EpicAddonSkillCategories.MutiSpecialAttack),
    SAO_SINGLE_SWORD(EpicAddonSkillCategories.SAO_SINGLE_SWORD);
    SkillCategory category;
    int id;

    private EpicAddonSkillSlots(SkillCategory category) {
        this.category = category;
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
    }

    public SkillCategory category() {
        return this.category;
    }

    public int universalOrdinal() {
        return this.id;
    }
}