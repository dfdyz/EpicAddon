package com.jvn.epicaddon.api.ItemAnim;

import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class ItemAnimator {
    protected ItemAnimation nextAnimation;
    protected final ItemLinkAnimation linkAnimation;
    protected final ItemConcurrentLinkAnimation concurrentLinkAnimation;
    public final ItemAnimationPlayer animationPlayer;

    protected boolean disabled;
    protected boolean paused;

    public ItemAnimator(){
        this.animationPlayer = new ItemAnimationPlayer();
        this.linkAnimation = new ItemLinkAnimation();
        this.concurrentLinkAnimation = new ItemConcurrentLinkAnimation();
        this.disabled = true;
    }

    public void playAnimation(ItemAnimation nextAnimation, LivingEntityPatch<?> entitypatch, float convertTimeModifier) {
        Pose lastPose = entitypatch.getArmature().getPose(1.0F);

        this.animationPlayer.getAnimation().end(entitypatch, nextAnimation, this.animationPlayer.isEnd());
        this.resume();
        nextAnimation.begin(entitypatch);

        if (!nextAnimation.isMetaAnimation()) {
            this.setLinkAnimation(nextAnimation, entitypatch, lastPose, convertTimeModifier);
            this.linkAnimation.putOnPlayer(this.animationPlayer);
            entitypatch.updateEntityState();
            this.nextAnimation = nextAnimation;
        }
    }

    public void update(LivingEntityPatch<?> entitypatch, DynamicAnimation entityAnim) {
        if (this.paused) {
            this.animationPlayer.setElapsedTime(this.animationPlayer.getElapsedTime());
        } else {
            this.animationPlayer.update(entitypatch, entityAnim);
        }

        this.animationPlayer.getAnimation().tick(entitypatch);

        if (!this.paused && this.animationPlayer.isEnd()) {
            if (this.nextAnimation != null) {
                this.animationPlayer.getAnimation().end(entitypatch, this.nextAnimation, true);

                if (!(this.animationPlayer.getAnimation() instanceof ItemLinkAnimation) && !(this.nextAnimation instanceof ItemLinkAnimation)) {
                    this.nextAnimation.begin(entitypatch);
                }

                this.nextAnimation.putOnPlayer(this.animationPlayer);
                this.nextAnimation = null;
            }
        }
    }

    protected void setLinkAnimation(ItemAnimation nextAnimation, LivingEntityPatch<?> entitypatch, Pose lastPose, float convertTimeModifier) {
        nextAnimation.setLinkAnimation(lastPose, convertTimeModifier, entitypatch, this.linkAnimation);
    }

    public void playAnimationInstant(ItemAnimation nextAnimation, LivingEntityPatch<?> entitypatch) {
        this.animationPlayer.getAnimation().end(entitypatch, nextAnimation, this.animationPlayer.isEnd());
        this.resume();
        nextAnimation.begin(entitypatch);
        nextAnimation.putOnPlayer(this.animationPlayer);
        entitypatch.updateEntityState();
        this.nextAnimation = null;
    }

    protected void playLivingAnimation(ItemAnimation nextAnimation, LivingEntityPatch<?> entitypatch) {
        this.animationPlayer.getAnimation().end(entitypatch, nextAnimation, this.animationPlayer.isEnd());
        this.resume();
        nextAnimation.begin(entitypatch);

        if (!nextAnimation.isMetaAnimation()) {
            this.concurrentLinkAnimation.acceptFrom(this.animationPlayer.getAnimation().getRealAnimation(), nextAnimation, this.animationPlayer.getElapsedTime());
            this.concurrentLinkAnimation.putOnPlayer(this.animationPlayer);
            entitypatch.updateEntityState();
            this.nextAnimation = nextAnimation;
        }
    }

    public Pose getPose(ItemStack itemStack, float partialTick) {
        Pose pose = this.animationPlayer.getCurrentPose(itemStack, partialTick);
        return pose;

    }

    public void pause() {
        this.paused = true;
    }

    public void resume() {
        this.paused = false;
        this.disabled = false;
    }

    protected boolean isDisabled() {
        return this.disabled;
    }

}
