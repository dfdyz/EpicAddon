package com.jvn.epicaddon.renderer.particle;

import com.mojang.math.Vector3d;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;

import java.util.function.BiFunction;

public class EpicAddonHitParticalType {
    public static final BiFunction<Entity, Entity, Vector3d> Atker2Tar = (target, attacker) -> {
        EntityDimensions size = target.getDimensions(target.getPose());
        double x = target.getX() - attacker.getX(0.5);
        double y = target.getY() +  size.width * 0.5D - attacker.getY(0.5);
        double z = target.getZ() - attacker.getZ(0.5);
        double l = Math.pow(x*x + y*y + z*z,0.5);
        if(l>0){
            x/=l;
            y/=l;
            z/=l;
        }
        return new Vector3d(x, y, z);
    };
}
