package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.api.anim.fuckAPI.PatchedStateSpectrum;
import com.jvn.epicaddon.mixin.PhaseAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.entity.PartEntity;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public class YoimiyaSAAnimation extends AttackAnimation {
    //private final int Aid;
    //public final String Hjoint;

    public YoimiyaSAAnimation(float convertTime, float antic, float recovery, InteractionHand hand, @Nullable Collider collider, String scanner, String path, Model model) {
        super(convertTime, path, model,
                new Phase(0.0F, 0f, antic, recovery, Float.MAX_VALUE, hand, scanner, collider));

        //Hjoint = shoot;
        this.addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        //this.Aid = aid;
    }

    @Override
    public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0);

        if (entitypatch instanceof PlayerPatch<?>) {
            PlayerPatch<?> playerpatch = (PlayerPatch<?>)entitypatch;
            PhaseAccessor phase =  (PhaseAccessor)this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            extTime *= (this.totalTime * playerpatch.getAttackSpeed(phase.getHand()));
        }

        extTime = Math.max(extTime - this.convertTime, 0);
        super.setLinkAnimation(pose1, extTime, entitypatch, dest);
    }

    @Override
    protected void modifyPose(Pose pose, LivingEntityPatch<?> entitypatch, float time) {
        JointTransform jt = pose.getOrDefaultTransform("Root");
        Vec3f jointPosition = jt.translation();
        OpenMatrix4f toRootTransformApplied = entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature().searchJointByName("Root").getLocalTrasnform().removeTranslation();
        OpenMatrix4f toOrigin = OpenMatrix4f.invert(toRootTransformApplied, (OpenMatrix4f)null);
        Vec3f worldPosition = OpenMatrix4f.transform3v(toRootTransformApplied, jointPosition, (Vec3f)null);
        worldPosition.x = 0.0F;
        worldPosition.y = 0.0F;
        worldPosition.z = 0.0F;
        OpenMatrix4f.transform3v(toOrigin, worldPosition, worldPosition);
        jointPosition.x = worldPosition.x;
        jointPosition.y = worldPosition.y;
        jointPosition.z = worldPosition.z;
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();

        if (!this.properties.containsKey(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", (1.0F / this.totalTime)));
            this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }

    }

    @Override
    protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().setNoGravity(true);
        Vec3f vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        entitypatch.getOriginal().setNoGravity(false);
        return vec3.multiply(1,2,1);
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);

        if (!entitypatch.isLogicalClient()) {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            float elapsedTime = player.getElapsedTime();
            float prevElapsedTime = player.getPrevElapsedTime();
            EntityState state = this.getState(elapsedTime);
            EntityState prevState = this.getState(prevElapsedTime);
            Phase phase = this.getPhaseByTime(elapsedTime);

            if (state.getLevel() == 1 && !state.turningLocked()) {
                if (entitypatch instanceof MobPatch) {
                    ((Mob)entitypatch.getOriginal()).getNavigation().stop();
                    entitypatch.getOriginal().attackAnim = 2;
                    LivingEntity target = entitypatch.getTarget();

                    if (target != null) {
                        entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
                    }
                }
            } else if (prevState.attacking() || state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2)) {
                if (!prevState.attacking()) {
                    //entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                    entitypatch.currentlyAttackedEntity.clear();
                }

                //EpicAddon.LOGGER.info(String.valueOf(prevElapsedTime));
                //this.ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
            }
        }
    }

        //entitypatch.currentlyAttackedEntity.add(entitypatch.getOriginal());



    @Override
    public void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {

    }
    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }
}
