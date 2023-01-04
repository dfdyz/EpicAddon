package com.jvn.epicaddon.renderer;

import com.jvn.epicaddon.tools.GlobalVal;
import com.jvn.epicaddon.tools.HealthBarStyle;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;

public class HealthBarRenderer {

    public static Matrix4f getMVMatrix(PoseStack matStackIn, float posX, float posY, float posZ) {
        OpenMatrix4f viewMatrix = OpenMatrix4f.importFromMojangMatrix(matStackIn.last().pose());
        OpenMatrix4f finalMatrix = new OpenMatrix4f();
        finalMatrix.translate(new Vec3f(-posX, posY, -posZ));
        matStackIn.popPose();

        finalMatrix.mulFront(viewMatrix);

        return OpenMatrix4f.exportToMojangMatrix(finalMatrix);
    }


    public static final Matrix4f getMVMatrix(PoseStack matStackIn, LivingEntity entityIn, float correctionX, float correctionY, float correctionZ, float partialTicks) {
        float posX = (float)Mth.lerp((double)partialTicks, entityIn.xOld, entityIn.getX());
        float posY = (float)Mth.lerp((double)partialTicks, entityIn.yOld, entityIn.getY());
        float posZ = (float)Mth.lerp((double)partialTicks, entityIn.zOld, entityIn.getZ());
        matStackIn.pushPose();
        matStackIn.translate(-posX, -posY, -posZ);
        matStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F));
        return getMVMatrix(matStackIn, posX + correctionX, posY + correctionY, posZ + correctionZ);
    }

    public static void drawPlane(VertexConsumer vertexBuilder,Matrix4f matrix,float x,float y,float z,float ex,float ey,float ez,int uvx,int uvy,int uvex,int uvey){
        vertexBuilder.vertex(matrix, x, y, z).uv(uvex / 512f,uvy / 512f).endVertex();
        vertexBuilder.vertex(matrix, ex, y, ez).uv(uvx / 512f,uvy / 512f).endVertex();
        vertexBuilder.vertex(matrix, ex, ey, ez).uv(uvx / 512f,uvey / 512f).endVertex();
        vertexBuilder.vertex(matrix, x, ey, z).uv(uvex / 512f,uvey / 512f).endVertex();
    }

    public static void drawPlane(VertexConsumer vertexBuilder,Matrix4f matrix,float x,float y,float z,float ex,float ey,float ez,float uvx,float uvy,float uvex,float uvey){
        vertexBuilder.vertex(matrix, x, y, z).uv(uvx,uvy).endVertex();
        vertexBuilder.vertex(matrix, ex, y, ez).uv(uvex,uvy).endVertex();
        vertexBuilder.vertex(matrix, ex, ey, ez).uv(uvex,uvey).endVertex();
        vertexBuilder.vertex(matrix, x, ey, z).uv(uvx,uvey).endVertex();
    }

    public static Double getAngD(float in){
        return Math.PI * in / 180f;
    }

    public static float getAngF(float in){
        return (float) Math.PI * in / 180f;
    }

    public static float getUV(float rate){
        if(rate > 0.8f){
            return 0;
        }
        else if(rate <=0.8f && rate > 0.6f){
            return 28.0f * 0.001953125f;
        }
        else if(rate <=0.6f && rate > 0.5f){
            return 28.0f * 0.001953125f * 2.0f;
        }
        else if(rate <=0.5f && rate > 0.4f){
            return 28.0f * 0.001953125f * 3.0f;
        }
        else if(rate <=0.4f && rate > 0.25f){
            return 28.0f * 0.001953125f * 4.0f;
        }
        else if(rate <=0.25f){
            return 28.0f * 0.001953125f * 5.0f;
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static void draw(LivingEntity entityIn, PoseStack matStackIn, MultiBufferSource bufferIn, float partialTicks, HealthBarStyle healthBarStyle) {
        if(healthBarStyle.count <= 0) return;
        float yaw = -Minecraft.getInstance().player.getViewYRot(0.5f);
        if(healthBarStyle.count == 1){
            drawSingle(entityIn,matStackIn,bufferIn,partialTicks,healthBarStyle,yaw);
            //drawOne(entityIn,matStackIn,bufferIn,partialTicks,healthBarStyle,yaw);
        }
        if(healthBarStyle.count > 1){

            drawMuti(entityIn,matStackIn,bufferIn,partialTicks,healthBarStyle,healthBarStyle.count,yaw);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void drawSingle(LivingEntity entityIn, PoseStack matStackIn, MultiBufferSource bufferIn, float partialTicks, HealthBarStyle healthBarStyle,float yaw){
        Matrix4f matrix = getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, partialTicks);

        VertexConsumer vertexBuilder = bufferIn.getBuffer(EpicAddonRenderType.HealthBar);

        float ang = healthBarStyle.ang;

        float scale = entityIn.getScale();

        float r = healthBarStyle.r * (float)Math.pow(scale,0.55f);

        float ey = (-1.0f  + healthBarStyle.y_modify)* scale;
        float y = ey + 0.2f;

        int cpt = 18;
        float l = r * getAngF(ang);
        int count = (int)(l * cpt);

        // 1/512 = 0.001953125f
        float lpc = l / count;
        //float UV_a = 0.07761942786701344622373523202829f;

        final float _Begin = 1.0f * 0.2f;
        final float _End = 2.0f * 0.2f;
        float _To_End = l - _End;
        float _Mid = _To_End - _Begin;

        for(int i=0; i<count; i++){
            if(lpc*(i) <= _Begin){
                drawPlane(vertexBuilder,matrix,
                        (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * i / count))),y,-(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * i / count))),
                        (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count))),ey,-(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count))),
                        0.001953125f * 28 * lpc * i / _Begin,0, 0.001953125f * 28 * lpc * (i+1) / _Begin,0.001953125f * 28);
            }
            else if (lpc*i > _Begin && lpc*i <= l - _End){
                drawPlane(vertexBuilder,matrix,
                        (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * i / count))),y,-(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * i / count))),
                        (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count))),ey,-(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count))),
                        (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),0, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),0.001953125f * 28);
            }
            else{
                drawPlane(vertexBuilder,matrix,
                        (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * i / count))),y,-(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * i / count))),
                        (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count))),ey,-(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count))),
                        (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),0, (0.001953125f * (456 + 56 * ( lpc*(i+1) - _To_End )/ _End)),0.001953125f * 28);
            }
        }

        float ratio = entityIn.getHealth() / entityIn.getMaxHealth();
        float uvBegin = 0.001953125f * 28 + getUV(ratio);
        float uvEnd = uvBegin + 0.001953125f * 28;
        ratio = ratio>1.0f ? 1.0f : ratio;
        r-=0.00004f;

        float x,z,ex,ez;
        final float _Alpha = 5f / 28f * 0.2f;
        for(int i=0; i<count; i++){
            x = (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * i / count)));
            z = -(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * i / count)));
            ex = (float)(r * Math.cos(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count)));
            ez = -(float)(r * Math.sin(getAngF(yaw + GlobalVal.ANG + ang * (i+1) / count)));
            if( (lpc*(i+1) - _Alpha) / (l - 2*_Alpha) <= ratio){
                if(lpc*(i) <= _Begin){
                    drawPlane(vertexBuilder,matrix,
                            x,y,z,
                            ex,ey,ez,
                            0.001953125f * 28 * lpc * i / _Begin,uvBegin, 0.001953125f * 28 * lpc * (i+1) / _Begin,uvEnd);
                }
                else if (lpc*i > _Begin && lpc*i <= l - _End){
                    drawPlane(vertexBuilder,matrix,
                            x,y,z,
                            ex,ey,ez,
                            (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),uvBegin, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),uvEnd);
                }
                else{
                    drawPlane(vertexBuilder,matrix,
                            x,y,z,
                            ex,ey,ez,
                            (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),uvBegin, (0.001953125f * (456 + 56 * ( lpc*(i+1) - _To_End )/ _End)),uvEnd);
                }
            }
            else {
                float in = (lpc*(i+1) - (l - 2*_Alpha)*ratio - _Alpha) / lpc;
                ex = (ex-x)*in + x;
                ez = (ez-z)*in + z;
                if(lpc*(i) <= _Begin){
                    drawPlane(vertexBuilder,matrix,
                            x,y,z,
                            ex,ey,ez,
                            0.001953125f * 28 * lpc * i / _Begin,uvBegin , 0.001953125f * (28 * lpc * (i+in) / _Begin),uvEnd);
                }
                else if (lpc*i > _Begin && lpc*i <= l - _End){
                    drawPlane(vertexBuilder,matrix,
                            x,y,z,
                            ex,ey,ez,
                            (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),uvBegin, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),uvEnd);
                }
                else{
                    drawPlane(vertexBuilder,matrix,
                            x,y,z,
                            ex,ey,ez,
                            (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),uvBegin, (0.001953125f * (456 + 56 * ( lpc*(i+in) - _To_End )/ _End)),uvEnd);
                }
                break;
            }
        }
    }
    @OnlyIn(Dist.CLIENT)
    public static void drawMuti(LivingEntity entityIn, PoseStack matStackIn, MultiBufferSource bufferIn, float partialTicks, HealthBarStyle healthBarStyle,int numOf,float yaw){
        Matrix4f matrix = getMVMatrix(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + 0.25F, 0.0F, partialTicks);

        VertexConsumer vertexBuilder = bufferIn.getBuffer(EpicAddonRenderType.HealthBar);

        float ang = healthBarStyle.ang;

        float scale = (entityIn.isBaby() ? 0.5f : 1.0f);

        float r = healthBarStyle.r * (float)Math.pow(scale,0.55f);

        float ey = (-1.0f  + healthBarStyle.y_modify)* scale;
        float y = ey + 0.2f;

        int cpt = 18;
        float l = r * getAngF(ang);
        int count = (int)(l * cpt);

        // 1/512 = 0.001953125f
        float lpc = l / count;
        //float UV_a = 0.07761942786701344622373523202829f;

        final float _Begin = 1.0f * 0.2f;
        final float _End = 2.0f * 0.2f;
        float _To_End = l - _End;
        float _Mid = _To_End - _Begin;

        for (int j=0; j<numOf; j++){
            float v2 = ey + j * 0.28f;
            float v1 = y + j * 0.28f;
            for(int i=0; i<count; i++){
                if(lpc*(i) <= _Begin){
                    drawPlane(vertexBuilder,matrix,
                            (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count))),v1,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count))),
                            (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),v2,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),
                            0.001953125f * 28 * lpc * i / _Begin,0, 0.001953125f * 28 * lpc * (i+1) / _Begin,0.001953125f * 28);
                }
                else if (lpc*i > _Begin && lpc*i <= l - _End){
                    drawPlane(vertexBuilder,matrix,
                            (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count))),v1,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count))),
                            (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),v2,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),
                            (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),0, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),0.001953125f * 28);
                }
                else{
                    drawPlane(vertexBuilder,matrix,
                            (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count))),v1,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count))),
                            (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),v2,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),
                            (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),0, (0.001953125f * (456 + 56 * ( lpc*(i+1) - _To_End )/ _End)),0.001953125f * 28);
                }
            }
        }

        float ratio = entityIn.getHealth() / entityIn.getMaxHealth();
        float uvBegin = 0.001953125f * 28 + getUV(ratio);
        float uvEnd = uvBegin + 0.001953125f * 28;
        ratio = ratio>1.0f ? 1.0f : ratio;
        r-=0.00004f;

        float x,z,ex,ez;
        final float _Alpha = 5f / 28f * 0.2f;

        float pbh = 1.0f/numOf;
        float tmpR;
        for (int j=0; j<numOf; j++){
            float v2 = ey + j * 0.28f;
            float v1 = y + j * 0.28f;
            if(pbh*j < ratio && pbh*(j+1) >= ratio){
                tmpR = (ratio - pbh*j) / pbh;
                for(int i=0; i<count; i++){
                    x = (float)(r * Math.cos(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count)));
                    z = -(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * i / count)));
                    ex = (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count)));
                    ez = -(float)(r * Math.sin(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count)));
                    if( (lpc*(i+1) - _Alpha) / (l - 2*_Alpha) <= tmpR){
                        if(lpc*(i) <= _Begin){
                            drawPlane(vertexBuilder,matrix,
                                    x,v1,z,
                                    ex,v2,ez,
                                    0.001953125f * 28 * lpc * i / _Begin,uvBegin, 0.001953125f * 28 * lpc * (i+1) / _Begin,uvEnd);
                        }
                        else if (lpc*i > _Begin && lpc*i <= l - _End){
                            drawPlane(vertexBuilder,matrix,
                                    x,v1,z,
                                    ex,v2,ez,
                                    (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),uvBegin, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),uvEnd);
                        }
                        else{
                            drawPlane(vertexBuilder,matrix,
                                    x,v1,z,
                                    ex,v2,ez,
                                    (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),uvBegin, (0.001953125f * (456 + 56 * ( lpc*(i+1) - _To_End )/ _End)),uvEnd);
                        }
                    }
                    else {
                        float in = (lpc*(i+1) - (l - 2*_Alpha)*tmpR - _Alpha) / lpc;
                        ex = (ex-x)*in + x;
                        ez = (ez-z)*in + z;
                        if(lpc*(i) <= _Begin){
                            drawPlane(vertexBuilder,matrix,
                                    x,v1,z,
                                    ex,v2,ez,
                                    0.001953125f * 28 * lpc * i / _Begin,uvBegin , 0.001953125f * (28 * lpc * (i+in) / _Begin),uvEnd);
                        }
                        else if (lpc*i > _Begin && lpc*i <= l - _End){
                            drawPlane(vertexBuilder,matrix,
                                    x,v1,z,
                                    ex,v2,ez,
                                    (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),uvBegin, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),uvEnd);
                        }
                        else{
                            drawPlane(vertexBuilder,matrix,
                                    x,v1,z,
                                    ex,v2,ez,
                                    (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),uvBegin, (0.001953125f * (456 + 56 * ( lpc*(i+in) - _To_End )/ _End)),uvEnd);
                        }
                        break;
                    }
                }
            }
            else if(pbh*j <= ratio && pbh*(j+1) < ratio){
                for(int i=0; i<count; i++){
                    if(lpc*(i) <= _Begin){
                        drawPlane(vertexBuilder,matrix,
                                (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * i / count))),v1,-(float)(r * Math.sin(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * i / count))),
                                (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count))),v2,-(float)(r * Math.sin(getAngF(j*1.4f + yaw + GlobalVal.ANG + ang * (i+1) / count))),
                                0.001953125f * 28 * lpc * i / _Begin,uvBegin, 0.001953125f * 28 * lpc * (i+1) / _Begin,uvEnd);
                    }
                    else if (lpc*i > _Begin && lpc*i <= l - _End){
                        drawPlane(vertexBuilder,matrix,
                                (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * i / count))),v1,-(float)(r * Math.sin(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * i / count))),
                                (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count))),v2,-(float)(r * Math.sin(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count))),
                                (0.001953125f * (28 + 428 * (lpc*i - _Begin)/_Mid)),uvBegin, (0.001953125f * (28 + 428 * ( lpc*(i+1) - _Begin)/_Mid)),uvEnd);
                    }
                    else{
                        drawPlane(vertexBuilder,matrix,
                                (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * i / count))),v1,-(float)(r * Math.sin(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * i / count))),
                                (float)(r * Math.cos(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count))),v2,-(float)(r * Math.sin(getAngF(j*1.4f +yaw + GlobalVal.ANG + ang * (i+1) / count))),
                                (0.001953125f * (456 + 56 * (lpc*i - _To_End)/ _End)),uvBegin, (0.001953125f * (456 + 56 * ( lpc*(i+1) - _To_End )/ _End)),uvEnd);
                    }
                }
            }
            else {
                break;
            }
        }
    }

}
