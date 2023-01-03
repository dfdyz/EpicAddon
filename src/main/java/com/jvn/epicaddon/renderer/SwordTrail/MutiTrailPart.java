package com.jvn.epicaddon.renderer.SwordTrail;

import com.jvn.epicaddon.tools.Trail;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class MutiTrailPart<T extends TrailPatr> extends TrailPatr {
    /*
    protected T bigTrail;
    protected int numberOfTrails;

    public MutiTrailPart(int numberOfTrails) {
        this.numberOfTrails = numberOfTrails;
    }

    protected abstract T createTrailPatr();

    @OnlyIn(Dist.CLIENT)
    public void draw(PoseStack matrixStackIn, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, Trail t1, Trail t2){}

    @OnlyIn(Dist.CLIENT)
    public void addPart(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder, Trail tt){}

    @OnlyIn(Dist.CLIENT)
    public void addBegin(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt){}

    @OnlyIn(Dist.CLIENT)
    public void addEnd(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt){}
     */
}
