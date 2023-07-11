package com.jvn.epicaddon.resources;

import com.jvn.epicaddon.register.RegItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public enum EpicAddonTier implements Tier {
    SAO_Normal(4, 9999, 9.0F, 6.0F, 22, () -> {
        return Ingredient.of(new ItemLike[]{RegItems.DragonShitCrystal.get()});
    }),

    SAO_IRON(2, 380, 6.0F, 2.5F, 17, () -> {
        return Ingredient.of(Items.IRON_INGOT);
    }),

    SAO_Special(4, Integer.MAX_VALUE, 9.0F, 6.0F, 22, () -> {
        return Ingredient.of(new ItemLike[]{RegItems.DragonShitCrystal.get()});
    }),
    ;


    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyLoadedValue<Ingredient> repairMaterial;

    private EpicAddonTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier repairMaterialIn) {
        this.harvestLevel = harvestLevelIn;
        this.maxUses = maxUsesIn;
        this.efficiency = efficiencyIn;
        this.attackDamage = attackDamageIn;
        this.enchantability = enchantabilityIn;
        this.repairMaterial = new LazyLoadedValue(repairMaterialIn);
    }

    public int getUses() {
        return this.maxUses;
    }

    public float getSpeed() {
        return this.efficiency;
    }

    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    public int getLevel() {
        return this.harvestLevel;
    }

    public int getEnchantmentValue() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairMaterial.get();
    }
}
