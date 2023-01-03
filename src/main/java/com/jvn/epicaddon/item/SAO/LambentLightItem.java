package com.jvn.epicaddon.item.SAO;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

public class LambentLightItem extends WeaponItem {
    private final float attackDamage;
    private final float attackSpeed;

    public LambentLightItem(Item.Properties build, Tier tier) {
        super(tier, 0, 0.0f, build);
        this.attackDamage = 9F;//21.3F
        this.attackSpeed = +1.0F - (0.05F * tier.getLevel());
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }
}
