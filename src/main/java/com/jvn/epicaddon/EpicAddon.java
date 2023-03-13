package com.jvn.epicaddon;

import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.register.*;
import com.jvn.epicaddon.resources.*;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("epicaddon")
public class EpicAddon
{
    public static final String MODID = "epicaddon";
    public static EpicAddon instance;
    // Directly reference a slf4j logger
    //private static final Logger LOGGER = LogUtils.getLogger();

    // public static final AnimationManager

    public EpicAddon()
    {
        instance = this;
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        //bus.addListener(CmdMgr::registerClientCommand);
        bus.addListener(this::setupCommon);
        bus.addListener(EpicAddonAnimations::registerAnimations);
        bus.addListener(RegEpicAddonSkills::registerSkills);
        bus.addListener(RegWeaponItemCap::register);
        //bus.addListener(RegModels::RegItemModelOverride);
        //bus.addListener(RegModels::RegItemEFMRenderer);

        EpicAddonSkillCategories.ENUM_MANAGER.loadPreemptive(EpicAddonSkillCategories.class);
        EpicAddonStyles.ENUM_MANAGER.loadPreemptive(EpicAddonStyles.class);
        //WeaponCategory.ENUM_MANAGER.load(CapabilityItem.WeaponCategories.class);
        RegItems.ITEMS.register(bus);
        RegParticle.PARTICLES.register(bus);
        RegEntity.ENTITIES.register(bus);
        //EpicFightMod.getInstance().animationManager.registerAnimations();

        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CfgMgr.CLIENT_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static EpicAddon getInstance(){
        return instance;
    }

    private void setupCommon(final FMLCommonSetupEvent event){
        if(FMLEnvironment.dist == Dist.CLIENT){
            BladeTrailTextureLoader.Load();
            ClientConfig.Load();
            EpicAddonAnimations.RegCamAnims();

            for (CamAnim camAnim: EpicAddonAnimations.CamAnimRegistry) {
                camAnim.load();
            }

        }
    }

/*
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        //LOGGER.info("-----Setup-----");
        //ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        //EpicFightMod.getInstance().animationManager.loadAnimationsInit(resourceManager);
        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        //LOGGER.info("HELLO from server starting");
    }
    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(modid = MODID)
    public static class RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            // Register a new block here
            LOGGER.info("HELLO from Register Block");
        }
    }

     */
}
