package com.jvn.epicaddon.skills;

import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.PassiveSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class DualSwordSkill extends PassiveSkill {
    private static float OrgStunShield = 0.0f;
    public static final UUID EVENT_UUID = UUID.fromString("39c89b89-5206-7a50-0f81-9872df158bd7");
    public DualSwordSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    public static Skill.Builder<PassiveSkill> createBuilder(ResourceLocation resourceLocation) {
        return (new Skill.Builder<PassiveSkill>(resourceLocation)).setCategory(EpicAddonSkillCategories.SAO_SINGLE_SWORD).setConsumption(0.0F).setMaxStack(0).setResource(Resource.NONE).setRequiredXp(0);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        PlayerPatch pp = container.getExecuter();
        OrgStunShield = pp.getMaxStunShield();
        pp.setMaxStunShield(180.0f);
        pp.getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            PlayerPatch Pp = event.getPlayerPatch();
            WeaponCategory weaponCategory2 = Pp.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory();
            if(weaponCategory2 == CapabilityItem.WeaponCategories.SWORD && Pp.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD){
                float maxSta =  Pp.getMaxStamina();
                float stamina = Pp.getStamina() - 1.3f;
                Pp.setStamina(stamina);
                if(stamina/maxSta >= 0.3F) {
                    Pp.setMaxStunShield(180.0f);
                    Pp.setStunShield(Pp.getMaxStunShield());
                    if(stamina/maxSta >= 0.8F){
                        event.setCanceled(true);
                        event.setResult(AttackResult.ResultType.BLOCKED);
                    }
                }
                else{
                    if(Pp.getStunShield()>OrgStunShield){
                        Pp.setStunShield(OrgStunShield);
                    }
                    Pp.setMaxStunShield(OrgStunShield);
                }

            }
            else
            {
                if(Pp.getStunShield()>OrgStunShield){
                    Pp.setStunShield(OrgStunShield);
                }
                Pp.setMaxStunShield(OrgStunShield);
            }
        }, 1);

    }
    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecuter().setMaxStunShield(OrgStunShield);
        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, 1);
    }
    @Override
    public boolean isExecutableState(PlayerPatch<?> executer) {
        return true;
    }
}
