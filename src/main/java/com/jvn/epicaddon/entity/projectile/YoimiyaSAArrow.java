package com.jvn.epicaddon.entity.projectile;

import com.jvn.epicaddon.register.RegEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class YoimiyaSAArrow extends AbstractHurtingProjectile {
    //private float life = 200;
    protected float dmg = 15f;
    //public Vec3 vel = Vec3.ZERO;
    protected float expRadio = 2f;
    public YoimiyaSAArrow(EntityType<? extends AbstractHurtingProjectile> type, Level level) {
        super(type, level);
    }

    public YoimiyaSAArrow(Level level,Vec3 pos, Entity owner){
        super(RegEntity.GS_YoimiyaSA_ARROW.get(), level);
        this.setPos(pos);
        this.setOwner(owner);
    }

    public YoimiyaSAArrow(Level level,double x, double y, double z){
        super(RegEntity.GS_YoimiyaSA_ARROW.get(), level);
        this.setPos(x,y,z);
    }

    public YoimiyaSAArrow(Level level,double x, double y, double z,float dmg){
        super(RegEntity.GS_YoimiyaSA_ARROW.get(), level);
        this.setPos(x,y,z);
        this.dmg = dmg;
    }

    public void setExpRadio(float r){
        expRadio = r;
    }

    @Override
    public void tick() {
        super.tick();
        //this.moveTo(this.position().add(vel));
    }

    @Override
    public void shoot(double p_37266_, double p_37267_, double p_37268_, float p_37269_, float p_37270_) {
        super.shoot(p_37266_, p_37267_, p_37268_, p_37269_, p_37270_);
        //this.vel = (new Vec3(p_37266_, p_37267_, p_37268_)).scale(p_37269_/20f);
    }

    public void setDmg(float dmg){
        this.dmg = dmg;
    }

    @Override
    public void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (hitResult.getType() != HitResult.Type.MISS){
            //System.out.println("sa arrow hit");

            //float radio = 2;
            Vec3 ExpBox = new Vec3(expRadio,expRadio,expRadio);
            Vec3 hitPos = hitResult.getLocation();
            this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, hitPos.x, hitPos.y, hitPos.z, 0.0, 0.0D, 0.0D);

            if (this.level.isClientSide) {
                this.level.playLocalSound(hitPos.x, hitPos.y, hitPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
            }

            List<Entity> list = this.level.getEntities(this, new AABB(hitPos.subtract(ExpBox),hitPos.add(ExpBox)));
            Entity owner = this.getOwner();

            for (Entity e : list) {
                if(!e.equals(owner) && !e.ignoreExplosion()){

                    if(e.distanceTo(this) <= expRadio){
                        e.hurt(this.GetDamageSource(), this.dmg);
                    }
                }
            }
            if(!this.isRemoved()){
                this.discard();
            }
        }
    }

    protected DamageSource GetDamageSource(){
        return new EntityDamageSource("explosion.player", this.getOwner()).setScalesWithDifficulty().setExplosion();
    }

    @Override
    protected void defineSynchedData() {

    }
}
