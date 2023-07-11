package com.jvn.epicaddon.resources;

import yesman.epicfight.skill.SkillCategory;

public enum EpicAddonSkillCategories implements SkillCategory {
    SAO_SINGLE_SWORD(true,true,true),
    GEN_SHIN_IMPACT_BOW(true,true,true),
    MutiSpecialAttack(false, true, true);

    boolean shouldSaved;
    boolean shouldSyncronized;
    boolean modifiable;
    int id;

    EpicAddonSkillCategories(boolean shouldSave, boolean shouldSyncronized, boolean modifiable) {
        this.shouldSaved = shouldSave;
        this.shouldSyncronized = shouldSyncronized;
        this.modifiable = modifiable;
        this.id = this.ENUM_MANAGER.assign(this);
    }

    @Override
    public boolean shouldSaved() {
        return this.shouldSaved;
    }
    @Override
    public boolean shouldSynchronized() {
        return this.shouldSyncronized;
    }
    @Override
    public boolean learnable() {
        return this.modifiable;
    }
    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
