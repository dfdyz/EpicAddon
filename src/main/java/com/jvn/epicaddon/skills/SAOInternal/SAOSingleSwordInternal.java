package com.jvn.epicaddon.skills.SAOInternal;

import com.jvn.epicaddon.register.RegEpicAddonSkills;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import net.minecraft.network.FriendlyByteBuf;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.SkillDataManager.SkillDataKey;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class SAOSingleSwordInternal extends Skill {

    public static final SkillDataKey<Boolean> LOCKED = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.BOOLEAN);
    private static final UUID EVENT_UUID = UUID.fromString("ce7c276b-ab8b-0955-713f-93347cdce622");

    public SAOSingleSwordInternal(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        container.getExecuter().getSkillCapability().skillContainers[SkillCategories.BASIC_ATTACK.universalOrdinal()].setSkill(RegEpicAddonSkills.SAOBasicAtkPatch);

        container.getDataManager().registerData(LOCKED);
        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.BASIC_ATTACK_EVENT, EVENT_UUID,
                (event) ->
                {
                    //System.out.println("Fffffff");
                    ServerPlayerPatch playerPatch = event.getPlayerPatch();
                    float staminaRate = playerPatch.getStamina()/playerPatch.getMaxStamina();

                    if(playerPatch.getSkill(EpicAddonSkillCategories.SAO_SINGLE_SWORD).getSkill() == RegEpicAddonSkills.SAO_DUALSWORD){
                        container.getDataManager().setData(LOCKED, staminaRate < 0.5f);
                    }
                });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.ACTION_EVENT_SERVER, EVENT_UUID);
        container.getExecuter().getSkillCapability().skillContainers[SkillCategories.BASIC_ATTACK.universalOrdinal()].setSkill(Skills.BASIC_ATTACK);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnServer(executer, args);
    }
}
