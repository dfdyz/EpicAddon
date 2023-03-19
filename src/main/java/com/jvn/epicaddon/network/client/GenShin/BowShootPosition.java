package com.jvn.epicaddon.network.client.GenShin;

import com.jvn.epicaddon.entity.projectile.GenShinArrow;
import com.jvn.epicaddon.network.EpicAddonDataSerializers;
import com.jvn.epicaddon.register.RegParticle;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowShootPosition {
    private EpicAddonDataSerializers.Vec3Pair vp;
    public BowShootPosition(EpicAddonDataSerializers.Vec3Pair vp) {
        this.vp = vp;
    }

    public EpicAddonDataSerializers.Vec3Pair getPair(){
        return this.vp;
    }

    public static BowShootPosition fromBytes(FriendlyByteBuf buf) {
        return new BowShootPosition(EpicAddonDataSerializers.VEC3Pair.read(buf));
    }

    public static void toBytes(BowShootPosition msg, FriendlyByteBuf buf) {
        EpicAddonDataSerializers.VEC3Pair.write(buf, msg.getPair());
    }

    public static void handle(BowShootPosition msg, Supplier<NetworkEvent.Context> ctx) {
        System.out.println("fuck");
        ctx.get().enqueueWork(() -> {
            System.out.println("fuck");
            ServerPlayer player = ctx.get().getSender();

            if(player.level instanceof ServerLevel){
                ServerLevel level = (ServerLevel) player.level;

                Vec3 pos = msg.vp.v1;
                Vec3 vec = msg.vp.v2;

                GenShinArrow projectile = new GenShinArrow(level, player);
                projectile.setPos(pos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(vec.x(), vec.y(), vec.z(), 4.2f, 1.0f);
                level.addFreshEntity(projectile);

                level.sendParticles(RegParticle.GENSHIN_BOW.get() ,pos.x,pos.y,pos.z,0,1D,1D,0.9019607D,1D);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
