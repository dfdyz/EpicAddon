package com.jvn.epicaddon.renderer.SwordTrail;

public class MutiSwordTrail /*extends MutiTrailPart<SwordTrail>*/ {
    /*
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

        Armature armature = entitypatch.getEntityModel(ClientModels.LOGICAL_CLIENT).getArmature();
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
*/
}
