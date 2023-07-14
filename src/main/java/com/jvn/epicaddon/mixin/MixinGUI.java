package com.jvn.epicaddon.mixin;


import com.jvn.epicaddon.resources.config.ClientConfig;
import com.jvn.epicaddon.resources.config.ClientConfigValue;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.Buffer;

import static com.jvn.epicaddon.renderer.EpicAddonRenderType.BladeTrailDefaultTexture;
import static net.minecraft.client.gui.components.AbstractWidget.WIDGETS_LOCATION;

//@Mixin(value = Gui.class)
public abstract class MixinGUI extends GuiComponent{
   /*
    @Shadow
    int screenWidth;
    @Shadow
    int screenHeight;
    @Shadow
    Minecraft minecraft;
    @Shadow
    protected abstract Player getCameraPlayer();
    @Shadow
    protected abstract void renderSlot(int a, int b, float c, Player player, ItemStack itemStack, int d);

    @Inject(method = "renderHotbar", at = @At("HEAD"),cancellable = true)
    protected void MixinRenderHotbar(float pt, PoseStack poseStack, CallbackInfo cbi){
        //todo

        //if(!ClientConfig.cfg.EnableOptFineMode) return;

        Player player = this.getCameraPlayer();
        if (player != null) {

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, BladeTrailDefaultTexture);

            int i = this.screenWidth / 2;
            int j = this.getBlitOffset();
            int k = 182;
            int l = 91;
            this.setBlitOffset(-90);
            this.blit(poseStack, i - 91, this.screenHeight - 22, 0, 0, 182, 22);
            this.blit(poseStack, i - 91 - 1 + player.getInventory().selected * 20, this.screenHeight - 22 - 1, 0, 22, 24, 22);
            if (!itemstack.isEmpty()) {
                if (humanoidarm == HumanoidArm.LEFT) {
                    this.blit(poseStack, i - 91 - 29, this.screenHeight - 23, 24, 22, 29, 24);
                } else {
                    this.blit(poseStack, i + 91, this.screenHeight - 23, 53, 22, 29, 24);
                }
            }


            this.setBlitOffset(j);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            int i1 = 1;

            for(int j1 = 0; j1 < 9; ++j1) {
                int k1 = i - 90 + j1 * 20 + 2;
                int l1 = this.screenHeight - 16 - 3;
                this.renderSlot(k1, l1, pt, player, player.getInventory().items.get(j1), i1++);
            }

            if (!itemstack.isEmpty()) {
                int j2 = this.screenHeight - 16 - 3;
                if (humanoidarm == HumanoidArm.LEFT) {
                    this.renderSlot(i - 91 - 26, j2, pt, player, itemstack, i1++);
                } else {
                    this.renderSlot(i + 91 + 10, j2, pt, player, itemstack, i1++);
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

        //cbi.cancel();
    }
*/



}
