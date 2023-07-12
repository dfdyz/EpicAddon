package com.jvn.epicaddon.skills.SAOInternal;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.renderer.EpicAddonRenderType;
import com.jvn.epicaddon.resources.EpicAddonSkillCategories;
import com.jvn.epicaddon.skills.IMutiSpecialSkill;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.gui.ModIngameGui;
import yesman.epicfight.skill.*;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.ArrayList;
import java.util.function.Function;

public class MutiSpecialSkill extends PassiveSkill {
    public static final SkillDataManager.SkillDataKey<Integer> CHILD_SKILL_INDEX = SkillDataManager.SkillDataKey.createDataKey(SkillDataManager.ValueType.INTEGER);;

    public MutiSpecialSkill(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    public void RollSelect(ServerPlayerPatch playerPatch, int dir){
        SkillContainer container = playerPatch.getSkill(category);
        SkillDataManager datas = container.getDataManager();
        if(!datas.hasData(CHILD_SKILL_INDEX)){
            datas.registerData(CHILD_SKILL_INDEX);
        }
        int selected = 0;
        IMutiSpecialSkill sai = getSAInstance(container);
        if(sai != null){
            int size = sai.getSkillTextures(playerPatch).size();
            selected = Math.max(0,Math.min(datas.getDataValue(CHILD_SKILL_INDEX)+dir, size-1));
        }
        datas.setDataSync(CHILD_SKILL_INDEX, selected, playerPatch.getOriginal());
    }

    public void onInitiate(SkillContainer container) {
        container.getDataManager().registerData(CHILD_SKILL_INDEX);
        container.getDataManager().setData(CHILD_SKILL_INDEX, 0);
    }

    public void onRemoved(SkillContainer container) {
        container.getDataManager().setData(CHILD_SKILL_INDEX, 0);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldDraw(SkillContainer container) {
        SkillContainer sa = container.getExecuter().getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK);
        //System.out.println(sa != null && sa.getSkill() instanceof IMutiSpecialSkill);
        return sa != null && sa.getSkill() instanceof IMutiSpecialSkill;
    }

    protected IMutiSpecialSkill getSAInstance(SkillContainer container){
        Skill sa = container.getExecuter().getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK).getSkill();
        if(sa != null && sa instanceof IMutiSpecialSkill) return (IMutiSpecialSkill) sa;
        else return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void drawOnGui(BattleModeGui gui, SkillContainer container, PoseStack matStackIn, float _x, float _y, float _scale, int width, int height) {
        Skill sa = container.getExecuter().getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK).getSkill();
        //EpicAddon.LOGGER.info("?????");
        if(sa == null) return;
        //EpicAddon.LOGGER.info("11111");
        if(!(sa instanceof IMutiSpecialSkill)) return;
        //EpicAddon.LOGGER.info("?????");
        //Vec3f pos = new Vec3f(42F, 48F, 0.117F);
        //float scale = 0.078F;
        float x = 42F;
        float y = 48F;


        IMutiSpecialSkill saInstance = (IMutiSpecialSkill) sa;



        ArrayList<ResourceLocation> textures = saInstance.getSkillTextures(container.getExecuter());

        int active = Math.min(textures.size()-1,container.getDataManager().getDataValue(CHILD_SKILL_INDEX));
        for(int i=0; i<textures.size(); ++i){
            ResourceLocation t = textures.get(i);
            //EpicAddon.LOGGER.info("?????");
            matStackIn.pushPose();

            boolean actived = active == i;
            float scale = actived ? 0.085F : 0.078F;
            double r = actived ? 35 : 30;

            matStackIn.scale(scale, scale, 1.0F);
            matStackIn.translate(0.0, (double)((float)gui.getSlidingProgression() * 1.0F / scale), 0.0);

            float scaleMultiply = 1.0F / scale;

            double a = (160.0 - 40*i)/180.0*Math.PI;
            double ox = r*Math.cos(a);
            double oy = -r*Math.sin(a);

            int canExeCol = FastColor.ARGB32.color(255,50,249,255);
            int cannotExeCol = FastColor.ARGB32.color(160,255,255,255);

            //System.out.println(FastColor.ARGB32.color(200,255,255,255));

            RenderSystem.setShaderTexture(0, actived ? EpicAddonRenderType.ChildSkillSelected : EpicAddonRenderType.ChildSkillnoSelected);
            drawTexturedModalRectFixCoord(gui, matStackIn.last().pose(), (float)(width - x + ox) * scaleMultiply, (float)(height - y + oy) * scaleMultiply, 0, 0, 255, 255
                    , FastColor.ARGB32.color(200,200,200,200));

            boolean canExe = saInstance.isSkillActive(container.getExecuter(), i);

            RenderSystem.setShaderTexture(0, t);
            drawTexturedModalRectFixCoord(gui,matStackIn.last().pose(), (float)(width - x + ox) * scaleMultiply, (float)(height - y + oy) * scaleMultiply, 0, 0, 255, 255
                    ,canExe ? canExeCol : cannotExeCol);
            //matStackIn.scale(scaleMultiply, scaleMultiply, 1.0F);
            matStackIn.popPose();
        }


        //gui.font.drawShadow(matStackIn, String.format("x%.1f", container.getDataManager().getDataValue(PENALTY)), (float)width - x, (float)height - y + 6.0F, 16777215);
    }

    public void drawTexturedModalRectFixCoord(ModIngameGui ingameGui, Matrix4f matrix, float xCoord, float yCoord, int minU, int minV, int maxU, int maxV, int col) {
        drawTexturedModalRectFixCoord(matrix, xCoord, yCoord, (float)maxU, (float)maxV, (float)ingameGui.getBlitOffset(), (float)minU, (float)minV, (float)maxU, (float)maxV, col);
    }

    public static void drawTexturedModalRectFixCoord(Matrix4f matrix, float minX, float minY, float maxX, float maxY, float z, float minU, float minV, float maxU, float maxV, int col) {
        float cor = 0.00390625F;
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferbuilder.vertex(matrix, minX, minY + maxX, z).uv(minU * cor, (minV + maxV) * cor).color(col).endVertex();
        bufferbuilder.vertex(matrix, minX + maxY, minY + maxX, z).uv((minU + maxU) * cor, (minV + maxV) * cor).color(col).endVertex();
        bufferbuilder.vertex(matrix, minX + maxY, minY, z).uv((minU + maxU) * cor, minV * cor).color(col).endVertex();
        bufferbuilder.vertex(matrix, minX, minY, z).uv(minU * cor, minV * cor).color(col).endVertex();
        tessellator.end();
    }
}
