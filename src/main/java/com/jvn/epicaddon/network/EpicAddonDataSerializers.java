package com.jvn.epicaddon.network;

import com.jvn.epicaddon.EpicAddon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DataSerializerEntry;
import yesman.epicfight.main.EpicFightMod;

public class EpicAddonDataSerializers {
    public static class Vec3Pair{
        public final Vec3 v1,v2;
        public Vec3Pair(Vec3 v1,Vec3 v2){
            this.v1 = v1;
            this.v2 = v2;
        }

        public Vec3Pair(double x1,double y1,double z1,double x2,double y2,double z2){
            this.v1 = new Vec3(x1, y1, z1);
            this.v2 = new Vec3(x2, y2, z2);
        }
    }

    public static final EntityDataSerializer<Vec3Pair> VEC3Pair = new EntityDataSerializer<Vec3Pair>() {
        public void write(FriendlyByteBuf buffer, Vec3Pair vec3p) {
            buffer.writeDouble(vec3p.v1.x);
            buffer.writeDouble(vec3p.v1.y);
            buffer.writeDouble(vec3p.v1.z);
            buffer.writeDouble(vec3p.v2.x);
            buffer.writeDouble(vec3p.v2.y);
            buffer.writeDouble(vec3p.v2.z);
        }

        public Vec3Pair read(FriendlyByteBuf buffer) {
            return new Vec3Pair(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(),
                    buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        }

        public Vec3Pair copy(Vec3Pair vec3p) {
            return vec3p;
        }
    };

    public EpicAddonDataSerializers() {
    }

    public static void register(RegistryEvent.Register<DataSerializerEntry> event) {
        event.getRegistry().registerAll(
                new DataSerializerEntry[]{
                        (new DataSerializerEntry(VEC3Pair)).
                                        setRegistryName(new ResourceLocation(EpicAddon.MODID, "vector3_pair"))
                });
    }
}