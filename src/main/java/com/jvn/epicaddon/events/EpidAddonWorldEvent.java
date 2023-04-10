package com.jvn.epicaddon.events;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.data.loot.EpicAddonLootTables;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.data.loot.EpicFightLootTables;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID)
public class EpidAddonWorldEvent {
    @SubscribeEvent
    public static void onLootTableRegistry(final LootTableLoadEvent event) {
        EpicAddonLootTables.modifyVanillaLootPools(event);
    }
}
