package com.jvn.epicaddon.register;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.mobeffects.StopEffect;
import com.jvn.epicaddon.mobeffects.WoundEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegMobEffect {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EpicAddon.MODID);
    public static final RegistryObject<MobEffect> WOUND = EFFECTS.register("wound", () -> new WoundEffect(MobEffectCategory.BENEFICIAL, 16735744));
    public static final RegistryObject<MobEffect> STOP = EFFECTS.register("stop", () -> new StopEffect(MobEffectCategory.BENEFICIAL, 16735744));


}
