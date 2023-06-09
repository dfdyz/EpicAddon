package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.mixin.PhaseAccessor;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.math.ExtraDamageType;
import yesman.epicfight.api.utils.math.ValueCorrector;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.Locale;

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

        if (!this.properties.containsKey(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", (1.0F / this.totalTime)));
            this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
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
