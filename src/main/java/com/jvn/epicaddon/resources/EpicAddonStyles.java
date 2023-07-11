package com.jvn.epicaddon.resources;

import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.world.capabilities.item.Style;

public enum EpicAddonStyles implements Style {
    SAO_DUAL_SWORD(true),
    SAO_DUAL_SWORD_LOCKED(true),
    SAO_SINGLE_SWORD(true),
    SAO_RAPIER(true),

    SAO_RAPIER_LOCKED(false);

    final boolean canUseOffhand;
    final int id;

    EpicAddonStyles(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }

    public boolean canUseOffhand() {
        return this.canUseOffhand;
    }




}
