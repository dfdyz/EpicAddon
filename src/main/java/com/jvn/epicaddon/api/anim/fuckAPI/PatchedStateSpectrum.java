package com.jvn.epicaddon.api.anim.fuckAPI;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.damagesource.DamageSource;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.TypeFlexibleHashMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

public class PatchedStateSpectrum {
    private final Set<PatchedStateSpectrum.StatesInTime> timePairs = Sets.newHashSet();

    public PatchedStateSpectrum() {
    }

    public void readFrom(PatchedStateSpectrum.Blueprint blueprint) {
        this.timePairs.clear();
        this.timePairs.addAll(blueprint.timePairs);
    }

    public EntityState bindStates(float time) {
        TypeFlexibleHashMap<EntityState.StateFactor<?>> stateMap = this.getStateMap(time);
        boolean turningLocked = stateMap.getOrDefault(EntityState.TURNING_LOCKED, EntityState.TURNING_LOCKED.getDefaultVal());
        boolean movementLocked = stateMap.getOrDefault(EntityState.MOVEMENT_LOCKED, EntityState.MOVEMENT_LOCKED.getDefaultVal());
        boolean attacking = stateMap.getOrDefault(EntityState.ATTACKING, EntityState.ATTACKING.getDefaultVal());
        boolean canBasicAttack = stateMap.getOrDefault(EntityState.CAN_BASIC_ATTACK, EntityState.CAN_BASIC_ATTACK.getDefaultVal());
        boolean canSkillExecution = stateMap.getOrDefault(EntityState.CAN_SKILL_EXECUTION, EntityState.CAN_SKILL_EXECUTION.getDefaultVal());
        boolean inaction = stateMap.getOrDefault(EntityState.INACTION, EntityState.INACTION.getDefaultVal());
        boolean hurt = stateMap.getOrDefault(EntityState.HURT, EntityState.HURT.getDefaultVal());
        boolean knockdown = stateMap.getOrDefault(EntityState.KNOCKDOWN, EntityState.KNOCKDOWN.getDefaultVal());
        boolean counterAttackable = stateMap.getOrDefault(EntityState.COUNTER_ATTACKABLE, EntityState.COUNTER_ATTACKABLE.getDefaultVal());
        int phaseLevel = stateMap.getOrDefault(EntityState.PHASE_LEVEL, EntityState.PHASE_LEVEL.getDefaultVal());
        Function<DamageSource, Boolean> invulnerabilityPredicate = stateMap.getOrDefault(EntityState.INVULNERABILITY_PREDICATE, EntityState.INVULNERABILITY_PREDICATE.getDefaultVal());
        return createEntityState(turningLocked, movementLocked, attacking, canBasicAttack, canSkillExecution, inaction, hurt, knockdown, counterAttackable, phaseLevel, invulnerabilityPredicate);
    }

    private TypeFlexibleHashMap<EntityState.StateFactor<?>> getStateMap(float time) {
        TypeFlexibleHashMap<EntityState.StateFactor<?>> stateMap = new TypeFlexibleHashMap();
        Iterator var3 = this.timePairs.iterator();

        while(true) {
            PatchedStateSpectrum.StatesInTime state;
            do {
                do {
                    if (!var3.hasNext()) {
                        return stateMap;
                    }

                    state = (PatchedStateSpectrum.StatesInTime)var3.next();
                } while(!(state.start <= time));
            } while(!(state.end > time));

            Iterator var5 = state.states.iterator();

            while(var5.hasNext()) {
                Pair<EntityState.StateFactor<?>, ?> timePair = (Pair)var5.next();
                stateMap.put(timePair.getFirst(), timePair.getSecond());
            }
        }
    }

    private static EntityState createEntityState(boolean turningLocked, boolean movementLocked, boolean attacking, boolean basicAttackPossible, boolean skillExecutionPossible, boolean inaction, boolean hurt, boolean knockDown, boolean counterAttackable, int phaseLevel, Function<DamageSource, Boolean> invulnerabilityChecker) {
        Constructor entitystate;
        try {
            entitystate = EntityState.class.getDeclaredConstructor(Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Function.class);
        } catch (NoSuchMethodException var17) {
            throw new RuntimeException(var17);
        }

        entitystate.setAccessible(true);

        try {
            EntityState entityState = (EntityState)entitystate.newInstance(turningLocked, movementLocked, attacking, basicAttackPossible, skillExecutionPossible, inaction, hurt, knockDown, counterAttackable, phaseLevel, invulnerabilityChecker);
            return entityState;
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Blueprint {
        public PatchedStateSpectrum.StatesInTime currentState;
        public Set<PatchedStateSpectrum.StatesInTime> timePairs = Sets.newHashSet();

        public Blueprint() {
        }

        public PatchedStateSpectrum.Blueprint newTimePair(float start, float end) {
            this.currentState = new PatchedStateSpectrum.StatesInTime(start, end);
            this.timePairs.add(this.currentState);
            return this;
        }

        public <T> PatchedStateSpectrum.Blueprint addState(EntityState.StateFactor<T> factor, T val) {
            this.currentState.states.add(Pair.of(factor, val));
            return this;
        }

        public <T> PatchedStateSpectrum.Blueprint addStateRemoveOld(EntityState.StateFactor<T> factor, T val) {
            Iterator var3 = this.timePairs.iterator();

            while(var3.hasNext()) {
                PatchedStateSpectrum.StatesInTime timePair = (PatchedStateSpectrum.StatesInTime)var3.next();
                timePair.states.removeIf((pair) -> {
                    return ((EntityState.StateFactor)pair.getFirst()).equals(factor);
                });
            }

            return this.addState(factor, val);
        }

        public PatchedStateSpectrum.Blueprint clear() {
            this.currentState = null;
            this.timePairs.clear();
            return this;
        }
    }

    public static class StatesInTime {
        float start;
        float end;
        Set<Pair<EntityState.StateFactor<?>, ?>> states;

        public StatesInTime(float start, float end) {
            this.start = start;
            this.end = end;
            this.states = Sets.newHashSet();
        }

        public <T> PatchedStateSpectrum.StatesInTime addState(EntityState.StateFactor<T> factor, T val) {
            this.states.add(Pair.of(factor, val));
            return this;
        }
    }
}
