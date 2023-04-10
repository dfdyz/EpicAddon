package com.jvn.epicaddon.skills.GenShinInternal;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.SimpleSpecialAttackSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.BasicAttackEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;
import java.util.UUID;

public class GSSpecialAttack extends SimpleSpecialAttackSkill {
    public GSSpecialAttack(Builder builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            return (executer.getSkill(this.getCategory()).isReady() || ((Player)executer.getOriginal()).isCreative())
                    && !(executer.isUnstable())
                    && Math.abs(executer.getOriginal().getDeltaMovement().y) <= 0.3f
                    && closedGround(executer);
        } else {
            return executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getSpecialAttack(executer) == this
                    && ((Player)executer.getOriginal()).getVehicle() == null
                    && (!executer.getSkill(this.category).isActivated() || this.activateType == ActivateType.TOGGLE)
                    && !(executer.isUnstable())
                    && Math.abs(executer.getOriginal().getDeltaMovement().y) <= 0.3f
                    && closedGround(executer);
        }
    }

    private boolean closedGround(PlayerPatch<?> executer){
        Vec3 epos = executer.getOriginal().position();
        ClipContext clipContext = new ClipContext(epos, epos.add(0,-2,0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, executer.getOriginal());
        Level level = executer.getOriginal().level;
        BlockHitResult result = level.clip(clipContext);
        return result.getType() == HitResult.Type.BLOCK;
    }
}
