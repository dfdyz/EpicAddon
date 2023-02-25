package com.jvn.epicaddon.utils;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;

import javax.annotation.Nullable;

public class DamageSources {
    public static DamageSource genshin_arrow(AbstractArrow p_19347_, @Nullable Entity p_19348_, int idx) {
        return (new IndirectEntityDamageSource("genshin_arrow"+idx, p_19347_, p_19348_)).setProjectile();
    }
}
