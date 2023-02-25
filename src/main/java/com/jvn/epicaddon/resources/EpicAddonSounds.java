package com.jvn.epicaddon.resources;


import com.jvn.epicaddon.EpicAddon;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.main.EpicFightMod;

@Mod.EventBusSubscriber(modid= EpicAddon.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class EpicAddonSounds {
    public static final SoundEvent GENSHIN_BOW = RegSound("weapon.genshin_bow");

    private static SoundEvent RegSound(String name) {
        ResourceLocation r = new ResourceLocation(EpicAddon.MODID, name);
        SoundEvent s = new SoundEvent(r).setRegistryName(name);
        return s;
    }

    @SubscribeEvent
    public static void onSoundRegistry(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll(
                GENSHIN_BOW
        );
    }
}
