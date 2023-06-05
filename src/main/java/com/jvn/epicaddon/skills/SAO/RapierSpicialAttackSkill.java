package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.skills.IMutiSpecialSkill;
import com.jvn.epicaddon.skills.SAOInternal.MutiSpecialSkill;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;

public class RapierSpicialAttackSkill extends SpecialAttackSkill implements IMutiSpecialSkill {
    private final ArrayList<ResourceLocation> childSkills = new ArrayList<>();
    private final ArrayList<ResourceLocation> childSkills2 = new ArrayList<>();
    private final StaticAnimation Normal;
    private final StaticAnimation OnRun;

    public RapierSpicialAttackSkill(Builder builder) {
        super(builder);
        this.Normal = builder.attackAnimation;
        this.OnRun = builder.attackAnimation2;
        ResourceLocation name = this.getRegistryName();
        //ResourceLocation tex = new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png");
        childSkills.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/sao_rapier_skill.png"));
        childSkills.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
        childSkills2.add(new ResourceLocation(name.getNamespace(), "textures/gui/skills/" + name.getPath() + ".png"));
    }

    public static Builder createBuilder(ResourceLocation resourceLocation) {
        return (new Builder(resourceLocation)).setCategory(SkillCategories.WEAPON_SPECIAL_ATTACK).setResource(Resource.SPECIAL_GAUAGE);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability()
                .skillContainers[EpicAddonSkillCategories.MutiSpecialAttack.universalOrdinal()]
                .setSkill(RegEpicAddonSkills.MUTI_SPECIAL_ATTACK);
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
            int selected = executer.getSkill(EpicAddonSkillCategories.MutiSpecialAttack).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);

            if(!executer.getOriginal().isSprinting()){
                ok = skillContainer.getStack() > (selected == 0 ? 1:0);
            }
            else{
                ok = skillContainer.getStack() > 0;
            }

            return ok || (executer.getOriginal()).isCreative();
        } else {
            return executer.getHoldingItemCapability(InteractionHand.MAIN_HAND).getSpecialAttack(executer) == this && (executer.getOriginal()).getVehicle() == null && (!executer.getSkill(this.category).isActivated() || this.activateType == ActivateType.TOGGLE);
        }
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillContainer skill = executer.getSkill(this.category);
        int selected = executer.getSkill(EpicAddonSkillCategories.MutiSpecialAttack).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);


        if (executer.getOriginal().isSprinting()){
            executer.playAnimationSynchronized(this.OnRun, 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 1);
        }
        else {
            if(selected == 0){
                executer.playAnimationSynchronized(this.Normal, 0.0F);
                this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 2);
            }
            else {
                executer.playAnimationSynchronized(this.OnRun, 0.0F);
                this.setStackSynchronize(executer, executer.getSkill(this.category).getStack() - 1);
            }
        }
        this.setDurationSynchronize(executer, this.maxDuration);
        skill.activate();
    }

    @Override
    public ArrayList<ResourceLocation> getSkillTextures(PlayerPatch<?> executer) {
        if(executer.getOriginal().isSprinting()){
            return childSkills2;
        }
        return childSkills;
    }

    @Override
    public boolean isSkillActive(PlayerPatch<?> executer, int idx) {
        boolean c = executer.getOriginal().isCreative();
        if(!executer.getOriginal().isSprinting()){
            if(idx == 0){
                return (executer.getSkill(this.category).getStack() > 1 || c);
            }
            return (executer.getSkill(this.category).getStack() > 0 || c);
        }
        else{
            return (executer.getSkill(this.category).getStack() > 0 || c);
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
