package com.jvn.epicaddon.api.anim;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class FallAtkFinalAnim extends BasicAttackAnimation {
    public FallAtkFinalAnim(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, contact, recovery, collider, index, path, model);
    }

    public FallAtkFinalAnim(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public FallAtkFinalAnim(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, contact, recovery, hand, collider, index, path, model);
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
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        if(entitypatch.isLogicalClient()){
            if(entitypatch.getOriginal() == Minecraft.getInstance().player){
                ClientEngine.instance.renderEngine.unlockRotation(Minecraft.getInstance().cameraEntity);
            }
        }
    }
}
