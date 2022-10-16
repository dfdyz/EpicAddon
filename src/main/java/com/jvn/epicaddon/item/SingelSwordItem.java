package com.jvn.epicaddon.item;

import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class SingelSwordItem extends WeaponItem {
    protected final int EnchantmentValue;
    public SingelSwordItem(Properties builder,Tier tier, int EnchantmentValue) {
        super(tier, 0,0.0f, builder);
        this.EnchantmentValue = EnchantmentValue;
    }

    @Override
    public int getEnchantmentValue() {
        return this.EnchantmentValue;
    }
}
