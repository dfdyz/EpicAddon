package com.jvn.epicaddon.item.SAO;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
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
