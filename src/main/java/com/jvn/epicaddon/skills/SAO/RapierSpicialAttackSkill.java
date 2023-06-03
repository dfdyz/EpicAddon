package com.jvn.epicaddon.skills.SAO;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class RapierSpicialAttackSkill extends SpecialAttackSkill {
    private final StaticAnimation Normal;
    private final StaticAnimation OnRun;

    public RapierSpicialAttackSkill(Builder builder) {
        super(builder);
        this.Normal = builder.attackAnimation;
        this.OnRun = builder.attackAnimation2;
    }


    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return (new Builder(resourceLocation)).setCategory(SkillCategories.WEAPON_SPECIAL_ATTACK).setResource(Resource.SPECIAL_GAUAGE);
    }

    @Override
    public SpecialAttackSkill registerPropertiesToAnimation() {
        return null;
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            boolean ok = false;
            SkillContainer skillContainer = executer.getSkill(this.category);
            if(executer.getOriginal().isSprinting()){
                ok = skillContainer.getStack() > 0;
            }
            else{
                ok = skillContainer.getStack() > 1;
            }

            return ok || (executer.getOriginal()).isCreative();
        } else {
            return executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getSpecialAttack(executer) == this && (executer.getOriginal()).getVehicle() == null && (!executer.getSkill(this.category).isActivated() || this.activateType == ActivateType.TOGGLE);
        }
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {

        SkillContainer skill = executer.getSkill(this.category);

        if (executer.getOriginal().isSprinting()){
            executer.playAnimationSynchronized(this.OnRun, 0.0F);

            this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 1);
            this.setDurationSynchronize(executer, this.maxDuration);
            skill.activate();
        }
        else {
            executer.playAnimationSynchronized(this.Normal, 0.0F);

            this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 2);
            this.setDurationSynchronize(executer, this.maxDuration);
            skill.activate();
        }

    }


    public static class Builder extends Skill.Builder<RapierSpicialAttackSkill> {
        protected StaticAnimation attackAnimation;
        protected StaticAnimation attackAnimation2;

        public Builder(ResourceLocation resourceLocation) {
            super(resourceLocation);
            this.maxStack = 3;
        }

        public Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public Builder setConsumption(float consumption) {
            this.consumption = consumption;
            return this;
        }

        public Builder setMaxDuration(int maxDuration) {
            this.maxDuration = maxDuration;
            return this;
        }

        public Builder setMaxStack(int maxStack) {
            this.maxStack = maxStack;
            return this;
        }

        public Builder setRequiredXp(int requiredXp) {
            this.requiredXp = requiredXp;
            return this;
        }

        public Builder setActivateType(Skill.ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }

        public Builder setResource(Skill.Resource resource) {
            this.resource = resource;
            return this;
        }

        public Builder setAnimation(StaticAnimation attackAnimation) {
            this.attackAnimation = attackAnimation;
            return this;
        }
        public Builder setAnimation2(StaticAnimation attackAnimation) {
            this.attackAnimation2 = attackAnimation;
            return this;
        }
    }
}
