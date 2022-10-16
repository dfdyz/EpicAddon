package com.jvn.epicaddon.capabilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

//W.I.P.
public class LockableWeaponCap extends WeaponCapability {

    protected final Map<Style, Map<Style,List<StaticAnimation>>> LockedAutoAttackMotions;
    protected Map<Style, Map<Style,StaticAnimation>> ChildSpecialAttack;
    protected final Map<Style, Function<LivingEntityPatch<?>, Style>> LockedProvider;

    public LockableWeaponCap(Builder builder) {
        super(builder);
        LockedAutoAttackMotions = builder.LockedAutoAttackMotions;
        LockedProvider = builder.LockedProvider;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WeaponCapability.Builder {
        public final Map<Style, Map<Style,List<StaticAnimation>>> LockedAutoAttackMotions = Maps.newHashMap();
        public final Map<Style, Map<Style,StaticAnimation>> ChildSpecialAttack = Maps.newHashMap();
        public final Map<Style, Function<LivingEntityPatch<?>, Style>> LockedProvider = Maps.newHashMap();
        protected Builder(){
            super();
        }

        public LockableWeaponCap.Builder ChildStyleCombo(Style ParentStyle, Style ChildStyle, StaticAnimation... animation) {
            if(LockedAutoAttackMotions.get(ParentStyle) == null){
                LockedAutoAttackMotions.put(ParentStyle, Maps.newHashMap());
            }
            LockedAutoAttackMotions.get(ParentStyle).put(ChildStyle, Lists.newArrayList(animation));
            return this;
        }

        public LockableWeaponCap.Builder ChildSpecialAttack(Style ParentStyle, Style ChildStyle, StaticAnimation animation) {
            if(ChildSpecialAttack.get(ParentStyle) == null){
                ChildSpecialAttack.put(ParentStyle,Maps.newHashMap());
            }
            ChildSpecialAttack.get(ParentStyle).put(ChildStyle, animation);
            return this;
        }

        public LockableWeaponCap.Builder category(WeaponCategory category) {
            super.category(category);
            return this;
        }

        public LockableWeaponCap.Builder styleProvider(Function<LivingEntityPatch<?>, Style> styleProvider) {
            super.styleProvider(styleProvider);
            return this;
        }

        public LockableWeaponCap.Builder ChildStyleProvider(Style ParentStyle, Function<LivingEntityPatch<?>, Style> Provider) {
            LockedProvider.put(ParentStyle, Provider);
            return this;
        }

        public LockableWeaponCap.Builder passiveSkill(Skill passiveSkill) {
            super.passiveSkill(passiveSkill);
            return this;
        }

        public LockableWeaponCap.Builder swingSound(SoundEvent swingSound) {
            super.swingSound(swingSound);
            return this;
        }

        public LockableWeaponCap.Builder hitSound(SoundEvent hitSound) {
            super.hitSound(hitSound);
            return this;
        }

        public LockableWeaponCap.Builder collider(Collider collider) {
            super.collider(collider);
            return this;
        }

        public LockableWeaponCap.Builder canBePlacedOffhand(boolean canBePlacedOffhand) {
            super.canBePlacedOffhand(canBePlacedOffhand);
            return this;
        }

        public LockableWeaponCap.Builder livingMotionModifier(Style wieldStyle, LivingMotion livingMotion, StaticAnimation animation) {
            super.livingMotionModifier(wieldStyle,livingMotion,animation);
            return this;
        }

        public LockableWeaponCap.Builder addStyleAttibutes(Style style, Pair<Attribute, AttributeModifier> attributePair) {
            super.addStyleAttibutes(style, attributePair);
            return this;
        }

        public LockableWeaponCap.Builder newStyleCombo(Style style, StaticAnimation... animation) {
            super.newStyleCombo(style, animation);
            return this;
        }

        public LockableWeaponCap.Builder weaponCombinationPredicator(Function<LivingEntityPatch<?>, Boolean> predicator) {
            super.weaponCombinationPredicator(predicator);
            return this;
        }

        public LockableWeaponCap.Builder specialAttack(Style style, Skill specialAttack) {
            super.specialAttack(style, specialAttack);
            return this;
        }

    }
}
