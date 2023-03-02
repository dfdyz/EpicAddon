package com.jvn.epicaddon.mixin;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Camera.class)
public interface CameraAccessor {
    @Invoker
    void invokeSetPosition(double var1, double var3, double var5);
    @Invoker
    void invokeMove(double var1, double var3, double var5);
    @Invoker
    void invokeSetRotation(float var1, float var3);
}
