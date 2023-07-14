package com.jvn.epicaddon.resources;

import yesman.epicfight.skill.SkillCategory;

public enum EpicAddonSkillCategories implements SkillCategory {
    SAO_SINGLE_SWORD(true,true,true),
    GEN_SHIN_IMPACT_BOW(true,true,true),
    MutiSpecialAttack(true, true, false);

    boolean shouldSaved;
    boolean shouldSyncronized;
    boolean learnable;
    int id;

    EpicAddonSkillCategories(boolean shouldSave, boolean shouldSyncronized, boolean learnable) {
        this.shouldSaved = shouldSave;
        this.shouldSyncronized = shouldSyncronized;
        this.learnable = learnable;
        this.id = this.ENUM_MANAGER.assign(this);
    }

    @Override
    public boolean shouldSave() {
        return this.shouldSaved;
    }
    @Override
    public boolean shouldSynchronize() {
        return this.shouldSyncronized;
    }
    @Override
    public boolean learnable() {
        return this.learnable;
    }
    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
