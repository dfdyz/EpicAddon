package com.jvn.epicaddon.api.playerEvent;

import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.AttackEndEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;

public class FallAttackEvent extends AttackEndEvent {
    private int animNamespace;

    public FallAttackEvent(ServerPlayerPatch playerpatch, List<LivingEntity> attackedEntity, int animNamespace, int animationId) {
        super(playerpatch,attackedEntity,animationId);
        this.animNamespace = animNamespace;
    }
    public int getAnimNamespace(){
        return animNamespace;
    }
}
