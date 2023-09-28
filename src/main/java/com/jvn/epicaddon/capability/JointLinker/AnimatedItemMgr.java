package com.jvn.epicaddon.capability.JointLinker;

import com.jvn.epicaddon.api.ItemAnim.ItemAnimationPlayer;
import com.jvn.epicaddon.api.ItemAnim.ItemAnimator;
import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import com.jvn.epicaddon.events.CapabilityEvent;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Function;

public class AnimatedItemMgr {
    protected Armature armature;
    protected ItemAnimator animator;

    protected LivingEntity owner;
    protected boolean active = false;

    public AnimatedItemMgr(){
    }

    public void onConstruct(LivingEntity owner){
        this.owner = owner;

    }

    public void SetItem(ItemStack itemStack){
        //set armature & animator

    }

    public void SetActive(boolean a){
        active = a;
    }

    public void onJoinWorld(Entity entityIn, EntityJoinWorldEvent event) {

    }

    public ItemAnimator getAnimator(){
        return animator;
    }


    public static class Provider implements ICapabilityProvider, NonNullSupplier<AnimatedItemMgr> {
        private LazyOptional<AnimatedItemMgr> optional = LazyOptional.of(this);
        private AnimatedItemMgr cap;

        public Provider(LivingEntity entity){
            this.cap = new AnimatedItemMgr();
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return cap == EpicAddonCapabilities.ITEM_ANIMATOR ? optional.cast() : LazyOptional.empty();
        }

        @NotNull
        @Override
        public AnimatedItemMgr get() {
            return cap;
        }
    }
}
