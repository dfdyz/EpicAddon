package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.api.playerEvent.FallAttackEvent;
import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.skills.GenShinInternal.GSFallAttack;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.JointMaskEntry;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.AttackEndEvent;

import static yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType.ATTACK_ANIMATION_END_EVENT;

public class FallAtkStartAnim extends ActionAnimation {
    public StaticAnimation Loop;
    public FallAtkStartAnim(float convertTime, String path, float loopConvertTime, float fallSpeed,String loopPath, Model model, StaticAnimation AtkAnim){
        super(convertTime, path, model);
        this.Loop = new FallAtkLoopAnim(loopConvertTime ,fallSpeed,loopPath, model, AtkAnim);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
        this.addProperty(ClientAnimationProperties.PRIORITY, Layer.Priority.HIGHEST);
        this.addProperty(ClientAnimationProperties.LAYER_TYPE, Layer.LayerType.BASE_LAYER);
    }

    @Override
    public void begin(LivingEntityPatch<?> entitypatch) {
        super.begin(entitypatch);
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().setNoGravity(true);
        if (!entitypatch.isLogicalClient()){
            if(entitypatch instanceof ServerPlayerPatch){
                ServerPlayerPatch spp = (ServerPlayerPatch) entitypatch;
                SkillContainer container = spp.getSkill(SkillCategories.AIR_ATTACK);
                if(container.getSkill() == RegEpicAddonSkills.GS_Bow_FallAttackPatch){
                    if(!container.getDataManager().hasData(GSFallAttack.FALL_STATE)){
                        container.getDataManager().registerData(GSFallAttack.FALL_STATE);
                    }
                    container.getDataManager().setData(GSFallAttack.FALL_STATE, 0);
                }
            }
        }
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        entitypatch.getOriginal().setDeltaMovement(0,0,0);
        entitypatch.getOriginal().setNoGravity(true);
    }

    @Override
    public void loadAnimation(ResourceManager resourceManager) {
        super.loadAnimation(resourceManager);
        load(resourceManager,Loop);
    }

    @Override
    public void end(LivingEntityPatch<?> entitypatch, boolean isEnd) {
        super.end(entitypatch, isEnd);
        entitypatch.getOriginal().setNoGravity(false);
        if (!entitypatch.isLogicalClient()){
            if(entitypatch instanceof ServerPlayerPatch){
                ServerPlayerPatch spp = (ServerPlayerPatch) entitypatch;
                SkillContainer container = spp.getSkill(SkillCategories.AIR_ATTACK);
                if(container.getSkill() == RegEpicAddonSkills.GS_Bow_FallAttackPatch){
                    if(!container.getDataManager().hasData(GSFallAttack.FALL_STATE)){
                        container.getDataManager().registerData(GSFallAttack.FALL_STATE);
                        container.getDataManager().setData(GSFallAttack.FALL_STATE, 0);
                    }
                    int state = container.getDataManager().getDataValue(GSFallAttack.FALL_STATE);
                    if (state == 0){
                        container.getDataManager().setData(GSFallAttack.FALL_STATE, 1);
                        entitypatch.getOriginal().setDeltaMovement(0,0,0);
                        entitypatch.getOriginal().setNoGravity(true);
                        spp.playAnimationSynchronized(Loop,0f);
                        //spp.getEventListener().triggerEvents(ATTACK_ANIMATION_END_EVENT, new FallAttackEvent(spp, entitypatch.currentlyAttackedEntity, this.getNamespaceId(),this.getId()));
                    }
                }
            }
        }
    }
    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }
}
