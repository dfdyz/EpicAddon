package com.jvn.epicaddon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.types.ActionAnimation;

@Mixin(value = ActionAnimation.ActionTime.class, remap = false)
public interface ActionTimeAccessor {
    @Accessor("begin")
    public float getBegin();
    @Accessor("end")
    public float getEnd();
}
