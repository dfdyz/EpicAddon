package com.jvn.epicaddon.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;


public abstract class MixinHotbar extends GuiComponent {
    /*
    @Shadow @Final
    protected ItemRenderer itemRenderer;

    @Shadow @Final
    protected Minecraft minecraft;
    @Shadow
    abstract Player getCameraPlayer();
    @Shadow
    protected int screenWidth;
    @Shadow
    protected int screenHeight;
    @Shadow @Final
    protected static ResourceLocation WIDGETS_LOCATION;

    @Shadow
    abstract void renderSlot(int p_168678_, int p_168679_, float p_168680_, Player p_168681_, ItemStack p_168682_, int p_168683_);



    private void renderSlot(int x, int y, float p_168680_, Player player, ItemStack itemStack, int p_168683_, float scale) {
        if (!itemStack.isEmpty()) {
            PoseStack posestack = RenderSystem.getModelViewStack();
            float f = (float)itemStack.getPopTime() - p_168680_;
            posestack.pushPose();
            posestack.translate((double)(x + 8), (double)(y + 12), 0.0D);
            posestack.scale(scale,scale,scale);
            if (f > 0.0F) {
                float f1 = 1.0F + f / 5.0F;
                posestack.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            }
            posestack.translate((double)(-(x + 8)), (double)(-(y + 12)), 0.0D);
            RenderSystem.applyModelViewMatrix();

            this.itemRenderer.renderAndDecorateItem(player, itemStack, x, y, p_168683_);
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            posestack.popPose();
            RenderSystem.applyModelViewMatrix();

            this.itemRenderer.renderGuiItemDecorations(this.minecraft.font, itemStack, x, y);
        }
    }

    @Overwrite
    protected void renderHotbar(float p_93010_, PoseStack poseStack){
        Player player = this.getCameraPlayer();
        if (player != null) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
            ItemStack itemstack = player.getOffhandItem();
            HumanoidArm humanoidarm = player.getMainArm().getOpposite();
            int i = this.screenWidth / 2;
            int j = this.getBlitOffset();
            int k = 182;
            int l = 91;
            this.setBlitOffset(-90);
            this.blit(p_93011_, i - 91, this.screenHeight - 22, 0, 0, 182, 22);
            this.blit(p_93011_, i - 91 - 1 + player.getInventory().selected * 20, this.screenHeight - 22 - 1, 0, 22, 24, 22);
            if (!itemstack.isEmpty()) {
                if (humanoidarm == HumanoidArm.LEFT) {
                    this.blit(p_93011_, i - 91 - 29, this.screenHeight - 23, 24, 22, 29, 24);
                } else {
                    this.blit(p_93011_, i + 91, this.screenHeight - 23, 53, 22, 29, 24);
                }
            }

            this.setBlitOffset(j);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();





            PoseStack matStack = new PoseStack();
            int i1 = 1;
            for(int j1 = 0; j1 < 9; ++j1) {
                matStack.pushPose();
                int k1 = i - 90 + j1 * 30 + 2;
                int l1 = this.screenHeight - 16 - 3 - 10;


                this.renderSlot(k1, l1, p_93010_, player, player.getInventory().items.get(j1), i1++, 2.0f);

                this.minecraft.getItemRenderer().renderStatic(player.getInventory().items.get(j1), ItemTransforms.TransformType.GUI, 1, 1, matStack, MultiBufferSource.immediate(Tesselator.getInstance().getBuilder()),1);
                matStack.popPose();
            }






            if (!itemstack.isEmpty()) {
                int j2 = this.screenHeight - 16 - 3 - 10;
                if (humanoidarm == HumanoidArm.LEFT) {
                    this.renderSlot(i - 91 - 26, j2, p_93010_, player, itemstack, i1++, 2.0f);
                } else {
                    this.renderSlot(i + 91 + 10, j2, p_93010_, player, itemstack, i1++, 2.0f);
                }
            }

            if (this.minecraft.options.attackIndicator == AttackIndicatorStatus.HOTBAR) {
                float f = this.minecraft.player.getAttackStrengthScale(0.0F);
                if (f < 1.0F) {
                    int k2 = this.screenHeight - 20;
                    int l2 = i + 91 + 6;
                    if (humanoidarm == HumanoidArm.RIGHT) {
                        l2 = i - 91 - 22;
                    }

                    RenderSystem.setShaderTexture(0, GuiComponent.GUI_ICONS_LOCATION);
                    int i2 = (int)(f * 19.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    this.blit(poseStack, l2, k2, 0, 94, 18, 18);
                    this.blit(poseStack, l2, k2 + 18 - i2, 18, 112 - i2, 18, i2);
                }
            }

            RenderSystem.disableBlend();
        }
    }


     */

}
