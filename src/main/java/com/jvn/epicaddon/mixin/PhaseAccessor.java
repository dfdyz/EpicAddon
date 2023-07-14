package com.jvn.epicaddon.mixin;

import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;

import java.util.Optional;

@Mixin(value = AttackAnimation.Phase.class, remap = false)
public interface PhaseAccessor {
    @Accessor("start")
    public float getStart();
    @Accessor("antic")
    public float getAntic();
    @Accessor("preDelay")
    public float getPreDelay();
    @Accessor("contact")
    public float getContact();
    @Accessor("recovery")
    public float getRecovery();
    @Accessor("end")
    public float getEnd();
    @Accessor("joint")
    public Joint getJoint();
    @Accessor("hand")
    public InteractionHand getHand();
    @Accessor("collider")
    public Collider getCollider();
    @Invoker("getProperty")
    public <V> Optional<V> getPropertyInvoker(AnimationProperty.AttackPhaseProperty<V> propertyType);
}
