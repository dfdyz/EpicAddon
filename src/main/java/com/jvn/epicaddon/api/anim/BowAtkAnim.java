package com.jvn.epicaddon.api.anim;

import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.EpicAddonSounds;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.apache.commons.lang3.ArrayUtils;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import static com.jvn.epicaddon.resources.EpicAddonAnimations.getPosByTick;

public class BowAtkAnim extends AttackAnimation {
    //private final int Aid;
    //public final String Hjoint;
    public BowAtkAnim(float convertTime,float antic, float recovery, InteractionHand hand, @Nullable Collider collider, String scanner, String path, Model model) {
        super(convertTime, path, model,
                new PhaseEx(0.0F, 0f, antic, recovery, Float.MAX_VALUE, hand, scanner, collider, false));

        //Hjoint = shoot;
        this.addProperty(AnimationProperty.AttackAnimationProperty.LOCK_ROTATION, true);
        this.addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true);
        //this.Aid = aid;
    }


    @Override
    public void setLinkAnimation(Pose pose1, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0);

        if (entitypatch instanceof PlayerPatch<?>) {
            PlayerPatch<?> playerpatch = (PlayerPatch<?>)entitypatch;
            PhaseEx phase = (PhaseEx) this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            extTime *= (float)(this.totalTime * playerpatch.getAttackSpeed(phase.getHand()));
        }

        extTime = Math.max(extTime - this.convertTime, 0);
        super.setLinkAnimation(pose1, extTime, entitypatch, dest);
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();

        if (!this.properties.containsKey(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float basisSpeed = Float.parseFloat(String.format(Locale.US, "%.2f", (1.0F / this.totalTime)));
            this.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, basisSpeed);
        }

    }

    @Override
    protected Vec3f getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3f vec3 = super.getCoordVector(entitypatch, dynamicAnimation);

        if (entitypatch.shouldBlockMoving() && this.getProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3.scale(0.0F);
        }

        return vec3;
    }

    @Override
    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);

        if (!entitypatch.isLogicalClient()) {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            float elapsedTime = player.getElapsedTime();
            float prevElapsedTime = player.getPrevElapsedTime();
            EntityState state = this.getState(elapsedTime);
            EntityState prevState = this.getState(prevElapsedTime);
            PhaseEx phase = (PhaseEx) this.getPhaseByTime(elapsedTime);

            if (state.getLevel() == 1 && !state.turningLocked()) {
                if (entitypatch instanceof MobPatch) {
                    ((Mob)entitypatch.getOriginal()).getNavigation().stop();
                    entitypatch.getOriginal().attackAnim = 2;
                    LivingEntity target = entitypatch.getTarget();

                    if (target != null) {
                        entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
                    }
                }
            } else if (prevState.attacking() || state.attacking() || (prevState.getLevel() < 2 && state.getLevel() > 2)) {
                if (!prevState.attacking()) {
                    //entitypatch.playSound(this.getSwingSound(entitypatch, phase), 0.0F, 0.0F);
                    entitypatch.currentlyAttackedEntity.clear();
                }

                if(!phase.atking){
                    //System.out.println("scan target");
                    this.ScanTarget(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
                }
                else {
                    //System.out.println("Atk");
                    //this.ShootProjectile(entitypatch, elapsedTime, phase.getJoint());
                }
            }
        }
    }

    public void ScanTarget(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, PhaseEx phase){
        Collider collider = this.getCollider(entitypatch, elapsedTime);
        LivingEntity entity = entitypatch.getOriginal();
        entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature().initializeTransform();
        float poseTime = state.attacking() ? elapsedTime : phase.getContact();
        List<Entity> list = collider.updateAndSelectCollideEntity(entitypatch, this, poseTime, poseTime, phase.getColliderJointName(), this.getPlaySpeed(entitypatch));

        if (list.size() > 0) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, HitEntityList.Priority.DISTANCE);
            int maxStrikes = 1;
            entitypatch.getOriginal().setLastHurtMob(list.get(0));

            while (entitypatch.currentlyAttackedEntity.size() < maxStrikes && hitEntities.next()) {
                Entity e = hitEntities.getEntity();
                if(!e.isAlive()) continue;

                LivingEntity trueEntity = this.getTrueEntity(e);
                if (!entitypatch.isTeammate(e) && trueEntity != null) {
                    if (e instanceof LivingEntity || e instanceof PartEntity) {
                        if (entity.hasLineOfSight(e)) {
                            entitypatch.currentlyAttackedEntity.add(trueEntity);
                        }
                    }
                }
            }
        }
        entitypatch.currentlyAttackedEntity.add(entitypatch.getOriginal());
    }

    /*
    public void ShootProjectile(LivingEntityPatch<?> entitypatch, float time, String joint){

        if(entitypatch.currentlyAttackedEntity.size() > 0){
            Entity target = entitypatch.currentlyAttackedEntity.get(0);
            Level worldIn = entitypatch.getOriginal().getLevel();
            if(target.equals(entitypatch.getOriginal())){
                float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
                Vec3 shootVec = new Vec3(Math.cos(ang), 0 , Math.sin(ang));
                Vec3 shootPos = entitypatch.getOriginal().position().add((shootVec).normalize());

                Projectile projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos.x, entitypatch.getOriginal().getEyeHeight(), shootPos.z);
                ((Arrow) projectile).pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.shoot(shootVec.x(), 0.1f, shootVec.z(), 4.0f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }
            else {
                Vec3 shootPos = entitypatch.getOriginal().position();
                shootPos = new Vec3(shootPos.x, entitypatch.getOriginal().getEyeY() ,shootPos.z);
                Vec3 shootTarget = target.position();
                shootTarget = new Vec3(shootTarget.x,target.getEyeY(),shootTarget.z);
                Vec3 shootVec = shootTarget.subtract(shootPos);
                shootPos = shootPos.add((new Vec3(shootVec.x,0,shootVec.z)).normalize());

                Projectile projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                ((Arrow) projectile).pickup = AbstractArrow.Pickup.DISALLOWED;
                //((Arrow) projectile).setPierceLevel((byte) 2);
                projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 3.0f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }

            if(worldIn instanceof ServerLevel){
                Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
                ((ServerLevel)worldIn).sendParticles(RegParticle.GENSHIN_BOW.get() ,vec3.x,vec3.y,vec3.z,0,1D,1D,0.9019607D,1D);
            }
            //entitypatch.playSound(EpicAddonSounds.GENSHIN_BOW, 0.0F, 0.0F);
            //entitypatch.currentlyAttackedEntity.clear();
        //}
    }*/

    @Override
    public void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {

    }

    public static class PhaseEx extends AttackAnimation.Phase{
        protected final boolean atking;

        public PhaseEx(float start, float antic, float contact, float recovery, float end, String jointName, Collider collider) {
            super(start, antic, contact, recovery, end, jointName, collider);
            atking = false;
        }

        public PhaseEx(float start, float antic, float contact, float recovery, float end, InteractionHand hand, String jointName, Collider collider,boolean isAtkPhase) {
            super(start, antic, contact, recovery, end, hand, jointName, collider);
            atking = isAtkPhase;
        }

        public PhaseEx(float start, float antic, float preDelay, float contact, float recovery, float end, String jointName, Collider collider, boolean isAtkPhase) {
            super(start, antic, preDelay, contact, recovery, end, jointName, collider);
            atking = isAtkPhase;
        }

        public PhaseEx(float start, float antic, float preDelay, float contact, float recovery, float end, InteractionHand hand, String jointName, Collider collider, boolean isAtkPhase) {
            super(start, antic, preDelay, contact, recovery, end, hand, jointName, collider);
            atking = isAtkPhase;
        }

        public String getJoint(){
            return this.jointName;
        }
        public float getContact(){
            return this.contact;
        }
    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }
}
