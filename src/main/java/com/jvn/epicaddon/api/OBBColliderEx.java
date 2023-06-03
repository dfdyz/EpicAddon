package com.jvn.epicaddon.api;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;

public class OBBColliderEx extends OBBCollider {
    public OBBColliderEx(double posX, double posY, double posZ, double center_x, double center_y, double center_z) {
        super(posX, posY, posZ, center_x, center_y, center_z);
    }

    public OBBColliderEx(AABB outerAABB, double posX, double posY, double posZ, double center_x, double center_y, double center_z) {
        super(outerAABB, posX, posY, posZ, center_x, center_y, center_z);
    }

    public OBBColliderEx(AABB entityCallAABB, double pos1_x, double pos1_y, double pos1_z, double pos2_x, double pos2_y, double pos2_z, double norm1_x, double norm1_y, double norm1_z, double norm2_x, double norm2_y, double norm2_z, double center_x, double center_y, double center_z) {
        super(entityCallAABB, pos1_x, pos1_y, pos1_z, pos2_x, pos2_y, pos2_z, norm1_x, norm1_y, norm1_z, norm2_x, norm2_y, norm2_z, center_x, center_y, center_z);
    }

    public OBBColliderEx(AABB aabbCopy) {
        super(aabbCopy);
    }

    @Override
    public void drawInternal(PoseStack stack, MultiBufferSource buffer, OpenMatrix4f pose, boolean red) {}
}
