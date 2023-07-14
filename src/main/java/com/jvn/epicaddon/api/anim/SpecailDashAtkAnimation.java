package com.jvn.epicaddon.api.anim;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class SpecailDashAtkAnimation extends DashAttackAnimation {
    public SpecailDashAtkAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint index, String path, Armature model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public SpecailDashAtkAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint index, String path, boolean noDirectionAttack, Armature model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, noDirectionAttack, model);
    }

    @Override
    protected Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        return super.getCoordVector(entitypatch, dynamicAnimation).add(0f,(float) LivingEntity.DEFAULT_BASE_GRAVITY,0f);
    }
}
