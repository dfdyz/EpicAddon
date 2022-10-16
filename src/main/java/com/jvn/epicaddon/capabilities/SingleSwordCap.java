package com.jvn.epicaddon.capabilities;

import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public class SingleSwordCap extends WeaponCapability {

    protected SingleSwordCap(CapabilityItem.Builder builder) {
        super(builder);
    }

    @Override
    public boolean canBePlacedOffhand() {
        return this.canBePlacedOffhand;
    }
}
