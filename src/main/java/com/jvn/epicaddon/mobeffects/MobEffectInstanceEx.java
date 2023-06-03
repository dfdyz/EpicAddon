package com.jvn.epicaddon.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class MobEffectInstanceEx extends MobEffectInstance {
    private float[] data = null;
    public MobEffectInstanceEx(MobEffectEx p_19513_) {
        super(p_19513_);
    }

    public MobEffectInstanceEx(MobEffectEx p_19515_, int p_19516_) {
        super(p_19515_, p_19516_);
    }

    public MobEffectInstanceEx(MobEffectEx p_19518_, int p_19519_, int p_19520_) {
        super(p_19518_, p_19519_, p_19520_);
    }

    public MobEffectInstanceEx(MobEffectEx p_19522_, int p_19523_, int p_19524_, boolean p_19525_, boolean p_19526_) {
        super(p_19522_, p_19523_, p_19524_, p_19525_, p_19526_);
    }

    public MobEffectInstanceEx(MobEffectEx p_19528_, int p_19529_, int p_19530_, boolean p_19531_, boolean p_19532_, boolean p_19533_) {
        super(p_19528_, p_19529_, p_19530_, p_19531_, p_19532_, p_19533_);
    }

    public MobEffectInstanceEx(MobEffectEx p_19535_, int p_19536_, int p_19537_, boolean p_19538_, boolean p_19539_, boolean p_19540_, @Nullable MobEffectInstance p_19541_) {
        super(p_19535_, p_19536_, p_19537_, p_19538_, p_19539_, p_19540_, p_19541_);
    }

    public MobEffectInstanceEx(MobEffectInstance p_19543_) {
        super(p_19543_);
    }

    @Override
    public void applyEffect(LivingEntity livingEntity) {
        if (this.getDuration() > 0) {
            if(data == null){
                data = new float[]{0};
            }
            this.getEffectEx().applyEffectTickEX(livingEntity, this.getAmplifier(), data);
        }
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public MobEffectEx getEffectEx() {
        return (MobEffectEx) super.getEffect();
    }
}
