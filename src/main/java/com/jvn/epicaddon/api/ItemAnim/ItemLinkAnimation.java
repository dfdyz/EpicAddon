package com.jvn.epicaddon.api.ItemAnim;


import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.utils.TypeFlexibleHashMap;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Iterator;
import java.util.Map;

public class ItemLinkAnimation extends ItemAnimation {
    protected ItemAnimation nextAnimation;
    protected float startsAt;

    public ItemLinkAnimation() {
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

    public TypeFlexibleHashMap<EntityState.StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        return this.nextAnimation.getStatesMap(entitypatch, time);
    }

    public EntityState getState(LivingEntityPatch<?> entitypatch, float time) {
        return this.nextAnimation.getState(entitypatch, 0.0F);
    }

    public <T> T getState(EntityState.StateFactor<T> stateFactor, LivingEntityPatch<?> entitypatch, float time) {
        return this.nextAnimation.getState(stateFactor, entitypatch, 0.0F);
    }

    @Override
    public Pose getPoseByTime(ItemStack itemStack, float time, float partialTicks) {
        Pose nextStartingPose = this.nextAnimation.getPoseByTime(itemStack, this.startsAt, 1.0F);
        Iterator var5 = nextStartingPose.getJointTransformData().entrySet().iterator();
        while(var5.hasNext()) {
            Map.Entry<String, JointTransform> entry = (Map.Entry)var5.next();
            if (this.jointTransforms.containsKey(entry.getKey())) {
                Keyframe[] keyframe = ((TransformSheet)this.jointTransforms.get(entry.getKey())).getKeyframes();
                JointTransform jt = keyframe[keyframe.length - 1].transform();
                JointTransform newJt = (JointTransform)nextStartingPose.getJointTransformData().get(entry.getKey());
                newJt.translation().set(jt.translation());
                jt.copyFrom(newJt);
            }
        }

        return super.getPoseByTime(itemStack, time, partialTicks);
    }

    public float getPlaySpeed(LivingEntityPatch<?> entitypatch) {
        return this.nextAnimation.getPlaySpeed(entitypatch);
    }

    public void setNextAnimation(ItemAnimation animation) {
        this.nextAnimation = animation;
    }

    public ItemAnimation getNextAnimation() {
        return this.nextAnimation;
    }

    public boolean isJointEnabled(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        return this.nextAnimation.isJointEnabled(entitypatch, layer, joint);
    }

    public JointMask.BindModifier getBindModifier(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        return this.nextAnimation.getBindModifier(entitypatch, layer, joint);
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

    public void copyTo(ItemLinkAnimation linkAnimation) {
        linkAnimation.setNextAnimation(this.nextAnimation);
        linkAnimation.totalTime = this.totalTime;
        Map<String, TransformSheet> trnasforms = linkAnimation.getTransfroms();
        trnasforms.clear();
        trnasforms.putAll(this.getTransfroms());
    }

    public String toString() {
        return "LinkAnimation " + this.nextAnimation;
    }
    
}
