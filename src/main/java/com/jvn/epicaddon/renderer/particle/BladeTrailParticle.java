package com.jvn.epicaddon.renderer.particle;


import com.google.common.collect.Lists;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.renderer.SwordTrail.IAnimSTOverride;
import com.jvn.epicaddon.resources.BladeTrailTextureLoader;
import com.jvn.epicaddon.resources.config.RenderConfig;
import com.jvn.epicaddon.utils.Trail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

@OnlyIn(Dist.CLIENT) // copy from yesman
public class BladeTrailParticle extends TextureSheetParticle {
    //private final Joint joint;
    private final int jointid;
    private final Trail trail;
    private final AttackAnimation anim;
    private final List<TrailEdge> Nodes;
    private boolean animationEnd;
    private float startEdgeCorrection = 0.0F;
    private final LivingEntityPatch<?> entitypatch;

    private static final int interpolateCount = 7;

    public BladeTrailParticle(ClientLevel level, LivingEntityPatch entitypatch, AttackAnimation anim, int jointid, Trail trail, SpriteSet spriteSet) {
        super(level,0,0,0);
        //this.joint = joint;
        this.jointid = jointid;
        this.trail = trail;
        this.anim = anim;
        this.hasPhysics = false;
        this.entitypatch = entitypatch;
        this.Nodes = Lists.newLinkedList();

        Vec3 entityPos = entitypatch.getOriginal().position();
        this.setSize(10.0F, 10.0F);
        this.move(entityPos.x, entityPos.y + entitypatch.getOriginal().getEyeHeight(), entityPos.z);
        this.setSpriteFromAge(spriteSet);

        ClientAnimator animator = this.entitypatch.getClientAnimator();
        Armature armature = this.entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        Vec3 start = new Vec3(trail.x,trail.y,trail.z);
        Vec3 end = new Vec3(trail.ex,trail.ey,trail.ez);

        Vec3 prevStartPos = getPosByTick(animator,armature,start,0.0f);
        Vec3 prevEndPos = getPosByTick(animator,armature,end,0.0f);
        Vec3 middleStartPos = getPosByTick(animator,armature,start,0.5f);
        Vec3 middleEndPos = getPosByTick(animator,armature,end,0.5f);
        Vec3 currentStartPos = getPosByTick(animator,armature,start,1.0f);
        Vec3 currentEndPos = getPosByTick(animator,armature,end,1.0f);

        int interpolateCount1 = (int)Math.ceil(middleEndPos.distanceTo(prevEndPos)*interpolateCount);
        int interpolateCount2 = (int)Math.ceil(middleEndPos.distanceTo(currentEndPos)*interpolateCount);

        this.Nodes.add(new TrailEdge(prevStartPos, prevEndPos, this.trail.lifetime));
        for(int i=1;i<interpolateCount1;i++){
            Vec3 interStart = getPosByTick(animator,armature,start,0.5f/interpolateCount1*i);
            Vec3 interEnd = getPosByTick(animator,armature,end,0.5f/interpolateCount1*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
        }

        this.Nodes.add(new TrailEdge(middleStartPos, middleEndPos, this.trail.lifetime));

        for(int i=1;i<interpolateCount2;i++){
            Vec3 interStart = getPosByTick(animator,armature,start,0.5f+0.5f/interpolateCount2*i);
            Vec3 interEnd = getPosByTick(animator,armature,end,0.5f+0.5f/interpolateCount2*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
        }

        this.Nodes.add(new TrailEdge(currentStartPos, currentEndPos, this.trail.lifetime));
    }

    public Vec3 getPosByTick(ClientAnimator animator, Armature armature, Vec3 org, float partialTicks){
        Pose pose = animator.getPose(partialTicks);
        Vec3 pos = this.entitypatch.getOriginal().getPosition(partialTicks);
        OpenMatrix4f modelTf = OpenMatrix4f.createTranslation((float)pos.x, (float)pos.y, (float)pos.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(partialTicks)));
        OpenMatrix4f JointTf = Animator.getBindedJointTransformByIndex(pose, armature,jointid).mulFront(modelTf);
        return OpenMatrix4f.transform(JointTf,org);
    }

    @Override
    public void tick() {
        if(!this.animationEnd && !(entitypatch!=null && entitypatch.getOriginal()!=null && entitypatch.getOriginal().isAlive())){
            this.animationEnd = true;
        }

        if(entitypatch != null){
            Vec3 entityPos = entitypatch.getOriginal().position();
            this.x = entityPos.x;
            this.xo = entityPos.x;
            this.y = entityPos.y;
            this.yo = entityPos.y;
            this.z = entityPos.z;
            this.zo = entityPos.z;

            //this.move(entityPos.x, entityPos.y + entitypatch.getOriginal().getEyeHeight(), entityPos.z);
        }

        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.anim);
        this.Nodes.removeIf(v -> !v.isAlive());

        if (this.animationEnd) {
            if (this.lifetime-- == 0) {
                this.remove();
                return;
            }
        } else {
            if (this.anim != animPlayer.getAnimation().getRealAnimation() || animPlayer.getElapsedTime() > anim.getTotalTime()*0.85f) {
                this.animationEnd = true;
                this.lifetime = this.trail.lifetime;
            }
        }
        boolean needCorrection = this.Nodes.size() == 0;

        if (needCorrection) {
            float startCorrection = (0 - animPlayer.getPrevElapsedTime()) / (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime());
            this.startEdgeCorrection = interpolateCount * startCorrection;
        }

        ClientAnimator animator = this.entitypatch.getClientAnimator();
        Armature armature = this.entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        Vec3 start = new Vec3(trail.x,trail.y,trail.z);
        Vec3 end = new Vec3(trail.ex,trail.ey,trail.ez);

        Vec3 prevEndPos = getPosByTick(animator,armature,end,0.0f);
        Vec3 middleStartPos = getPosByTick(animator,armature,start,0.5f);
        Vec3 middleEndPos = getPosByTick(animator,armature,end,0.5f);
        Vec3 currentStartPos = getPosByTick(animator,armature,start,1.0f);
        Vec3 currentEndPos = getPosByTick(animator,armature,end,1.0f);

        int interpolateCount1 = (int)Math.ceil(middleEndPos.distanceTo(prevEndPos)*interpolateCount);
        int interpolateCount2 = (int)Math.ceil(middleEndPos.distanceTo(currentEndPos)*interpolateCount);

        for(int i=1;i<interpolateCount1;i++){
            Vec3 interStart = getPosByTick(animator,armature,start,0.5f/interpolateCount1*i);
            Vec3 interEnd = getPosByTick(animator,armature,end,0.5f/interpolateCount1*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
        }

        this.Nodes.add(new TrailEdge(middleStartPos, middleEndPos, this.trail.lifetime));

        for(int i=1;i<interpolateCount2;i++){
            Vec3 interStart = getPosByTick(animator,armature,start,0.5f+0.5f/interpolateCount2*i);
            Vec3 interEnd = getPosByTick(animator,armature,end,0.5f+0.5f/interpolateCount2*i);
            this.Nodes.add(new TrailEdge(interStart, interEnd, this.trail.lifetime));
        }

        this.Nodes.add(new TrailEdge(currentStartPos, currentEndPos, this.trail.lifetime));
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        if(trail.lifetime == 0){
            this.lifetime = 0;
            return;
        }
        if (this.Nodes.size() < 1) {
            return;
        }
        PoseStack poseStack = new PoseStack();
        int light = this.getLightColor(partialTick);
        this.setupPoseStack(poseStack, camera, partialTick);
        Matrix4f matrix4f = poseStack.last().pose();
        int edges = this.Nodes.size() - 1;
        boolean startFade = this.Nodes.get(0).lifetime == 1;
        boolean endFade = this.Nodes.get(edges).lifetime == this.trail.lifetime;

        float startEdge = (startFade ? interpolateCount * partialTick : 0.0F) + this.startEdgeCorrection;
        float endEdge = endFade ? edges - (interpolateCount) * (1.0F - partialTick) : edges - 1;

        float interval = 1.0F / (endEdge - startEdge);
        float partialStartEdge = interval * (startEdge % 1.0F);
        float from = -partialStartEdge;
        float to = -partialStartEdge + interval;

        int start = (int)Math.max(startEdge,0);
        int end = (int)Math.min(endEdge + 1,Nodes.size()-2);
        for (int i = start; i < end; i++) {
            TrailEdge e1 = this.Nodes.get(i);
            TrailEdge e2 = this.Nodes.get(i + 1);
            Vector4f pos1 = new Vector4f((float)e1.start.x, (float)e1.start.y, (float)e1.start.z, 1.0F);
            Vector4f pos2 = new Vector4f((float)e1.end.x, (float)e1.end.y, (float)e1.end.z, 1.0F);
            Vector4f pos3 = new Vector4f((float)e2.end.x, (float)e2.end.y, (float)e2.end.z, 1.0F);
            Vector4f pos4 = new Vector4f((float)e2.start.x, (float)e2.start.y, (float)e2.start.z, 1.0F);

            pos1.transform(matrix4f);
            pos2.transform(matrix4f);
            pos3.transform(matrix4f);
            pos4.transform(matrix4f);

            vertexConsumer.vertex(pos1.x(), pos1.y(), pos1.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e1.lifetime/trail.lifetime).uv(from, 1.0F).uv2(light).endVertex();
            vertexConsumer.vertex(pos2.x(), pos2.y(), pos2.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e1.lifetime/trail.lifetime).uv(from, 0.0F).uv2(light).endVertex();
            vertexConsumer.vertex(pos3.x(), pos3.y(), pos3.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e2.lifetime/trail.lifetime).uv(to, 0.0F).uv2(light).endVertex();
            vertexConsumer.vertex(pos4.x(), pos4.y(), pos4.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a * e2.lifetime/trail.lifetime).uv(to, 1.0F).uv2(light).endVertex();

            from += interval;
            to += interval;
        }
    }

    protected void setupPoseStack(PoseStack poseStack, Camera camera, float partialTicks) {
        Quaternion rotation = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
        Vec3 vec3 = camera.getPosition();
        float x = (float)-vec3.x();
        float y = (float)-vec3.y();
        float z = (float)-vec3.z();

        poseStack.translate(x, y, z);
        poseStack.mulPose(rotation);
    }

    /*
    private void makeTrailEdges(List<Vec3> startPositions, List<Vec3> endPositions, List<TrailEdge> dest) {
        for (int i = 0; i < startPositions.size(); i++) {
            dest.add(new TrailEdge(startPositions.get(i), endPositions.get(i), this.trail.lifetime));
        }
    }
     */

    @Override
    public ParticleRenderType getRenderType() {
        EpicAddonRenderType.BladeTrailRenderType type = BladeTrailTextureLoader.RenderType.get(trail.textureRegisterId);
        //System.out.println(type.toString());
        if(type == null) return EpicAddonRenderType.BladeTrail;
        return type;
    }

    @OnlyIn(Dist.CLIENT)
    private static class TrailEdge {
        final Vec3 start;
        final Vec3 end;
        int lifetime;
        float linerTime;


        public TrailEdge(Vec3 start, Vec3 end, int lifetime) {
            this.start = start;
            this.end = end;
            this.lifetime = lifetime;
            this.linerTime = lifetime;
        }

        boolean isAlive() {
            return --this.lifetime > 0;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            int eid = (int)Double.doubleToLongBits(x);
            int modid = (int)Double.doubleToLongBits(y);
            int animid = (int)Double.doubleToLongBits(z);
            int jointId = (int)Double.doubleToLongBits(xSpeed);
            //ySpeed -> isOffHand?
            Entity entity = level.getEntity(eid);

            if (entity != null) {
                LivingEntityPatch<?> entitypatch = getEntityPatch(entity, LivingEntityPatch.class);
                StaticAnimation anim = EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid);

                if (entitypatch != null && anim != null && anim instanceof AttackAnimation) {
                    Trail trail = RenderConfig.getItemTrail(entitypatch.getValidItemInHand(ySpeed>=0 ? InteractionHand.MAIN_HAND:InteractionHand.OFF_HAND));
                    IAnimSTOverride patchedAnim = ((IAnimSTOverride)anim);
                    if(trail == null){
                        if(patchedAnim.isPosOverride() && patchedAnim.isColorOverride() && patchedAnim.isLifetimeOverride()){
                            trail = patchedAnim.getTrail();
                        }
                        else return null;
                    }

                    if(patchedAnim.isPosOverride() || patchedAnim.isColorOverride() || patchedAnim.isLifetimeOverride()){
                        trail = (new Trail()).CopyFrom(trail);
                        Trail trail2 = patchedAnim.getTrail();
                        if(patchedAnim.isPosOverride()) trail.CopyPosFrom(trail2);
                        if(patchedAnim.isColorOverride()) trail.CopyColFrom(trail2);
                        if(patchedAnim.isLifetimeOverride()) trail.lifetime = trail2.lifetime;
                    }

                    return new BladeTrailParticle(level, entitypatch, (AttackAnimation) anim, jointId, trail, this.spriteSet);
                }
            }
            return null;
        }

        public static <T extends EntityPatch> T getEntityPatch(Entity entity, Class<T> type) {
            if (entity != null) {
                EntityPatch<?> entitypatch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse(null);

                if (entitypatch != null && type.isAssignableFrom(entitypatch.getClass())) {
                    return (T)entitypatch;
                }
            }
            return null;
        }
    }
}
