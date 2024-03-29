package com.jvn.epicaddon;

import com.jvn.epicaddon.api.PostEffect.ShaderProgram;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import com.jvn.epicaddon.events.ControllerEvent;
import com.jvn.epicaddon.events.reloader.Config2SkinReloader;
import com.jvn.epicaddon.network.EpicaddonNetMgr;
import com.jvn.epicaddon.register.*;
import com.jvn.epicaddon.resources.*;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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
    public static final Logger LOGGER = LogUtils.getLogger();

    // public static final AnimationManager

    public EpicAddon()
    {
        instance = this;
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.CLIENT_CONFIG);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus fg_bus = MinecraftForge.EVENT_BUS;

        //bus.addListener(CmdMgr::registerClientCommand);
        bus.addListener(this::setupCommon);

        if(FMLEnvironment.dist == Dist.CLIENT){
            bus.addListener(this::initPostEffect);
            bus.addListener(this::regClientReloader);
            bus.addListener(this::InitShaders);
        }

        bus.addListener(EpicAddonAnimations::registerAnimations);
        bus.addListener(RegWeaponItemCap::register);


        //bus.addListener(RegModels::RegItemModelOverride);
        bus.addListener(RegModels::RegItemEFMRenderer);
        bus.addListener(EpicAddonCapabilities::registerCapabilities);


        EpicAddonSkillCategories.ENUM_MANAGER.loadPreemptive(EpicAddonSkillCategories.class);
        EpicAddonStyles.ENUM_MANAGER.loadPreemptive(EpicAddonStyles.class);
        EpicAddonSkillSlots.ENUM_MANAGER.loadPreemptive(EpicAddonSkillSlots.class);
        RegWeaponItemCap.EpicAddonWeaponCategories.ENUM_MANAGER.loadPreemptive(RegWeaponItemCap.EpicAddonWeaponCategories.class);

        //WeaponCategory.ENUM_MANAGER.load(CapabilityItem.WeaponCategories.class);
        RegItems.ITEMS.register(bus);
        RegParticle.PARTICLES.register(bus);
        RegEntity.ENTITIES.register(bus);
        RegMobEffect.EFFECTS.register(bus);
        //EpicFightMod.getInstance().animationManager.registerAnimations();

        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CfgMgr.CLIENT_CONFIG);
        fg_bus.register(this);
        fg_bus.addListener(RegEpicAddonSkills::BuildSkills);
    }
    public void setupCommon(final FMLCommonSetupEvent event){
        event.enqueueWork(EpicaddonNetMgr::register);
        event.enqueueWork(RegEpicAddonSkills::registerSkills);
        if(FMLEnvironment.dist == Dist.CLIENT){
            //BladeTrailTextureLoader.Load();
            ClientConfig.Load(false);
            EpicAddonAnimations.RegCamAnims();

            for (CamAnim camAnim: EpicAddonAnimations.CamAnimRegistry) {
                camAnim.load();
            }
            RegPostEffect.Reg();
            ControllerEvent.EpicAddonKeyMapping.Reg();
        }
        //event.enqueueWork(EpicAddonNetworkManager::registerPackets);
    }

    @OnlyIn(Dist.CLIENT)
    public void regClientReloader(final RegisterClientReloadListenersEvent event){
        event.registerReloadListener(new Config2SkinReloader());
    }

    @OnlyIn(Dist.CLIENT)
    public void initPostEffect(final RegisterShadersEvent event){
        EpicAddon.LOGGER.info("Register PostEffect");
        RegPostEffect.Registries.forEach(obj -> {
            obj.Init();
            EpicAddon.LOGGER.info("Init PostEffect: "+obj.hashCode());
        });
    }

    @OnlyIn(Dist.CLIENT)
    public void InitShaders(final RegisterShadersEvent event){
        EpicAddon.LOGGER.info("Register Effect Shader");
        ShaderProgram.LoadAll();
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
