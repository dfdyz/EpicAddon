package com.jvn.epicaddon.api.ItemAnim;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.utils.TypeFlexibleHashMap;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ItemAnimation {
    public static ItemAnimation DUMMY_ANIMATION = new ItemAnimation();
    protected Map<String, TransformSheet> jointTransforms;
    protected final boolean isRepeat;
    protected final float convertTime;
    protected float totalTime;

    public ItemAnimation() {
        this(0.15F, false);
    }

    public ItemAnimation(float convertTime, boolean isRepeat) {
        this.jointTransforms = Maps.newHashMap();
        this.totalTime = 0.0F;
        this.isRepeat = isRepeat;
        this.convertTime = convertTime;
    }

    public void addSheet(String jointName, TransformSheet sheet) {
        this.jointTransforms.put(jointName, sheet);
    }
    public final Pose getRawPose(float time) {
        Pose pose = new Pose();
        Iterator iterator = this.jointTransforms.keySet().iterator();

        while(iterator.hasNext()) {
            String jointName = (String)iterator.next();
            pose.putJointData(jointName, this.jointTransforms.get(jointName).getInterpolatedTransform(time));
        }
        return pose;
    }

    public Pose getPoseByTime(float time, float partialTicks) {
        Pose pose = new Pose();
        Iterator iterator = this.jointTransforms.keySet().iterator();

        while(iterator.hasNext()) {
            String jointName = (String)iterator.next();
            pose.putJointData(jointName, this.jointTransforms.get(jointName).getInterpolatedTransform(time));
        }
        return pose;
    }


    public void setLinkAnimation(Pose lastPose, float convertTimeModifier, LivingEntityPatch<?> entitypatch, ItemLinkAnimation dest) {
        if (!entitypatch.isLogicalClient()) {
            lastPose = DUMMY_ANIMATION.getPoseByTime(0.0F, 1.0F);
        }

        float totalTime = convertTimeModifier >= 0.0F ? convertTimeModifier + this.convertTime : this.convertTime;
        boolean isNeg = convertTimeModifier < 0.0F;
        float nextStart = isNeg ? -convertTimeModifier : 0.0F;
        if (isNeg) {
            dest.startsAt = nextStart;
        }

        dest.getTransfroms().clear();
        dest.setTotalTime(totalTime);
        dest.setNextAnimation(this);
        Map<String, JointTransform> data1 = lastPose.getJointTransformData();
        Map<String, JointTransform> data2 = this.getPoseByTime(nextStart, 1.0F).getJointTransformData();
        Iterator var10 = data1.keySet().iterator();

        while(var10.hasNext()) {
            String jointName = (String)var10.next();
            if (data1.containsKey(jointName) && data2.containsKey(jointName)) {
                Keyframe[] keyframes = new Keyframe[]{new Keyframe(0.0F, (JointTransform)data1.get(jointName)), new Keyframe(totalTime, (JointTransform)data2.get(jointName))};
                TransformSheet sheet = new TransformSheet(keyframes);
                dest.addSheet(jointName, sheet);
            }
        }

    }

    public void putOnPlayer(ItemAnimationPlayer player) {
        player.setPlayAnimation(this);
    }

    public void begin(LivingEntityPatch<?> entitypatch) {
    }

    public void tick(LivingEntityPatch<?> entitypatch) {
    }

    public void end(LivingEntityPatch<?> entitypatch, ItemAnimation nextAnimation, boolean isEnd) {
    }

    public void linkTick(LivingEntityPatch<?> entitypatch, ItemAnimation linkAnimation) {
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isJointEnabled(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        return this.jointTransforms.containsKey(joint);
    }

    @OnlyIn(Dist.CLIENT)
    public JointMask.BindModifier getBindModifier(LivingEntityPatch<?> entitypatch, Layer.Priority layer, String joint) {
        return null;
    }

    public EntityState getState(LivingEntityPatch<?> entitypatch, float time) {
        return EntityState.DEFAULT_STATE;
    }

    public TypeFlexibleHashMap<EntityState.StateFactor<?>> getStatesMap(LivingEntityPatch<?> entitypatch, float time) {
        return new TypeFlexibleHashMap(false);
    }

    public <T> T getState(EntityState.StateFactor<T> stateFactor, LivingEntityPatch<?> entitypatch, float time) {
        return stateFactor.defaultValue();
    }

    public Map<String, TransformSheet> getTransfroms() {
        return this.jointTransforms;
    }

    public float getPlaySpeed(LivingEntityPatch<?> entitypatch) {
        return 1.0F;
    }

    public TransformSheet getCoord() {
        return (TransformSheet)this.jointTransforms.get("Root");
    }

    public ItemAnimation getRealAnimation() {
        return this;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public float getTotalTime() {
        return this.totalTime - 0.001F;
    }

    public float getConvertTime() {
        return this.convertTime;
    }

    public boolean isRepeat() {
        return this.isRepeat;
    }

    public boolean canBePlayedReverse() {
        return false;
    }

    public int getNamespaceId() {
        return -1;
    }

    public int getId() {
        return -1;
    }

    public <V> Optional<V> getProperty(AnimationProperty<V> propertyType) {
        return Optional.empty();
    }

    public boolean isBasicAttackAnimation() {
        return false;
    }

    public boolean isMainFrameAnimation() {
        return false;
    }

    public boolean isReboundAnimation() {
        return false;
    }

    public boolean isMetaAnimation() {
        return false;
    }

    public boolean isClientAnimation() {
        return false;
    }

    public boolean isStaticAnimation() {
        return false;
    }

    public ItemAnimation getThis() {
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderDebugging(PoseStack poseStack, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, float playTime, float partialTicks) {
    }
}
