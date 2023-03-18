package com.jvn.epicaddon.skills.GenShin;

import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.jvn.epicaddon.entity.projectile.YoimiyaSAArrow;
import com.jvn.epicaddon.register.RegParticle;
import com.jvn.epicaddon.resources.EpicAddonSounds;
import com.jvn.epicaddon.utils.GlobalVal;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class YoimiyaSkillFunction {
    public static void BowShoot(LivingEntityPatch<?> entitypatch, String joint){
        if(entitypatch.currentlyAttackedEntity.size() > 0){
            Entity target = entitypatch.currentlyAttackedEntity.get(0);
            Level worldIn = entitypatch.getOriginal().getLevel();
            Vec3 handPos = getPosByTick(entitypatch,0.4f,joint);
            if(target.equals(entitypatch.getOriginal())){
                float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
                Vec3 shootVec = new Vec3(Math.cos(ang), 0 , Math.sin(ang));
                Vec3 shootPos = handPos.add(shootVec.x,0,shootVec.z);

                GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(shootVec.x(), 0.1f, shootVec.z(), 4.2f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }
            else {
                Vec3 shootPos = new Vec3(handPos.x, entitypatch.getOriginal().getEyeY() ,handPos.z);
                Vec3 shootTarget = target.position();
                shootTarget = new Vec3(shootTarget.x,target.getEyeY(),shootTarget.z);
                Vec3 shootVec = shootTarget.subtract(shootPos);
                shootPos = shootPos.add((new Vec3(shootVec.x,0,shootVec.z)).normalize());

                GenShinArrow projectile = new GenShinArrow(worldIn, entitypatch.getOriginal());
                projectile.setPos(shootPos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(shootVec.x(), shootVec.y(), shootVec.z(), 4.2f, 1.0f);
                worldIn.addFreshEntity(projectile);
            }

            if(worldIn instanceof ServerLevel){
                //Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
                ((ServerLevel)worldIn).sendParticles(RegParticle.GENSHIN_BOW.get() ,handPos.x,handPos.y,handPos.z,0,1D,1D,0.9019607D,1D);
            }
            entitypatch.playSound(EpicAddonSounds.GENSHIN_BOW, 0.0F, 0.0F);
        }
    }

    public static final Vec3[] Positions = new Vec3[]{
            new Vec3(-6.5,1.1,-2.6),
            new Vec3(-5.85,0.2,0.39),
            new Vec3(-3.9,0.8,3.9),
            new Vec3(-3.9,1.3,-4.2),

            new Vec3(-5.2,1.2,-2.6),
            new Vec3(-3.9,0.4,2.6),
            new Vec3(-5.85,0.2,0.39),
            new Vec3(-5.2,1.2,-2.6),
    };

    public static final int[] lifetimes = {
            3,3,3,3,1,2,2,1
    };
    public static void YoimiyaSAFirework(LivingEntityPatch<?> entitypatch){
        Entity entity = entitypatch.getOriginal();
        Level worldIn = entity.getLevel();

        if (worldIn instanceof ServerLevel){
            float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);

            Vec3 Center = entity.position();

            for (int i=0; i<Positions.length; ++i) {
                Vec3 spos = Center.add(Positions[i].yRot(-ang));
                ((ServerLevel) worldIn).sendParticles(RegParticle.GS_YOIMIYA_SA.get(),spos.x,spos.y+0.2f,spos.z,0,lifetimes[i],i,1,1D);
            }
        }

        SoundEvent[] sounds = new SoundEvent[]{
                EpicAddonSounds.Yoimiya_Skill1,
                EpicAddonSounds.Yoimiya_Skill2,
                EpicAddonSounds.Yoimiya_Skill3
        };

        entitypatch.playSound(sounds[Math.abs(GlobalVal.random.nextInt())%3],0.0F, 0.0F);
    }

    public static void YoimiyaSA(LivingEntityPatch<?> entitypatch){
        Level worldIn = entitypatch.getOriginal().getLevel();
        Vec3 handPos = getPosByTick(entitypatch,0.4f,"Tool_L");

        float ang = (float) ((entitypatch.getOriginal().getViewYRot(1)+90)/180 * Math.PI);
        Vec3 shootVec = new Vec3(Math.cos(ang), -1.2, Math.sin(ang));
        Vec3 shootPos = handPos.add(shootVec.x,0,shootVec.z);

        YoimiyaSAArrow projectile = new YoimiyaSAArrow(worldIn, shootPos, entitypatch.getOriginal());
        projectile.shoot(shootVec.x*2, shootVec.y*2, shootVec.z*2, 4.2f, 1.0f);

        float dmg = (float) entitypatch.getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE);
        projectile.setDmg(dmg);
        projectile.setExpRadio(5.5F);
        worldIn.addFreshEntity(projectile);
    }
    public static Vec3 getPosByTick(LivingEntityPatch entitypatch, float partialTicks, String joint){
        Animator animator = entitypatch.getAnimator();
        Armature armature = entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        Pose pose = animator.getPose(partialTicks);
        Vec3 pos = entitypatch.getOriginal().getPosition(partialTicks);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(entitypatch.getModelMatrix(partialTicks)));
        OpenMatrix4f JointTf = Animator.getBindedJointTransformByName(pose, armature, joint).mulFront(modelTf);
        return OpenMatrix4f.transform(JointTf,Vec3.ZERO);
    }

    public static void SendParticle(Level level, ParticleOptions particle, Vec3 pos){
        if(level instanceof ServerLevel){
            //Vec3 vec3 = getPosByTick(entitypatch,0.4f,"Tool_L");
            ((ServerLevel)level).sendParticles(particle ,pos.x,pos.y,pos.z,0,1D,1D,0.9019607D,1D);
        }
    }

}
