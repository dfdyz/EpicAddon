package com.jvn.epicaddon.item;

import com.jvn.epicaddon.command.CmdMgr;
import com.mojang.authlib.minecraft.TelemetrySession;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class CategoriesChangeItem extends Item {
    public final String types[];
    public CategoriesChangeItem(Properties properties,String... types) {
        super(properties);
        this.types = types.clone();
    }

    protected void setType(String typeName,ItemStack stack){
        CompoundTag tags = stack.getOrCreateTag();
        tags.putString("epicaddon_type",typeName);
    }

    protected int getOffHandIdx(ItemStack stack,String type){
        if(stack.getItem() instanceof CategoriesChangeItem){
            for (int i=0; i<((CategoriesChangeItem)(stack.getItem())).types.length;++i) {
                if(((CategoriesChangeItem)(stack.getItem())).types[i] == type) return i;
            }
        }
        return -1;
    }

    protected int getTypeIdx(ItemStack stack){
        CompoundTag tags = stack.getOrCreateTag();
        int a = tags.getShort("epicaddon_typeidx");
        if(a <= 0) tags.putShort("epicaddon_typeidx", (short) 1);
        return a-1;
    }

    protected void setTypeIdx(ItemStack stack,int idx){
        stack.getOrCreateTag().putShort("epicaddon_typeidx", (short) (idx+1));
    }

    protected void updateItem(Player playerIn, InteractionHand hand){
        ItemStack itemstack = playerIn.getItemInHand(hand);
        if(playerIn.isShiftKeyDown()){
            if(hand == InteractionHand.MAIN_HAND){
                PlayerPatch playerpatch = (PlayerPatch)playerIn.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).orElse((PlayerPatch) null);
                CapabilityItem fromCap =  playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);

                int a = getTypeIdx(itemstack);
                if (++a >= types.length) a=0;
                setTypeIdx(itemstack,a);
                setType(types[a],itemstack);
                playerpatch.updateHeldItem(fromCap,
                        EpicFightCapabilities.getItemStackCapability(itemstack),
                        itemstack,itemstack,InteractionHand.MAIN_HAND);
                if(FMLEnvironment.dist == Dist.CLIENT && playerIn instanceof LocalPlayer) playerIn.displayClientMessage(Component.nullToEmpty("MainHand Type Change To: "+types[a]),false);

                ItemStack stackOffHand = playerIn.getItemInHand(InteractionHand.OFF_HAND);
                if(stackOffHand != ItemStack.EMPTY && stackOffHand.getItem() instanceof CategoriesChangeItem) {
                    int idxOff = getOffHandIdx(stackOffHand,types[a]);
                    if(idxOff != -1){
                        CapabilityItem fromCapOff = playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND);
                        setTypeIdx(stackOffHand,idxOff);
                        setType(types[a],stackOffHand);
                        playerpatch.updateHeldItem(fromCapOff,
                                EpicFightCapabilities.getItemStackCapability(stackOffHand),
                                itemstack,itemstack,InteractionHand.OFF_HAND);
                        if(FMLEnvironment.dist == Dist.CLIENT && playerIn instanceof LocalPlayer) playerIn.displayClientMessage(Component.nullToEmpty("OffHand Type Change To: "+types[a]),false);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
        updateItem(playerIn,hand);
        return InteractionResultHolder.pass(playerIn.getItemInHand(hand));
    }
}
