package com.jvn.epicaddon.api.ItemAnim;

import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class ItemConcurrentLinkAnimation extends ItemAnimation{
    protected ItemAnimation nextAnimation;
    protected ItemAnimation currentAnimation;
    protected float startsAt;

    public ItemConcurrentLinkAnimation() {
    }

    public void acceptFrom(ItemAnimation currentAnimation, ItemAnimation nextAnimation, float time) {
        this.currentAnimation = currentAnimation;
        this.nextAnimation = nextAnimation;
        this.startsAt = time;
        this.setTotalTime(nextAnimation.getConvertTime());
    }

    public void tick(LivingEntityPatch<?> entitypatch) {
        this.nextAnimation.linkTick(entitypatch, this);
    }

    public void end(LivingEntityPatch<?> entitypatch, ItemAnimation nextAnimation, boolean isEnd) {
        if (!isEnd) {
            this.nextAnimation.end(entitypatch, nextAnimation, isEnd);
        } else if (this.startsAt > 0.0F) {
            EpicAddonCapabilities.getAnimatedItem(entitypatch).getAnimator().animationPlayer.setElapsedTime(this.startsAt);
            EpicAddonCapabilities.getAnimatedItem(entitypatch).getAnimator().animationPlayer.markToDoNotReset();
            this.startsAt = 0.0F;
        }

    }

    public EntityState getState(LivingEntityPatch<?> entitypatch, float time) {
        return this.nextAnimation.getState(entitypatch, 0.0F);
    }

    public <T> T getState(EntityState.StateFactor<T> stateFactor, LivingEntityPatch<?> entitypatch, float time) {
        return this.nextAnimation.getState(stateFactor, entitypatch, 0.0F);
    }


    @Override
    public Pose getPoseByTime(ItemStack itemStack, float time, float partialTicks) {
        float elapsed = time + this.startsAt;
        float currentElapsed = elapsed % this.currentAnimation.getTotalTime();
        float nextElapsed = elapsed % this.nextAnimation.getTotalTime();
        Pose currentAnimPose = this.currentAnimation.getPoseByTime(itemStack, currentElapsed, 1.0F);
        Pose nextAnimPose = this.nextAnimation.getPoseByTime(itemStack, nextElapsed, 1.0F);
        float interpolate = time / this.getTotalTime();
        return Pose.interpolatePose(currentAnimPose, nextAnimPose, interpolate);
    }

    public float getPlaySpeed(LivingEntityPatch<?> entitypatch) {
        return this.nextAnimation.getPlaySpeed(entitypatch);
    }

    public void setNextAnimation(ItemAnimation animation) {
        this.nextAnimation = animation;
    }

    public boolean isJointEnabled(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        return this.nextAnimation.isJointEnabled(entitypatch, layer, joint);
    }

    public boolean isMainFrameAnimation() {
        return this.nextAnimation.isMainFrameAnimation();
    }

    public boolean isReboundAnimation() {
        return this.nextAnimation.isReboundAnimation();
    }

    public ItemAnimation getRealAnimation() {
        return this.nextAnimation;
    }

    public String toString() {
        return "ConcurrentLinkAnimation: Mix " + this.currentAnimation + " and " + this.nextAnimation;
    }
}
