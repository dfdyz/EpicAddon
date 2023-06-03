package com.jvn.epicaddon.mobeffects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.MathUtils;

public class WoundEffect extends MobEffectEx {
    public WoundEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int lv) {
        return duration <= 1;
    }

    @Override
    public void applyEffectTick(LivingEntity owner, int lv) {
        float dmg = Math.max(owner.getMaxHealth()*lv/100f, lv);
        dmg += 0.4f*(owner.getMaxHealth()-owner.getHealth());
        owner.hurt(DamageSource.MAGIC, dmg);
    }

    @Override
    public void applyEffectTickEX(LivingEntity owner, int lv, float[] data) {
        float dmg = Math.max(owner.getMaxHealth()*lv/100f, data[0]);
        dmg += 0.4f*(owner.getMaxHealth()-owner.getHealth());
        owner.hurt(DamageSource.MAGIC, dmg);
    }
}
