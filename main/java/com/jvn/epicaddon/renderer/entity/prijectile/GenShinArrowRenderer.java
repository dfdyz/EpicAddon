package com.jvn.epicaddon.renderer.entity.prijectile;

import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class GenShinArrowRenderer extends EntityRenderer<GenShinArrow> {

    public GenShinArrowRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(GenShinArrow p_114485_, float p_114486_, float p_114487_, PoseStack p_114488_, MultiBufferSource p_114489_, int p_114490_) {
        //todo
    }

    @Override
    public ResourceLocation getTextureLocation(GenShinArrow p_114482_) {
        return null;
    }
}
