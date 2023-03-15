package com.jvn.epicaddon.skills.SAO;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.PassiveSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class RapierSkill extends PassiveSkill {
    //private static float OrgStunShield = 0.0f;
    //public static final UUID EVENT_UUID = UUID.fromString("5d267390-b46f-41d4-940d-a1b2fb2481bd");
    public RapierSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Skill.Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
        return (new Skill.Builder<PassiveSkill>(resourceLocation)).setCategory(EpicAddonSkillCategories.SAO_SINGLE_SWORD).setConsumption(0.0F).setMaxStack(0).setResource(Resource.NONE).setRequiredXp(0);
    }

}
