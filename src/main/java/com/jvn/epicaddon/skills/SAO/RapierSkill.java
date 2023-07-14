package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

public class RapierSkill extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public RapierSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
        return (new Builder<PassiveSkill>()).setCategory(EpicAddonSkillCategories.SAO_SINGLE_SWORD).setRegistryName(resourceLocation).setResource(Resource.NONE).setActivateType(ActivateType.PASSIVE);
    }


    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerPatch pp = container.getExecuter();
        pp.getSkillCapability().addLearnedSkill(RegEpicAddonSkills.SAO_SINGLESWORD);
    }
}
