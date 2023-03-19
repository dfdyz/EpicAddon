package com.jvn.epicaddon.network;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.network.client.GenShin.BowShootPosition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;

public class EpicAddonNetworkManager {
	private static final String PROTOCOL_VERSION = "114514";
	public static final SimpleChannel INSTANCE =
			NetworkRegistry.newSimpleChannel(
					new ResourceLocation(EpicAddon.MODID, "network_mgr"),
			() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}
	public static <MSG> void sendToClient(MSG message, PacketTarget packetTarget) {
		INSTANCE.send(packetTarget, message);
	}
	
	public static <MSG> void sendToAll(MSG message) {
		sendToClient(message, PacketDistributor.ALL.noArg());
	}

	public static <MSG> void sendToAllPlayerTrackingThisEntity(MSG message, Entity entity) {
		sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
	}
	
	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		sendToClient(message, PacketDistributor.PLAYER.with(() -> player));
	}

	public static <MSG> void sendToAllPlayerTrackingThisEntityWithSelf(MSG message, ServerPlayer entity) {
		sendToPlayer(message, entity);
		sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
	}
	
	public static void registerPackets() {
		int id = 0;
		INSTANCE.registerMessage(id++, BowShootPosition.class, BowShootPosition::toBytes, BowShootPosition::fromBytes, BowShootPosition::handle);
	}
}