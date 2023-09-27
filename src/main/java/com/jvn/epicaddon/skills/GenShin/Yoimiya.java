package com.jvn.epicaddon.skills.GenShin;

import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.Skill;

public class Yoimiya extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public Yoimiya(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Skill.Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
        return (new Skill.Builder<PassiveSkill>()).setCategory(EpicAddonSkillCategories.GEN_SHIN_IMPACT_BOW).setResource(Skill.Resource.NONE);
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack poseStack, float x, float y) {

    }
}