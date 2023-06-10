package com.jvn.epicaddon.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Consumer;

@Mixin(value = StaticAnimation.Event.class, remap = false)
public interface AnimEventAccessor {
    @Accessor("time")
    public float getTime();
    @Accessor("executionSide")
    public StaticAnimation.Event.Side getSide();
    @Accessor("event")
    public Consumer<LivingEntityPatch<?>> getEventConsumer();
}
