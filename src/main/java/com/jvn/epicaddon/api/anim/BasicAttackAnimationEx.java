package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.api.anim.fuckAPI.IPatchedState;
import com.jvn.epicaddon.api.anim.fuckAPI.PatchedStateSpectrum;
import com.jvn.epicaddon.mixin.PhaseAccessor;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.Locale;

public class BasicAttackAnimationEx extends AttackAnimation implements IPatchedState {
    protected final PatchedStateSpectrum.Blueprint patchedBlueprint = new PatchedStateSpectrum.Blueprint();
    private final PatchedStateSpectrum patchedState = new PatchedStateSpectrum();

    public BasicAttackAnimationEx(float convertTime, float antic, float contact, float recovery, @javax.annotation.Nullable Collider collider, String index, String path, Model model) {
        this(convertTime, antic, antic, contact, recovery, collider, index, path, model);



    }

    public BasicAttackAnimationEx(float convertTime, float antic, float preDelay, float contact, float recovery, @javax.annotation.Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);

        this.patchedBlueprint.clear();

        for (Phase _phase : phases) {
            PhaseAccessor phase = (PhaseAccessor) _phase;

            this.patchedBlueprint
                    .newTimePair(phase.getStart(),preDelay)
                    .addState(EntityState.PHASE_LEVEL, 1)
                    .newTimePair(phase.getStart(), phase.getContact() + 0.01F)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .newTimePair(phase.getStart(), phase.getRecovery())
                    .addState(EntityState.MOVEMENT_LOCKED, true)
                    .addState(EntityState.CAN_BASIC_ATTACK, false)
                    .newTimePair(phase.getStart(), phase.getEnd())
                    .addState(EntityState.INACTION, true)
                    .newTimePair(phase.getAntic(), phase.getRecovery())
                    .addState(EntityState.TURNING_LOCKED, true)
                    .newTimePair(preDelay, phase.getContact() + 0.01F)
                    .addState(EntityState.ATTACKING, true)
                    .addState(EntityState.PHASE_LEVEL, 2)
                    .newTimePair(phase.getContact()+0.01F, phase.getEnd())
                    .addState(EntityState.PHASE_LEVEL, 3);
        }
    }

    public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0.0F);
        if (entitypatch instanceof PlayerPatch<?> playerpatch) {
            AttackAnimation.Phase phase = this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            //PhaseAccessor phaseAccessor = (PhaseAccessor)phase;
            extTime *= this.totalTime * playerpatch.getAttackSpeed(phase.getHand());
        }

        extTime = Math.max(extTime - this.convertTime, 0.0F);
        super.setLinkAnimation(pose1, extTime, entitypatch, dest);
    }

    protected void onLoaded() {
        super.onLoaded();
        if (!this.properties.containsKey(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.totalTime));
            this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }
        patchedState.readFrom(patchedBlueprint);
    }

    protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3f vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        if (entitypatch.shouldBlockMoving() && (Boolean)this.getProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3.scale(0.0F);
        }

        return vec3;
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }

    @Override
    public EntityState getPatchedState(float t) {
        return patchedState.bindStates(t);
    }
}
