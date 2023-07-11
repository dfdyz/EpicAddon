package com.jvn.epicaddon.item.SAO;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import yesman.epicfight.world.item.WeaponItem;

import java.util.function.Consumer;

public class ElucidatorItem extends WeaponItem {

    public ElucidatorItem(Item.Properties build, Tier tier) {
        super(tier, 0, 0.0f, build);
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return amount;
    }

    @Override
    public boolean canBeDepleted() {
        return false;
    }

}