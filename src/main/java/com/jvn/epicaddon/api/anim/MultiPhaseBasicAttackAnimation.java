package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.mixin.ActionTimeAccessor;
import com.jvn.epicaddon.mixin.AnimEventAccessor;
import com.jvn.epicaddon.mixin.PhaseAccessor;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ExtraDamageType;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;

public class MultiPhaseBasicAttackAnimation extends AttackAnimation {
    public MultiPhaseBasicAttackAnimation(float convertTime, String path, Model model, Phase... phases) {
        super(convertTime, path, model, phases);
    }
    @Override
    public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0);

        if (entitypatch instanceof PlayerPatch<?>) {
            PlayerPatch<?> playerpatch = (PlayerPatch<?>)entitypatch;
            Phase phase = this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            extTime *= this.totalTime * playerpatch.getAttackSpeed(phase.getHand());
        }

        extTime = Math.max(extTime - this.convertTime, 0);
        super.setLinkAnimation(pose1, extTime, entitypatch, dest);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
    }

    private boolean validateMovement(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        LivingEntity livingentity = (LivingEntity)entitypatch.getOriginal();
        if (entitypatch.isLogicalClient()) {
            if (!(livingentity instanceof LocalPlayer)) {
                return false;
            }
        } else if (livingentity instanceof ServerPlayer) {
            return false;
        }

        if (animation instanceof LinkAnimation) {
            return !(Boolean)this.getProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK).orElse(true) ? false : this.shouldMove(0.0F);
        } else {
            return this.shouldMove(entitypatch.getAnimator().getPlayerFor(animation).getElapsedTime());
        }
    }

    private boolean shouldMove(float currentTime) {
        if (this.properties.containsKey(AnimationProperty.ActionAnimationProperty.MOVE_TIME)) {
            ActionTime[] actionTimes = this.getProperty(AnimationProperty.ActionAnimationProperty.MOVE_TIME).get();
            ActionTime[] var3 = actionTimes;
            int var4 = actionTimes.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                ActionTimeAccessor actionTime = (ActionTimeAccessor) var3[var5];
                if (actionTime.getBegin() <= currentTime && currentTime <= actionTime.getEnd()) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }
    private void move(LivingEntityPatch<?> entitypatch, DynamicAnimation animation) {
        if (this.validateMovement(entitypatch, animation)) {
            EntityState state = this.getState(entitypatch.getAnimator().getPlayerFor(this).getElapsedTime());
            if (state.inaction()) {
                LivingEntity livingentity = entitypatch.getOriginal();
                Vec3f vec3 = this.getCoordVector(entitypatch, animation);
                BlockPos blockpos = new BlockPos(livingentity.getX(), livingentity.getBoundingBox().minY - 1.0, livingentity.getZ());
                BlockState blockState = livingentity.level.getBlockState(blockpos);
                AttributeInstance movementSpeed = livingentity.getAttribute(Attributes.MOVEMENT_SPEED);
                boolean soulboost = blockState.is(BlockTags.SOUL_SPEED_BLOCKS) && EnchantmentHelper.getEnchantmentLevel(Enchantments.SOUL_SPEED, livingentity) > 0;
                double speedFactor = soulboost ? 1.0 : (double)livingentity.level.getBlockState(blockpos).getBlock().getSpeedFactor();
                double moveMultiplier = (Boolean)this.getProperty(AnimationProperty.ActionAnimationProperty.AFFECT_SPEED).orElse(false) ? movementSpeed.getValue() / movementSpeed.getBaseValue() : 1.0;
                livingentity.move(MoverType.SELF, new Vec3((double)vec3.x * moveMultiplier, (double)vec3.y, (double)vec3.z * moveMultiplier * speedFactor));
            }

        }
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        this.getProperty(AnimationProperty.StaticAnimationProperty.EVENTS).ifPresent((events) -> {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            if (player != null) {
                float prevElapsed = player.getPrevElapsedTime();
                float elapsed = player.getElapsedTime();
                Event[] var6 = events;
                int var7 = events.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    Event event = var6[var8];
                    AnimEventAccessor accessor = (AnimEventAccessor) event;
                    float eventTime = accessor.getTime();
                    if (eventTime != Float.MIN_VALUE && eventTime != Float.MAX_VALUE && !(eventTime < prevElapsed) && !(eventTime >= elapsed)) {
                        event.testAndExecute(entitypatch);
                    }
                }
            }
        });
        entitypatch.getOriginal().animationSpeed = 0.0F;
        this.move(entitypatch, this);

        if (!entitypatch.isLogicalClient()) {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            float elapsedTime = player.getElapsedTime();
            float prevElapsedTime = player.getPrevElapsedTime();
            EntityState state = this.getState(elapsedTime);
            EntityState prevState = this.getState(prevElapsedTime);
            Phase phase = this.getPhaseByTime(elapsedTime);
            Phase prevPhase = this.getPhaseByTime(prevElapsedTime);
            if (state.getLevel() == 1 && !state.turningLocked()) {
                if (entitypatch instanceof MobPatch) {
                    ((Mob)entitypatch.getOriginal()).getNavigation().stop();
                    entitypatch.getOriginal().attackAnim = 2.0F;
                    LivingEntity target = entitypatch.getTarget();
                    if (target != null) {
                        entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
                    }
                }
            } else if (prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2) {
                if (!prevState.attacking() || prevPhase != phase) {
                    entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                    entitypatch.currentlyAttackedEntity.clear();
                }

                this.hurtCollidingEntities(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
            }
        }
    }

    @Override
    protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3f vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        if (entitypatch.shouldBlockMoving() && this.getProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3.scale(0.0F);
        }

        return vec3;
    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

    @Override
    public float getPlaySpeed(LivingEntityPatch<?> entitypatch) {
        return super.getPlaySpeed(entitypatch);
    }

    @Override
    protected float getDamageTo(LivingEntityPatch<?> entitypatch, LivingEntity target, Phase _phase, ExtendedDamageSource source) {
        float f = entitypatch.getDamageTo(target, source, _phase.getHand());
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, entitypatch.getOriginal());
        ValueCorrector cor = new ValueCorrector(0, (i > 0) ? 1.0F + (float)i / (float)(i + 1.0F) : 1.0F, 0);

        PhaseAccessor phase = (PhaseAccessor) _phase;
        phase.getPropertyInvoker(AnimationProperty.AttackPhaseProperty.DAMAGE).ifPresent((opt) -> cor.merge(opt));
        float totalDamage = cor.getTotalValue(f);
        ExtraDamageType extraCalculator = phase.getPropertyInvoker(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE).orElse(null);

        if (extraCalculator != null) {
            totalDamage += extraCalculator.get(entitypatch.getOriginal(), target);
        }

        return totalDamage;
    }

}
