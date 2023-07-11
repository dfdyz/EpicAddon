package com.jvn.epicaddon.skills.GenShinInternal;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.register.RegEpicAddonSkills;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GSBowInternal extends Skill {
    public static Builder<GSBowInternal> GetBuilder(String registryName){
        return new Builder<GSBowInternal>(new ResourceLocation(EpicAddon.MODID, registryName)).setCategory(SkillCategories.WEAPON_PASSIVE);
    }

    public GSBowInternal(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability().skillContainers[SkillCategories.AIR_ATTACK.universalOrdinal()].setSkill(RegEpicAddonSkills.GS_Bow_FallAttackPatch);
        container.getExecuter().getSkillCapability().skillContainers[SkillCategories.BASIC_ATTACK.universalOrdinal()].setSkill(RegEpicAddonSkills.GS_Bow_BasicAttackPatch);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getSkillCapability().skillContainers[SkillCategories.AIR_ATTACK.universalOrdinal()].setSkill(Skills.AIR_ATTACK);
        container.getExecuter().getSkillCapability().skillContainers[SkillCategories.BASIC_ATTACK.universalOrdinal()].setSkill(Skills.BASIC_ATTACK);

    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
    }
}
