package com.jvn.epicaddon.renderer.SwordTrail;

import com.jvn.epicaddon.tools.Trail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.PartEntity;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.RenderingTool;
import yesman.epicfight.gameasset.Models;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;
import java.util.List;

public abstract class TrailPatr {
    public TrailPatr() {

    }
    @OnlyIn(Dist.CLIENT)
    public abstract void draw(PoseStack matrixStackIn, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, Trail t1, Trail t2);

    @OnlyIn(Dist.CLIENT)
    public abstract void addPart(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt);

    @OnlyIn(Dist.CLIENT)
    public abstract void addBegin(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt);

    @OnlyIn(Dist.CLIENT)
    public abstract void addEnd(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt);
}
