package com.jvn.epicaddon.skills;

import com.google.common.collect.Lists;
import com.jvn.epicaddon.resources.EpicAddonStyles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.GuardSkill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;

public class SingleSwordGuardSkill extends GuardSkill {

    public static GuardSkill.Builder createBuilder(ResourceLocation resourceLocation) {
        return GuardSkill.createBuilder(resourceLocation)
                .addAdvancedGuardMotion(CapabilityItem.WeaponCategories.SWORD,
                        (itemCap, playerpatch) ->

                        {
                            Style style = itemCap.getStyle(playerpatch);
                            if (style == EpicAddonStyles.SAO_SINGLE_SWORD){
                                return new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 };
                            }
                            else if (style == EpicAddonStyles.SAO_DUAL_SWORD){
                                return new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 };
                            }

                            //def
                            return new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 };
                        });

    }

    public SingleSwordGuardSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        container.getDataManager().registerData(LAST_HIT_TICK);
        container.getDataManager().registerData(PENALTY);

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.CLIENT_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND);

            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(InteractionHand.MAIN_HAND);
            }
        });

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_USE_EVENT, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(InteractionHand.MAIN_HAND);

            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && this.isExecutableState(event.getPlayerPatch())) {
                event.getPlayerPatch().getOriginal().startUsingItem(InteractionHand.MAIN_HAND);
            }
        });

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.SERVER_ITEM_STOP_EVENT, EVENT_UUID, (event) -> {
            ServerPlayer serverplayerentity = event.getPlayerPatch().getOriginal();
            container.getDataManager().setDataSync(LAST_HIT_TICK, serverplayerentity.tickCount, serverplayerentity);
        });

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_POST, EVENT_UUID, (event) -> {
            container.getDataManager().setDataSync(PENALTY, 0.0F, event.getPlayerPatch().getOriginal());
        });

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.HURT_EVENT_PRE, EVENT_UUID, (event) -> {
            CapabilityItem itemCapability = event.getPlayerPatch().getHoldingItemCapability(event.getPlayerPatch().getOriginal().getUsedItemHand());

            if (this.isHoldingWeaponAvailable(event.getPlayerPatch(), itemCapability, BlockType.GUARD) && event.getPlayerPatch().getOriginal().isUsingItem() && this.isExecutableState(event.getPlayerPatch())) {
                DamageSource damageSource = event.getDamageSource();
                boolean isFront = false;
                Vec3 sourceLocation = damageSource.getSourcePosition();

                if (sourceLocation != null) {
                    Vec3 viewVector = event.getPlayerPatch().getOriginal().getViewVector(1.0F);
                    Vec3 toSourceLocation = sourceLocation.subtract(event.getPlayerPatch().getOriginal().position()).normalize();

                    if (toSourceLocation.dot(viewVector) > 0.0D) {
                        isFront = true;
                    }
                }

                if (isFront) {
                    float impact = 0.1F;
                    float knockback = 0.1F;

                    if (event.getDamageSource() instanceof ExtendedDamageSource) {
                        impact = ((ExtendedDamageSource)event.getDamageSource()).getImpact();
                        knockback += Math.min(impact * 0.1F, 1.0F);
                    }

                    this.guard(container, itemCapability, event, knockback, impact, false);
                }
            }
        }, 1);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public List<Object> getTooltipArgs() {
        List<Object> list = Lists.newArrayList();
        list.add(String.format("%s ",CapabilityItem.WeaponCategories.SWORD).toLowerCase());
        return list;
    }


}
