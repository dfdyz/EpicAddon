package com.jvn.epicaddon.renderer.SwordTrail;

public class SwordTrail /*extends TrailPatr*/ {
    /*
    public SwordTrail() {

    }

    @Override
    public void draw(PoseStack matrixStackIn, MultiBufferSource buffer, LivingEntityPatch<?> entitypatch, AttackAnimation animation, float prevElapsedTime, float elapsedTime, float partialTicks, float attackSpeed, Trail t1, Trail t2) {

    }

    public static final int LM = 10;

    @Override
    public void addPart(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder, Trail tt) {
        OpenMatrix4f transpose = new OpenMatrix4f();
        OpenMatrix4f.transpose(pose, transpose);
        matrixStackIn.pushPose();
        MathUtils.translateStack(matrixStackIn, pose);
        MathUtils.rotateStack(matrixStackIn, transpose);
        Matrix4f matrix = matrixStackIn.last().pose();

        vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();
        vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();
        vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();
        vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();

        matrixStackIn.popPose();
    }

    @Override
    public void addBegin(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt) {
        OpenMatrix4f transpose = new OpenMatrix4f();
        OpenMatrix4f.transpose(pose, transpose);
        matrixStackIn.pushPose();
        MathUtils.translateStack(matrixStackIn, pose);
        MathUtils.rotateStack(matrixStackIn, transpose);
        Matrix4f matrix = matrixStackIn.last().pose();


        vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();
        vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();

        matrixStackIn.popPose();
    }

    @Override
    public void addEnd(PoseStack matrixStackIn, OpenMatrix4f pose, VertexConsumer vertexBuilder,Trail tt) {
        OpenMatrix4f transpose = new OpenMatrix4f();
        OpenMatrix4f.transpose(pose, transpose);
        matrixStackIn.pushPose();
        MathUtils.translateStack(matrixStackIn, pose);
        MathUtils.rotateStack(matrixStackIn, transpose);
        Matrix4f matrix = matrixStackIn.last().pose();

        vertexBuilder.vertex(matrix, tt.x, tt.y, tt.z).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();
        vertexBuilder.vertex(matrix, tt.ex, tt.ey, tt.ez).color(tt.r,tt.g,tt.b,tt.a).uv2(LM).endVertex();

        matrixStackIn.popPose();
    }

     */
}