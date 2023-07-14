package com.jvn.epicaddon.command;

import com.jvn.epicaddon.EpicAddon;
import com.jvn.epicaddon.api.camera.CamAnim;
import com.jvn.epicaddon.resources.BladeTrailTextureLoader;
import com.jvn.epicaddon.resources.EpicAddonAnimations;
import com.jvn.epicaddon.resources.config.ClientConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;

public class CmdMgr {
    protected static LiteralArgumentBuilder<CommandSourceStack> command;


    public static void MSGClient(String str){
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            player.displayClientMessage(Component.nullToEmpty(str),false);
        }
    }

    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        command = Commands.literal(EpicAddon.MODID).executes(context -> {
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player != null) {
                        player.displayClientMessage(Component.nullToEmpty("Reload\nSwordTrail <boolean>\nHealthBar <boolean>\nOptFineMode <boolean> --W.I.P."),false);
                    }
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("Reload")
                        .executes(context -> {
                            ClientConfig.Load();
                            BladeTrailTextureLoader.ReleaseAll();
                            BladeTrailTextureLoader.Load();
                            for (CamAnim camAnim: EpicAddonAnimations.CamAnimRegistry) {
                                camAnim.load();
                            }
                            MSGClient("[EpicAddon]Reload All Config.");
                            return Command.SINGLE_SUCCESS;
                        }))
                /*
                .then(Commands.literal("SwordTrail")
                        .executes(context -> {
                            MSGClient("[EpicAddon]SwordTrail Render: " + ClientConfig.cfg.EnableSwordTrail);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Enabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Disabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                 */
                .then(Commands.literal("DeathParticle")
                        .executes(context -> {
                            MSGClient("[EpicAddon]DeathParticle: " + ClientConfig.cfg.EnableDeathParticle);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableDeathParticle = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Enabled DeathParticle.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableDeathParticle = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Disabled DeathParticle.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("GenShinVoice")
                        .executes(context -> {
                            MSGClient("[EpicAddon]GenShinVoice: " + (ClientConfig.cfg.EnableGenShinVoice ? "Enable" : "Disable"));
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableGenShinVoice = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Enabled GenShinVoice.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableGenShinVoice = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Disabled GenShinVoice.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("HealthBar")
                        .executes(context -> {
                            MSGClient("[EpicAddon]HealthBar Render: " + ClientConfig.cfg.EnableHealthBar);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Enabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Disabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("RenderSelf")
                                .executes(context -> {
                                    MSGClient("[EpicAddon]Rendering Health Bar for self: " + ClientConfig.cfg.RenderHealthBarSelf);
                                    return Command.SINGLE_SUCCESS;
                                }).then(Commands.literal("true")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = true;
                                            ClientConfig.SaveCommon();
                                            MSGClient("[EpicAddon]Enabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        }))
                                .then(Commands.literal("false")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = false;
                                            ClientConfig.SaveCommon();
                                            MSGClient("[EpicAddon]Disabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                /*
                .then(Commands.literal("OptFineMode")
                        .executes(context -> {
                            MSGClient("[EpicAddon]OptFineMode: " + ClientConfig.cfg.EnableOptFineMode);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = true;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Enabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = false;
                                    ClientConfig.SaveCommon();
                                    MSGClient("[EpicAddon]Disabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                })))

                 */
        ;



        dispatcher.register(command);
    }
}
