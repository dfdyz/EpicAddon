package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.api.anim.fuckAPI.IPatchedState;
import com.jvn.epicaddon.api.anim.fuckAPI.PatchedStateSpectrum;
import com.jvn.epicaddon.events.RenderEvent;
import com.jvn.epicaddon.mixin.PhaseAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;

public class ScanAttackAnimationEx extends ScanAttackAnimation implements IPatchedState {
    protected final PatchedStateSpectrum.Blueprint patchedBlueprint = new PatchedStateSpectrum.Blueprint();
    private final PatchedStateSpectrum patchedState = new PatchedStateSpectrum();
    protected SpecialPhase invisiblePhase;
    protected SpecialPhase movablePhase;

    public ScanAttackAnimationEx(float convertTime, float antic,float contact, float recovery, InteractionHand hand, int maxStrikes, @Nullable Collider collider, String scanner, String path, Model model) {
        super(convertTime, antic, contact, recovery, hand, maxStrikes, collider, scanner, path, model);

        this.patchedBlueprint.clear();
        for (Phase _phase : phases) {
            PhaseAccessor phase = (PhaseAccessor) _phase;

            this.patchedBlueprint
                    .newTimePair(phase.getStart(),antic)
                    .addState(EntityState.PHASE_LEVEL, 1)
                    .newTimePair(phase.getStart(), phase.getContact() + 0.01F)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .newTimePair(phase.getStart(), phase.getRecovery())
                    .addState(EntityState.MOVEMENT_LOCKED, true)
                    .addState(EntityState.CAN_BASIC_ATTACK, false)
                    .newTimePair(phase.getStart(), phase.getEnd())
                    .addState(EntityState.INACTION, true)
                    .addState(EntityState.TURNING_LOCKED, true)
                    .newTimePair(antic, phase.getContact() + 0.01F)
                    .addState(EntityState.ATTACKING, true)
                    .addState(EntityState.PHASE_LEVEL, 2)
                    .newTimePair(phase.getContact()+0.01F, phase.getEnd())
                    .addState(EntityState.PHASE_LEVEL, 3);
        }

        //this.Aid = aid;
    }

    public ScanAttackAnimationEx SetInvisiblePhase(float start, float end){
        invisiblePhase = new SpecialPhase(start,end);
        return this;
    }

    public ScanAttackAnimationEx SetMovablePhase(float start, float end){
        movablePhase = new SpecialPhase(start,end);
        return this;
    }

    @Override
    public EntityState getPatchedState(float t) {
        return patchedState.bindStates(t);
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        if (entitypatch.isLogicalClient()){
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            float elapsedTime = player.getElapsedTime();
            LivingEntity e = entitypatch.getOriginal();
            if(invisiblePhase.isInPhase(elapsedTime)){
                RenderEvent.SkipToRender.add(e);
            }
            else {
                if(RenderEvent.SkipToRender.contains(e)) RenderEvent.SkipToRender.remove(e);
            }
        }
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        patchedState.readFrom(patchedBlueprint);
    }

    @Override
    protected void modifyPose(Pose pose, LivingEntityPatch<?> entitypatch, float time) {
        JointTransform jt = pose.getOrDefaultTransform("Root");
        Vec3f jointPosition = jt.translation();
        OpenMatrix4f toRootTransformApplied = entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature().searchJointByName("Root").getLocalTrasnform().removeTranslation();
        OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, (OpenMatrix4f)null);
        Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, (Vec3f)null);
        if(movablePhase.isInPhase(time)){
            worldPosition.x = 0.0F;
//        worldPosition.y = moveRootY ? worldPosition.y : 0.0F;
            worldPosition.z = 0.0F;
        }
        OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
        jointPosition.x = worldPosition.x;
        jointPosition.y = worldPosition.y;
        jointPosition.z = worldPosition.z;
    }

    @Override
    protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        float t = entitypatch.getAnimator().getPlayerFor(this).getElapsedTime();
        if (!movablePhase.isInPhase(t)){
            return new Vec3f();
        }
        Vec3f vec3f = super.getCoordVector(entitypatch, dynamicAnimation);
        vec3f.y=0;
        return vec3f;
    }

    public record SpecialPhase(float start, float end) {
        public boolean isInPhase(float t){
            return t >= start && t<=end;
        }
    }
}
