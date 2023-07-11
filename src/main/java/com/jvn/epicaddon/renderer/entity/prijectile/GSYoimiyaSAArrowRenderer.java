package com.jvn.epicaddon.renderer.entity.prijectile;

import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.jvn.epicaddon.entity.projectile.YoimiyaSAArrow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GSYoimiyaSAArrowRenderer extends EntityRenderer<YoimiyaSAArrow> {
    public GSYoimiyaSAArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(YoimiyaSAArrow entity, float p_114486_, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        super.render(entity, p_114486_, partialTicks, poseStack, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(YoimiyaSAArrow arrow) {
        return null;
    }
}
