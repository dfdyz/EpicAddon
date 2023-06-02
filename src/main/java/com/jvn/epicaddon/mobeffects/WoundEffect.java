package com.jvn.epicaddon.mobeffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class WoundEffect extends MobEffect {
    public WoundEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int lv) {
        return duration <= 1;
    }

    @Override
    public void applyEffectTick(LivingEntity owner, int lv) {
        float dmg = lv/100.0f*owner.getMaxHealth();
        owner.hurt(DamageSource.MAGIC, dmg);
    }
}
