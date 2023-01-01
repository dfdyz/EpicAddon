package com.jvn.epicaddon.renderer.particle;


import com.google.common.collect.Lists;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.resources.config.RenderConfig;
import com.jvn.epicaddon.tools.CubicBezierCurve;
import com.jvn.epicaddon.tools.Trail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.animation.types.LinkAnimation;
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
import java.util.Optional;

@OnlyIn(Dist.CLIENT) // copy from yesman
public class BladeTrailParticle extends TextureSheetParticle {
    //private final Joint joint;

    private final int jointid;
    private final Trail trail;
    private final StaticAnimation anim;
    private final List<TrailEdge> Nodes;
    private boolean animationEnd;
    private float startEdgeCorrection = 0.0F;
    private final LivingEntityPatch<?> entitypatch;

    private static final int interpolateCount = 16;

    public BladeTrailParticle(ClientLevel level, LivingEntityPatch entitypatch, StaticAnimation anim, int jointid, Trail trail, SpriteSet spriteSet) {
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
        Pose prevPose = animator.getPose(0.0f);
        Pose middlePose = animator.getPose(0.5f);
        Pose currentPose = animator.getPose(1.0f);

        Vec3 posOld = this.entitypatch.getOriginal().getPosition(0.0F);
        Vec3 posMid = this.entitypatch.getOriginal().getPosition(0.5F);
        Vec3 posCur = this.entitypatch.getOriginal().getPosition(1.0F);

        OpenMatrix4f prvmodelTf = OpenMatrix4f.createTranslation((float)posOld.x, (float)posOld.y, (float)posOld.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(0.0F)));
        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(0.5F)));
        OpenMatrix4f curModelTf = OpenMatrix4f.createTranslation((float)posCur.x, (float)posCur.y, (float)posCur.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(1.0F)));


        Armature armature = this.entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        OpenMatrix4f prevJointTf = Animator.getBindedJointTransformByIndex(prevPose, armature,jointid).mulFront(prvmodelTf);
        OpenMatrix4f middleJointTf = Animator.getBindedJointTransformByIndex(middlePose, armature, jointid).mulFront(middleModelTf);
        OpenMatrix4f currentJointTf = Animator.getBindedJointTransformByIndex(currentPose, armature, jointid).mulFront(curModelTf);

        Vec3 start = new Vec3(trail.x,trail.y,trail.z);
        Vec3 end = new Vec3(trail.ex,trail.ey,trail.ez);
        Vec3 prevStartPos = OpenMatrix4f.transform(prevJointTf,start);
        Vec3 prevEndPos = OpenMatrix4f.transform(prevJointTf, end);
        Vec3 middleStartPos = OpenMatrix4f.transform(middleJointTf,start);
        Vec3 middleEndPos = OpenMatrix4f.transform(middleJointTf, end);
        Vec3 currentStartPos = OpenMatrix4f.transform(currentJointTf, start);
        Vec3 currentEndPos = OpenMatrix4f.transform(currentJointTf, end);

        this.Nodes.add(new TrailEdge(prevStartPos, prevEndPos, this.trail.lifetime));
        this.Nodes.add(new TrailEdge(middleStartPos, middleEndPos, this.trail.lifetime));
        this.Nodes.add(new TrailEdge(currentStartPos, currentEndPos, this.trail.lifetime));
    }

    @Override
    public void tick() {
        AnimationPlayer animPlayer = this.entitypatch.getAnimator().getPlayerFor(this.anim);
        this.Nodes.removeIf(v -> !v.isAlive());

        if (this.animationEnd) {
            if (this.lifetime-- == 0) {
                this.remove();
            }
        } else {
            if (this.anim != animPlayer.getAnimation().getRealAnimation() || animPlayer.isEnd()) {
                this.animationEnd = true;
                this.lifetime = this.trail.lifetime+20;
            }
        }

        //boolean isTrailInvisible = animPlayer.getAnimation() instanceof LinkAnimation || animPlayer.getElapsedTime() <= this.trailInfo.startTime;
        //boolean isFirstTrail = this.Nodes.size() == 0;
        boolean needCorrection = this.Nodes.size() == 0;

        if (needCorrection) {
            float startCorrection = (0 - animPlayer.getPrevElapsedTime()) / (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime());
            this.startEdgeCorrection = interpolateCount * startCorrection;
        }

        Trail trailInfo = this.trail;

        ClientAnimator animator = this.entitypatch.getClientAnimator();
        Pose prevPose = animator.getPose(0.0f);
        Pose middlePose = animator.getPose(0.5f);
        Pose currentPose = animator.getPose(1.0f);

        Vec3 posOld = this.entitypatch.getOriginal().getPosition(0.0F);
        Vec3 posMid = this.entitypatch.getOriginal().getPosition(0.5F);
        Vec3 posCur = this.entitypatch.getOriginal().getPosition(1.0F);

        OpenMatrix4f prvmodelTf = OpenMatrix4f.createTranslation((float)posOld.x, (float)posOld.y, (float)posOld.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(0.0F)));
        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(0.5F)));
        OpenMatrix4f curModelTf = OpenMatrix4f.createTranslation((float)posCur.x, (float)posCur.y, (float)posCur.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(1.0F)));


        Armature armature = this.entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        OpenMatrix4f prevJointTf = Animator.getBindedJointTransformByIndex(prevPose, armature,jointid).mulFront(prvmodelTf);
        OpenMatrix4f middleJointTf = Animator.getBindedJointTransformByIndex(middlePose, armature, jointid).mulFront(middleModelTf);
        OpenMatrix4f currentJointTf = Animator.getBindedJointTransformByIndex(currentPose, armature, jointid).mulFront(curModelTf);

        Vec3 start = new Vec3(trail.x,trail.y,trail.z);
        Vec3 end = new Vec3(trail.ex,trail.ey,trail.ez);
        //Vec3 prevStartPos = OpenMatrix4f.transform(prevJointTf,start);
        //Vec3 prevEndPos = OpenMatrix4f.transform(prevJointTf, end);
        Vec3 middleStartPos = OpenMatrix4f.transform(middleJointTf,start);
        Vec3 middleEndPos = OpenMatrix4f.transform(middleJointTf, end);
        Vec3 currentStartPos = OpenMatrix4f.transform(currentJointTf, start);
        Vec3 currentEndPos = OpenMatrix4f.transform(currentJointTf, end);

        /*
        Pose prevPose = this.entitypatch.getArmature().getPrevPose();
        Pose middlePose = this.entitypatch.getArmature().getPose(0.5F);
        Pose currentPose = this.entitypatch.getArmature().getCurrentPose();
        Vec3 posOld = this.entitypatch.getOriginal().getPosition(0.0F);
        Vec3 posMid = this.entitypatch.getOriginal().getPosition(0.5F);
        Vec3 posCur = this.entitypatch.getOriginal().getPosition(1.0F);

        OpenMatrix4f prvmodelTf = OpenMatrix4f.createTranslation((float)posOld.x, (float)posOld.y, (float)posOld.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(0.0F)));
        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float)posMid.x, (float)posMid.y, (float)posMid.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(0.5F)));
        OpenMatrix4f curModelTf = OpenMatrix4f.createTranslation((float)posCur.x, (float)posCur.y, (float)posCur.z)
                .mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                        .mulBack(this.entitypatch.getModelMatrix(1.0F)));

        OpenMatrix4f prevJointTf = this.entitypatch.getArmature().getBindedTransformFor(prevPose, this.joint).mulFront(prvmodelTf);
        OpenMatrix4f middleJointTf = this.entitypatch.getArmature().getBindedTransformFor(middlePose, this.joint).mulFront(middleModelTf);
        OpenMatrix4f currentJointTf = this.entitypatch.getArmature().getBindedTransformFor(currentPose, this.joint).mulFront(curModelTf);
        Vec3 prevStartPos = OpenMatrix4f.transform(prevJointTf, trailInfo.start);
        Vec3 prevEndPos = OpenMatrix4f.transform(prevJointTf, trailInfo.end);
        Vec3 middleStartPos = OpenMatrix4f.transform(middleJointTf, trailInfo.start);
        Vec3 middleEndPos = OpenMatrix4f.transform(middleJointTf, trailInfo.end);
        Vec3 currentStartPos = OpenMatrix4f.transform(currentJointTf, trailInfo.start);
        Vec3 currentEndPos = OpenMatrix4f.transform(currentJointTf, trailInfo.end);

         */

        List<Vec3> finalStartPositions;
        List<Vec3> finalEndPositions;

        List<Vec3> startPosList = Lists.newArrayList();
        List<Vec3> endPosList = Lists.newArrayList();
        TrailEdge edge1;
        TrailEdge edge2;

        edge1 = this.Nodes.get(Math.max(this.Nodes.size() - (interpolateCount / 4 + 1),0));
        edge2 = this.Nodes.get(this.Nodes.size() - 1);
        edge2.lifetime++;

        startPosList.add(edge1.start);
        endPosList.add(edge1.end);
        startPosList.add(edge2.start);
        endPosList.add(edge2.end);
        startPosList.add(middleStartPos);
        endPosList.add(middleEndPos);
        startPosList.add(currentStartPos);
        endPosList.add(currentEndPos);

        finalStartPositions = CubicBezierCurve.getBezierInterpolatedPoints(startPosList, 1, 3, interpolateCount/2);
        finalEndPositions = CubicBezierCurve.getBezierInterpolatedPoints(endPosList, 1, 3, interpolateCount/2);

        if (!needCorrection) {
            finalStartPositions.remove(0);
            finalEndPositions.remove(0);
        }

        this.makeTrailEdges(finalStartPositions, finalEndPositions, Nodes);
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
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
        //int inCountEdges = (startFade || endFade) ? edges - this.trailInfo.interpolateCount * 2 : edges;
        //int fromEdges = endFade ? edges - this.trailInfo.interpolateCount * 2 : edges;
        //int toEdges = startFade ? edges - this.trailInfo.interpolateCount * 2 : edges;

        float startEdge = (startFade ? interpolateCount * partialTick : 0.0F) + this.startEdgeCorrection;
        float endEdge = endFade ? edges - (interpolateCount) * (1.0F - partialTick) : edges - 1;

        //System.out.println(startEdge +" "+ endEdge);
        //System.out.println(startEdge +" "+ this.trailInfo.startTime);
        //System.out.println(endEdge +" "+ endFade +" "+ edges);


        //float endEdge = endFade ? fromEdges + (toEdges - fromEdges) * partialTick : edges;
        //float gapDistance = (1.0F / inCountEdges) * this.trailInfo.interpolateCount * 2;
        //float startU = startFade ? -partialTick * gapDistance : 0.0F;
        //float endU = endFade ? 1.0F + gapDistance * (1.0F - partialTick) : 1.0F;

        float interval = 1.0F / (endEdge - startEdge);//1.0F / endEdge;
        float fading = Mth.clamp(this.animationEnd ? (this.lifetime + (1.0F - partialTick)) / this.trail.lifetime : 1.0F, 0.0F, 1.0F);

        //int edgeCount = (int)endEdge + 1;

        float partialStartEdge = interval * (startEdge % 1.0F);
        float from = -partialStartEdge;
        float to = -partialStartEdge + interval;

        for (int i = (int)(startEdge); i < (int)endEdge; i++) {  //ccccc +1
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

            float alphaFrom = Mth.clamp(from, 0.0F, 1.0F);
            float alphaTo = Mth.clamp(to, 0.0F, 1.0F);

            //if (e2.lifetime == this.trailInfo.trailLifetime) {
            vertexConsumer.vertex(pos1.x(), pos1.y(), pos1.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a).endVertex();
            vertexConsumer.vertex(pos2.x(), pos2.y(), pos2.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a).endVertex();
            vertexConsumer.vertex(pos3.x(), pos3.y(), pos3.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a).endVertex();
            vertexConsumer.vertex(pos4.x(), pos4.y(), pos4.z()).color(this.trail.r, this.trail.g, this.trail.b, this.trail.a).endVertex();

            from += interval;
            to += interval;
            //}
        }
        //} catch (Exception e) {
        //e.printStackTrace();
        //}
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

    private void makeTrailEdges(List<Vec3> startPositions, List<Vec3> endPositions, List<TrailEdge> dest) {
        for (int i = 0; i < startPositions.size(); i++) {
            dest.add(new TrailEdge(startPositions.get(i), endPositions.get(i), this.trail.lifetime));
        }
    }


    @Override
    public ParticleRenderType getRenderType() {
        return EpicAddonRenderType.BladeTrail;
    }

    @OnlyIn(Dist.CLIENT)
    private static class TrailEdge {
        final Vec3 start;
        final Vec3 end;
        int lifetime;

        public TrailEdge(Vec3 start, Vec3 end, int lifetime) {
            this.start = start;
            this.end = end;
            this.lifetime = lifetime;
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
            int offhand = (int)Double.doubleToLongBits(ySpeed);
            Entity entity = level.getEntity(eid);

            if (entity != null) {
                LivingEntityPatch<?> entitypatch = getEntityPatch(entity, LivingEntityPatch.class);
                StaticAnimation anim = EpicFightMod.getInstance().animationManager.findAnimationById(modid, animid);

                if (entitypatch != null && anim != null) {
                    Trail trail = RenderConfig.TrailItem.get(entitypatch.getValidItemInHand(offhand==0 ? InteractionHand.MAIN_HAND:InteractionHand.OFF_HAND).getItem().getRegistryName().toString());
                    if(trail == null) return null;
                    return new BladeTrailParticle(level, entitypatch, anim, jointId, trail, this.spriteSet);
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
