package com.jvn.epicaddon.skills.GenShinInternal;

import com.jvn.epicaddon.EpicAddon;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;

import java.util.UUID;

public class GSBowInternal extends Skill {

    public Builder<GSBowInternal> GetBuilder(String registryName){
        return new Builder<GSBowInternal>(new ResourceLocation(EpicAddon.MODID, registryName)).setCategory(SkillCategories.WEAPON_PASSIVE);
    }

    public GSBowInternal(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }
}
