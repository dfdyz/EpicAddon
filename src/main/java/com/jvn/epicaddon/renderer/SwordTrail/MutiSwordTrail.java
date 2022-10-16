package com.jvn.epicaddon.renderer.SwordTrail;

import com.google.common.collect.Lists;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.ConfigVal;
import com.jvn.epicaddon.tools.Trail;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.JointTransform;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class MutiSwordTrail extends MutiTrailPart<SwordTrail> {
    public MutiSwordTrail(int numberOfTrails) {
        super(numberOfTrails);
    }

    @Override
    public SwordTrail createTrailPatr() {
        return new SwordTrail();
    }

    protected static final Logger LOGGER = LogUtils.getLogger();

    @OnlyIn(Dist.CLIENT)
    @Override
    public void draw(PoseStack matrixStackIn, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed,Trail t1,Trail t2) {
        float TrailLen = 0.082f;
        float realLen = Math.min(TrailLen,elapsedTime);
        int numberOf = Math.max(Math.round((float)(this.numberOfTrails) * realLen * attackSpeed), 1);
        float partialScale = 1.0f / numberOf;
        float begin = Math.max(elapsedTime-(realLen * attackSpeed),0);

        Armature armature = entitypatch.getEntityModel(Models.LOGICAL_SERVER).getArmature();
        String idx = animation.getPathIndexByTime(elapsedTime);
        boolean dual = false;
        int pathIndex1 = -1;
        int pathIndex2 = -1;
        List<SwordTrail> trails1 = Lists.newArrayList();
        if(idx.equals("Tool_R") || idx.equals("Tool_L")){
            pathIndex1 = armature.searchPathIndex(idx);
        }
        else {
            pathIndex1 = armature.searchPathIndex("Tool_R");
            pathIndex2 = armature.searchPathIndex("Tool_L");
            dual = true;
        }

        if(dual){
            VertexConsumer vertexBuilder;
            if(t1 != null){
                vertexBuilder = buffer.getBuffer(ClientConfig.cfg.EnableOptFineMode ? EpicAddonRenderType.SwordTrail_OF:EpicAddonRenderType.SwordTrail);
                for (int i = 0; i <= numberOf; i++) {
                    //SwordTrail SwordTrail = this.createTrailPatr();
                    //matrixStackIn.pushPose();
                    OpenMatrix4f mat = null;
                    armature.initializeTransform();

                    float partialTime = Mth.lerp(i*partialScale, begin, elapsedTime);

                    if (pathIndex1 == -1) {
                        mat = new OpenMatrix4f();
                    } else {
                        mat = Animator.getBindedJointTransformByIndex(animation.getPoseByTime(entitypatch,partialTime, 0.0f), armature, pathIndex1);
                    }
                    if(i == 0){
                        this.addBegin(matrixStackIn, mat, vertexBuilder,t1);
                    } else if (i == numberOf) {
                        this.addEnd(matrixStackIn,  mat, vertexBuilder,t1);
                    } else {
                        this.addPart(matrixStackIn, mat, vertexBuilder,t1);
                    }

                    //matrixStackIn.popPose();
                }
            }

            if(t2!=null){
                vertexBuilder = buffer.getBuffer(ClientConfig.cfg.EnableOptFineMode ? EpicAddonRenderType.SwordTrail_OF:EpicAddonRenderType.SwordTrail);
                for (int i = 0; i <= numberOf; i++) {
                    //SwordTrail SwordTrail = this.createTrailPatr();
                    //matrixStackIn.pushPose();
                    OpenMatrix4f mat = null;
                    armature.initializeTransform();

                    float partialTime = Mth.lerp(i*partialScale, begin, elapsedTime);

                    if (pathIndex2 == -1) {
                        mat = new OpenMatrix4f();
                    } else {
                        mat = Animator.getBindedJointTransformByIndex(animation.getPoseByTime(entitypatch,partialTime, 0.0f), armature, pathIndex2);
                    }

                    if(i == 0){
                        this.addBegin(matrixStackIn, mat, vertexBuilder,t2);
                    } else if (i == numberOf) {
                        this.addEnd(matrixStackIn,  mat, vertexBuilder,t2);
                    } else {
                        this.addPart(matrixStackIn, mat, vertexBuilder,t2);
                    }
                    //matrixStackIn.popPose();
                }
            }
        }
        else{
            Trail tt = idx.equals("Tool_R") ? t1:t2;

            if(tt != null){
                VertexConsumer vertexBuilder = buffer.getBuffer(ClientConfig.cfg.EnableOptFineMode ? EpicAddonRenderType.SwordTrail_OF:EpicAddonRenderType.SwordTrail);
                for (int i = 0; i <= numberOf; i++) {
                    //SwordTrail SwordTrail = this.createTrailPatr();
                    //matrixStackIn.pushPose();
                    OpenMatrix4f mat = null;
                    armature.initializeTransform();

                    float partialTime = Mth.lerp(i*partialScale, begin, elapsedTime);

                    if (pathIndex1 == -1) {
                        mat = new OpenMatrix4f();
                    } else {
                        mat = Animator.getBindedJointTransformByIndex(animation.getPoseByTime(entitypatch,partialTime, 0.0f), armature, pathIndex1);
                    }

                    if(i == 0){
                        this.addBegin(matrixStackIn, mat, vertexBuilder,tt);
                    } else if (i == numberOf) {
                        this.addEnd(matrixStackIn,  mat, vertexBuilder,tt);
                    } else {
                        this.addPart(matrixStackIn, mat, vertexBuilder,tt);
                    }
                    //matrixStackIn.popPose();
                }

                //tessellator.end();

                //if (depthTestEnabled)
                //    RenderSystem.enableDepthTest();
                //if (!blendEnabled)
                    //RenderSystem.disableBlend();
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public void addPart(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder, Trail tt){
        OpenMatrix4f transpose = new OpenMatrix4f();
        OpenMatrix4f.transpose(pose, transpose);
        matrixStackIn.pushPose();
        MathUtils.translateStack(matrixStackIn, pose);
        MathUtils.rotateStack(matrixStackIn, transpose);
        Matrix4f matrix = matrixStackIn.last().pose();
        if(ClientConfig.cfg.EnableOptFineMode){
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv(0f,0f).endVertex();
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv(0f,1f).endVertex();
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv(1f,1f).endVertex();
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv(1f,0f).endVertex();
        }
        else {
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).endVertex();
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).endVertex();
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).endVertex();
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).endVertex();
        }
        matrixStackIn.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    public void addBegin(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt){
        OpenMatrix4f transpose = new OpenMatrix4f();
        OpenMatrix4f.transpose(pose, transpose);
        matrixStackIn.pushPose();
        MathUtils.translateStack(matrixStackIn, pose);
        MathUtils.rotateStack(matrixStackIn, transpose);
        Matrix4f matrix = matrixStackIn.last().pose();

        if(ClientConfig.cfg.EnableOptFineMode){
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv(1f,1f).endVertex();
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv(1f,0f).endVertex();
        }
        else {
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).endVertex();
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).endVertex();
        }

        matrixStackIn.popPose();
    }

    @OnlyIn(Dist.CLIENT)
    public void addEnd(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt){
        OpenMatrix4f transpose = new OpenMatrix4f();
        OpenMatrix4f.transpose(pose, transpose);
        matrixStackIn.pushPose();
        MathUtils.translateStack(matrixStackIn, pose);
        MathUtils.rotateStack(matrixStackIn, transpose);
        Matrix4f matrix = matrixStackIn.last().pose();
        if(ClientConfig.cfg.EnableOptFineMode){
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv(0f,0f).endVertex();
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv(0f,1f).endVertex();

        }
        else {
            vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).endVertex();
            vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).endVertex();
        }
        matrixStackIn.popPose();
    }

}
