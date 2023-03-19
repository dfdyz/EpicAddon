package com.jvn.epicaddon.network.server.GenShin;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BowShoot {
    public final int namespaceId;
    public final int animationId;

    public BowShoot(int namespaceId, int animationId){
        this.namespaceId = namespaceId;
        this.animationId = animationId;
    }


    public static BowShoot fromBytes(FriendlyByteBuf buf) {
        return new BowShoot(buf.readInt(),buf.readInt());
    }

    public static void toBytes(BowShoot msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.namespaceId);
        buf.writeInt(msg.animationId);
    }

    public static void handle(BowShoot msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            System.out.println("s -> c");
            ServerPlayer player = ctx.get().getSender();

            /*
            if(player.level instanceof ServerLevel){
                ServerLevel level = (ServerLevel) player.level;

                //Vec3 pos = msg.vp.v1;
                //Vec3 vec = msg.vp.v2;

                GenShinArrow projectile = new GenShinArrow(level, player);
                projectile.setPos(pos);
                projectile.pickup = AbstractArrow.Pickup.DISALLOWED;
                projectile.setDmg((float) player.getAttributeValue(Attributes.ATTACK_DAMAGE)*0.2333f);
                projectile.shoot(vec.x(), vec.y(), vec.z(), 4.2f, 1.0f);
                level.addFreshEntity(projectile);

                level.sendParticles(RegParticle.GENSHIN_BOW.get() ,pos.x,pos.y,pos.z,0,1D,1D,0.9019607D,1D);


            }*/
        });
        ctx.get().setPacketHandled(true);
    }


}
