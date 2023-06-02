package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.mobeffects.MobEffectUtils;
import com.jvn.epicaddon.mixin.PhaseAccessor;
import com.jvn.epicaddon.register.RegMobEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.DashAttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.effect.EpicFightMobEffects;
import yesman.epicfight.world.entity.eventlistener.DealtDamageEvent;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.List;

public class SpecailDashAtkAnimation extends DashAttackAnimation {
    public SpecailDashAtkAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public SpecailDashAtkAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, boolean noDirectionAttack, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, noDirectionAttack, model);
    }


    @Override
    public void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        Collider collider = this.getCollider(entitypatch, elapsedTime);
        LivingEntity entity = entitypatch.getOriginal();
        entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature().initializeTransform();
        float prevPoseTime = prevState.attacking() ? prevElapsedTime : ((PhaseAccessor)phase).getPreDelay();
        float poseTime = state.attacking() ? elapsedTime : ((PhaseAccessor)phase).getContact();
        List<Entity> list = collider.updateAndSelectCollideEntity(entitypatch, this, prevPoseTime, poseTime, phase.getColliderJointName(), this.getPlaySpeed(entitypatch));

        if (list.size() > 0) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, ((PhaseAccessor)phase).getPropertyInvoker(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY).orElse(HitEntityList.Priority.DISTANCE));
            boolean flag1 = true;
            int maxStrikes = this.getMaxStrikes(entitypatch, phase);
            entitypatch.getOriginal().setLastHurtMob(list.get(0));

            while (entitypatch.currentlyAttackedEntity.size() < maxStrikes && hitEntities.next()) {
                Entity e = hitEntities.getEntity();
                LivingEntity trueEntity = this.getTrueEntity(e);

                if (!entitypatch.currentlyAttackedEntity.contains(trueEntity) && !entitypatch.isTeammate(e)) {
                    if (e instanceof LivingEntity || e instanceof PartEntity) {
                        if (entity.hasLineOfSight(e)) {
                            ExtendedDamageSource source = this.getExtendedDamageSource(entitypatch, e, phase);
                            AttackResult attackResult = entitypatch.tryHarm(e, source, this.getDamageTo(entitypatch, trueEntity, phase, source));
                            boolean count = attackResult.resultType.count();

                            if (attackResult.resultType.dealtDamage()) {
                                int temp = e.invulnerableTime;
                                trueEntity.invulnerableTime = 0;

                                boolean attackSuccess = false;

                                if(e instanceof LivingEntity) {
                                    LivingEntity le = (LivingEntity)e;
                                    le.addEffect(new MobEffectInstance(RegMobEffect.WOUND.get(), 30, 50));
                                    le.addEffect(new MobEffectInstance(RegMobEffect.STOP.get(), 30, 1));
                                    //le.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 1));
                                    attackSuccess = true;

                                } else if(e instanceof PartEntity){
                                    PartEntity pe = (PartEntity)e;
                                    Entity _e = MobEffectUtils.getMainEntity(pe);
                                    if (_e instanceof LivingEntity){
                                        LivingEntity le = (LivingEntity)_e;
                                        le.addEffect(new MobEffectInstance(RegMobEffect.WOUND.get(), 30, 50));
                                        //le.addEffect(new MobEffectInstance(RegMobEffect.STOP.get(), 30, 1));
                                        //le.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), 30, 1));
                                        attackSuccess = true;
                                    }
                                }

                                if(attackSuccess){
                                    e.hurt((DamageSource)source, 0);
                                }
                                else {
                                    attackSuccess = e.hurt((DamageSource)source, attackResult.damage);
                                }

                                trueEntity.invulnerableTime = temp;
                                count = attackSuccess || trueEntity.isDamageSourceBlocked((DamageSource)source);
                                entitypatch.onHurtSomeone(e, phase.getHand(), source, attackResult.damage, attackSuccess);

                                if (attackSuccess) {
                                    if (entitypatch instanceof ServerPlayerPatch) {
                                        ServerPlayerPatch playerpatch = ((ServerPlayerPatch)entitypatch);
                                        playerpatch.getEventListener().triggerEvents(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_POST, new DealtDamageEvent<>(playerpatch, trueEntity, source, attackResult.damage));
                                    }

                                    if (flag1 && entitypatch instanceof PlayerPatch) {
                                        entity.getItemInHand(phase.getHand()).hurtEnemy(trueEntity, (Player)entity);
                                        flag1 = false;
                                    }

                                    e.level.playSound(null, e.getX(), e.getY(), e.getZ(), this.getHitSound(entitypatch, phase), e.getSoundSource(), 1.0F, 1.0F);
                                    this.spawnHitParticle(((ServerLevel)e.level), entitypatch, e, phase);
                                }
                            }

                            if (count) {
                                entitypatch.currentlyAttackedEntity.add(trueEntity);
                            }
                        }
                    }
                }
            }
        }
    }
}
