package com.jvn.epicaddon.command;

import com.jvn.epicaddon.EpicAddon;
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
import net.minecraftforge.fml.common.Mod;

public class CmdMgr {
    protected static LiteralArgumentBuilder<CommandSourceStack> command;


    public static void MSG(String str){
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
                        player.displayClientMessage(Component.nullToEmpty("Reload\nSwordTrail <boolean>\nHealthBar <boolean>\nOptFineMode <boolean>"),false);
                    }
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("Reload")
                        .executes(context -> {
                            ClientConfig.Load();
                            MSG("[EpicAddon]Reload All Config.");
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(Commands.literal("SwordTrail")
                        .executes(context -> {
                            MSG("[EpicAddon]SwordTrail Render: " + ClientConfig.cfg.EnableSwordTrail);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = true;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicAddon]Enabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = false;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicAddon]Disabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("HealthBar")
                        .executes(context -> {
                            MSG("[EpicAddon]HealthBar Render: " + ClientConfig.cfg.EnableHealthBar);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = true;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicAddon]Enabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableHealthBar = false;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicAddon]Disabled Health Bar Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("RenderSelf")
                                .executes(context -> {
                                    MSG("[EpicAddon]Rendering Health Bar for self: " + ClientConfig.cfg.RenderHealthBarSelf);
                                    return Command.SINGLE_SUCCESS;
                                }).then(Commands.literal("true")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = true;
                                            ClientConfig.SaveCommon();
                                            MSG("[EpicAddon]Enabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        }))
                                .then(Commands.literal("false")
                                        .executes(context -> {
                                            ClientConfig.cfg.RenderHealthBarSelf = false;
                                            ClientConfig.SaveCommon();
                                            MSG("[EpicAddon]Disabled Rendering Health Bar for self.");
                                            return Command.SINGLE_SUCCESS;
                                        })
                                )
                        )
                )
                .then(Commands.literal("OptFineMode")
                        .executes(context -> {
                            MSG("[EpicAddon]OptFineMode: " + ClientConfig.cfg.EnableOptFineMode);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = true;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicAddon]Enabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = false;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicAddon]Disabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                })))
        ;



        dispatcher.register(command);
    }
}
