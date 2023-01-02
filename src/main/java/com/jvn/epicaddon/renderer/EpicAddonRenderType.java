package com.jvn.epicaddon.renderer;

import com.google.common.collect.ImmutableMap;
import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.resources.ModResourceLocation;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import yesman.epicfight.main.EpicFightMod;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import java.util.OptionalDouble;

@OnlyIn(Dist.CLIENT)
public class EpicAddonRenderType extends RenderType {

    boolean writeColor;
    boolean writeDepth;
    public static final ParticleRenderType BladeTrail = new ParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            //RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
            AbstractTexture abstracttexture = texturemanager.getTexture(GetTextures("trail/trail"));
            RenderSystem.bindTexture(abstracttexture.getId());

            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

            RenderSystem.setShaderTexture(0, abstracttexture.getId());

            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSortOrigin(0.0F, 0.0F, 0.0F);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
        }

        @Override
        public String toString() {
            return "AFTER_IMAGE";
        }
    };

    public static final RenderType SwordTrail = create(EpicAddon.MODID + ":sword_trail_def", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256, false, false,
            RenderType.CompositeState.builder()
                    .setShaderState(POSITION_COLOR_SHADER)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setLightmapState(NO_LIGHTMAP)
                    .setCullState(NO_CULL)
                    .createCompositeState(false)
    );

    public static final RenderType SwordTrail_OF = create(EpicAddon.MODID + ":sword_trail_of", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderStateShard.POSITION_COLOR_TEX_SHADER)
                    .setOutputState(TRANSLUCENT_TARGET)
                    .setTextureState(new TextureStateShard(GetTextures("trail/trail"),false,false))
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setWriteMaskState(COLOR_WRITE)
                    .setCullState(NO_CULL)
                    .createCompositeState(true)
    );


    public static final RenderType HealthBar = create(EpicAddon.MODID + ":health_bar", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 2097152, true, true,
            RenderType.CompositeState.builder()
                    .setShaderState(POSITION_TEX_SHADER)
                    .setTextureState(new RenderStateShard.TextureStateShard(GetTextures("gui/health_bar"), false, false))
                    //.setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setOverlayState(RenderStateShard.NO_OVERLAY)
                    .setTransparencyState(NO_TRANSPARENCY)
                    .setLightmapState(RenderStateShard.NO_LIGHTMAP)
                    //.setWriteMaskState(COLOR_DEPTH_WRITE)
                    .setCullState(NO_CULL)
                    .createCompositeState(true)
    );

    public static ResourceLocation GetTextures(String path){
        return new ResourceLocation(EpicAddon.MODID, "textures/" + path + ".png");
    }

    public EpicAddonRenderType(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }
}
