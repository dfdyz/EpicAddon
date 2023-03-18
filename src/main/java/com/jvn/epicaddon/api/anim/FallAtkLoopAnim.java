package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.api.anim.fuckAPI.StateSpectrumUtils;
import com.jvn.epicaddon.utils.JointMasks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.JointMaskEntry;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class FallAtkLoopAnim extends ActionAnimation {
    private StaticAnimation atk;
    private final float fallSpeed;
    public FallAtkLoopAnim(float convertTime,float fallSpeed, String path, Model model, StaticAnimation atk){
        super(convertTime ,path,model);
        this.atk = atk;
        this.fallSpeed = fallSpeed;
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(ClientAnimationProperties.PRIORITY, Layer.Priority.HIGHEST);
        //this.addProperty(ClientAnimationProperties.LAYER_TYPE, Layer.LayerType.COMPOSITE_LAYER);
        this.addProperty(ClientAnimationProperties.JOINT_MASK,
                JointMaskEntry.builder().defaultMask(JointMasks.ALL).mask(LivingMotions.FALL ,JointMasks.ALL).create());
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        if(!entitypatch.isLogicalClient()){
            if(shouldAtk(entitypatch)){
                entitypatch.playAnimationSynchronized(atk,0);
            }
        }
        else {
            if(entitypatch.getOriginal() == Minecraft.getInstance().player){
                ClientEngine.instance.renderEngine.unlockRotation(Minecraft.getInstance().cameraEntity);
            }
        }
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().move(MoverType.SELF, new Vec3(0,-2,0));
    }

    @Override
    public void linkTick(LivingEntityPatch<?> entitypatch, LinkAnimation linkAnimation) {
        super.linkTick(entitypatch, linkAnimation);
        if(entitypatch.isLogicalClient()){
            if(entitypatch.getOriginal() == Minecraft.getInstance().player){
                ClientEngine.instance.renderEngine.unlockRotation(Minecraft.getInstance().cameraEntity);
            }
        }
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, boolean isEnd) {
        super.end(entitypatch, isEnd);
        entitypatch.getOriginal().setDeltaMovement(0,-2,0);
    }

    @Override
    protected void modifyPose(Pose pose, LivingEntityPatch<?> entitypatch, float time) {

    }

    private boolean shouldAtk(LivingEntityPatch<?> entitypatch){
        Vec3 epos = entitypatch.getOriginal().position();
        ClipContext clipContext = new ClipContext(epos, epos.add(0,-3.3,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entitypatch.getOriginal());
        Level level = entitypatch.getOriginal().level;
        BlockHitResult result = level.clip(clipContext);
        return result.getType() == HitResult.Type.BLOCK;
    }

    @Override
    public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        float patchedTime = time % getTotalTimeReal();
        return super.getPoseByTime(entitypatch, patchedTime, partialTicks);
    }

    private float getTotalTimeReal(){
        return super.getTotalTime();
    }

    @Override
    public float getTotalTime() {
        return Float.MAX_VALUE;
    }
}
