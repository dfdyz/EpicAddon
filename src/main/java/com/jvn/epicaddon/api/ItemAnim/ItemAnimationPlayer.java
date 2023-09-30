package com.jvn.epicaddon.api.ItemAnim;

import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.config.ConfigurationIngame;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class ItemAnimationPlayer {
    private float elapsedTime;
    private float prevElapsedTime;
    private ItemAnimation play;

    public ItemAnimationPlayer() {
        this.setPlayAnimation(ItemAnimation.DUMMY_ANIMATION);
    }


    public void update(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float ElapsedTime) {
        this.prevElapsedTime = this.elapsedTime;

    }

    public void setPlayAnimation(ItemAnimation animation) {
        this.play = animation;
    }

    public Pose getCurrentPose(ItemStack itemStack, float partialTicks) {
        return this.play.getPoseByTime(itemStack, this.prevElapsedTime + (this.elapsedTime - this.prevElapsedTime) * partialTicks, partialTicks);
    }

    public float getElapsedTime() {
        return this.elapsedTime;
    }

    public boolean isEmpty() {
        return this.play == ItemAnimation.DUMMY_ANIMATION;
    }

}
