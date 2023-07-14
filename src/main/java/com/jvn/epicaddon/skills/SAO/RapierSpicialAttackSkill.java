package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.resources.EpicAddonSkillSlots;
import com.jvn.epicaddon.skills.IMutiSpecialSkill;
import com.jvn.epicaddon.skills.SAOInternal.MutiSpecialSkill;
import com.jvn.epicaddon.utils.SkillUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;

public class RapierSpicialAttackSkill extends WeaponInnateSkill implements IMutiSpecialSkill {
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
        return (new Builder(resourceLocation)).setCategory(SkillSlots.WEAPON_INNATE.category()).setResource(Skill.Resource.STAMINA);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability()
                .skillContainers[EpicAddonSkillSlots.SKILL_SELECTOR.universalOrdinal()]
                .setSkill(RegEpicAddonSkills.MUTI_SPECIAL_ATTACK);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            boolean ok = false;
            SkillContainer skillContainer = executer.getSkill(SkillSlots.WEAPON_INNATE);
            int selected = executer.getSkill(EpicAddonSkillSlots.SKILL_SELECTOR).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);

            if(!executer.getOriginal().isSprinting()){
                ok = skillContainer.getStack() > (selected == 0 ? 1:0);
            }
            else{
                ok = skillContainer.getStack() > 0;
            }

            return ok || (executer.getOriginal()).isCreative();
        } else {
            return SkillUtils.getMainHandSkill(executer) == this && (executer.getOriginal()).getVehicle() == null && (!executer.getSkill(SkillSlots.WEAPON_INNATE).isActivated() || this.activateType == ActivateType.TOGGLE);
        }
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }


    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        SkillContainer skill = executer.getSkill(SkillSlots.WEAPON_INNATE);
        int selected = executer.getSkill(EpicAddonSkillSlots.SKILL_SELECTOR).getDataManager().getDataValue(MutiSpecialSkill.CHILD_SKILL_INDEX);


        if (executer.getOriginal().isSprinting()){
            executer.playAnimationSynchronized(this.OnRun, 0.0F);
            this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
        }
        else {
            if(selected == 0){
                executer.playAnimationSynchronized(this.Normal, 0.0F);
                this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 2);
            }
            else {
                executer.playAnimationSynchronized(this.OnRun, 0.0F);
                this.setStackSynchronize(executer, executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() - 1);
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
                return (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() > 1 || c);
            }
            return (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() > 0 || c);
        }
        else{
            return (executer.getSkill(SkillSlots.WEAPON_INNATE).getStack() > 0 || c);
        }
    }


    public static class Builder extends Skill.Builder<RapierSpicialAttackSkill> {
        protected StaticAnimation attackAnimation;
        protected StaticAnimation attackAnimation2;

        public Builder(ResourceLocation resourceLocation) {
            super();
            this.registryName = resourceLocation;
            //this.maxStack = 3;
        }

        public Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public Builder setMaxDuration(int maxDuration) {
            //this.maxDuration = maxDuration;
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
