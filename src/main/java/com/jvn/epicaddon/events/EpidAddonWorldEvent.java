package com.jvn.epicaddon.events;

import com.jvn.epicaddon.EpicAddon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = EpicAddon.MODID)
public class EpidAddonWorldEvent {

    private static final Map<Player,Integer> FloatPlayer = new HashMap<>();


}
