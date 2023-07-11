package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.renderer.SwordTrail.IAnimSTOverride;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.utils.Trail;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.ClientModels;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = StaticAnimation.class, remap = false)
public abstract class MixinAtkAnim implements IAnimSTOverride {
    protected Trail trail;
    private boolean colorOverride = false;
    private boolean posOverride = false;
    private boolean lifetimeOverride = false;
    private boolean enable = true;
    //private boolean resetGravity = true;
    //private static final StaticAnimation anim = null;
/*
    @Override
    public boolean ShouldResetGravity() {
        return resetGravity;
    }

    @Override
    public void setMode(boolean should) {
        this.resetGravity = should;
    }

 */

    @Override
    public boolean isEnable() {
        return enable;
    }
    @Override
    public void EnableST(boolean a) {
        enable = a;
    }
    @Override
    public boolean isColorOverride() {
        return colorOverride;
    }

    @Override
    public boolean isPosOverride() {
        return posOverride;
    }

    @Override
    public boolean isLifetimeOverride() {
        return lifetimeOverride;
    }

    @Override
    public Trail getTrail() {
        return trail;
    }

    @Override
    public IAnimSTOverride setColorOverride(Trail tr) {
        if(tr != null){
            colorOverride = true;
            if(trail == null){
                trail = new Trail();
            }
            this.trail.CopyColFrom(tr);
        }
        else{
            colorOverride = false;
        }
        return this;
    }

    @Override
    public IAnimSTOverride setPosOverride(Trail tr) {
        if(tr != null){
            posOverride = true;
            if(trail == null){
                trail = new Trail();
            }
            this.trail.CopyPosFrom(tr);
        }
        else{
            posOverride = false;
        }
        return this;
    }

    @Override
    public IAnimSTOverride setLifeTimeOverride(int lt) {
        if(lt > 0){
            lifetimeOverride = true;
            if(trail == null){
                trail = new Trail();
            }
            this.trail.lifetime = lt;
        }
        else{
            lifetimeOverride = false;
        }
        return this;
    }
    private StaticAnimation getAtkAnim(){
        if(namespaceId >= 0 && animationId >= 0) return EpicFightMod.getInstance().animationManager.findAnimationById(namespaceId,animationId);
        else return null;
    }
    @Shadow
    private int namespaceId;
    @Shadow
    private int animationId;
    public String prevJoint;
    @Inject(at = @At("HEAD"),method = "begin")
    private void MixinBegin(LivingEntityPatch<?> entitypatch,CallbackInfo cbi){
        if(!ClientConfig.cfg.EnableSwordTrail || !this.enable) return;
        if(entitypatch.getOriginal().getLevel().isClientSide()){
            if(entitypatch instanceof PlayerPatch || entitypatch instanceof HumanoidMobPatch){
                StaticAnimation animation = getAtkAnim();
                if(animation == null || !(animation instanceof AttackAnimation)) return;
                double eid = Double.longBitsToDouble(entitypatch.getOriginal().getId());
                double modid = Double.longBitsToDouble(animation.getNamespaceId());
                double animid = Double.longBitsToDouble(animation.getId());
                String jointID = ((AttackAnimation) animation).getPathIndexByTime(0);
                this.prevJoint = jointID;
                //System.out.println(String.format("Particle(mod=%d, anim=%d, joint=%s)",animation.getNamespaceId(),animation.getId(),jointID));
                if(jointID == "Tool_R" || jointID == "Tool_L"){
                    double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex(jointID));
                    entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, jointID == "Tool_R" ? 1:-1, 0);
                }
                else {
                    double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_R"));
                    entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, 1, 0);
                    jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_L"));
                    entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, -1, 0);
                }
            }
        }
    }

    @Inject(at = @At("HEAD"),method = "tick")
    private void MixinTick(LivingEntityPatch<?> entitypatch, CallbackInfo cbi){
        if(!ClientConfig.cfg.EnableSwordTrail || !this.enable) return;
        if(entitypatch.getOriginal().getLevel().isClientSide()){
            if(entitypatch instanceof PlayerPatch || entitypatch instanceof HumanoidMobPatch){
                StaticAnimation animation = getAtkAnim();
                if(animation == null || !(animation instanceof AttackAnimation)) return;
                String jointID = ((AttackAnimation) animation).getPathIndexByTime(entitypatch.getClientAnimator().getPlayerFor(animation).getElapsedTime());
                if(jointID != prevJoint){
                    double eid = Double.longBitsToDouble(entitypatch.getOriginal().getId());
                    double modid = Double.longBitsToDouble(animation.getNamespaceId());
                    double animid = Double.longBitsToDouble(animation.getId());
                    //System.out.println(String.format("Particle 2(mod=%d, anim=%d, joint=%s)",animation.getNamespaceId(),animation.getId(),jointID));
                    if(jointID == "Tool_R" || jointID == "Tool_L"){
                        double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature().searchPathIndex(jointID));
                        entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, jointID == "Tool_R" ? -1:1, 0);
                    }
                    else {
                        double jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_R"));
                        entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, -1, 0);
                        jointId = Double.longBitsToDouble(entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature().searchPathIndex("Tool_L"));
                        entitypatch.getOriginal().level.addParticle(RegParticle.BLADE_TRAIL.get(), eid, modid, animid, jointId, 1, 0);
                    }
                }
                this.prevJoint = jointID;
            }
        }
    }
}
