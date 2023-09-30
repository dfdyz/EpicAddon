package com.jvn.epicaddon.capability.JointLinker;

import com.jvn.epicaddon.api.ItemAnim.ItemAnimation;
import com.jvn.epicaddon.capability.EpicAddonCapabilities;
import com.jvn.epicaddon.register.RegItems;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class AnimatedItemMgr {
    protected RegItems.AnimatedItems.AnimatedItemModel animItem;
    protected Item activeItem;

    //protected ItemLinkAnimation linkAnimation = new ItemLinkAnimation();
    protected ItemAnimation animation;

    protected Pose lastPose = new Pose();
    protected Pose prevPose = new Pose();
    protected Pose curPose = new Pose();

    protected float linkTime = 0f;

    protected LivingEntity owner;
    protected boolean active = false;

    public AnimatedItemMgr(){
    }

    public void onConstruct(LivingEntity owner){
        this.owner = owner;
    }

    public void SetItem(ItemStack itemStack){
        //set animItem
        activeItem = itemStack.getItem();
    }

    public void SetActive(boolean a){
        active = a;
    }

    public boolean isEnabled(ItemStack itemStack){
        return activeItem == itemStack.getItem() && active;
    }

    public void onJoinWorld(Entity entityIn, EntityJoinWorldEvent event) {

    }

    public void playAnimation(ItemAnimation nextAnimation, LivingEntityPatch<?> entitypatch, float convertTime) {
        //Pose lastPose = curPose;
        animation.end(entitypatch, nextAnimation, false);
        nextAnimation.begin(entitypatch);
        animation = nextAnimation;
        linkTime = convertTime;
    }

    public void setPrevPose(Pose p){
        prevPose = p;
    }
    public void setCurrPose(Pose p){
        curPose = p;
    }

    public Pose getPose(float et, float pt){
        if(et < linkTime){
            Pose a = Pose.interpolatePose(prevPose, curPose, pt);
            return Pose.interpolatePose(lastPose, a, et/linkTime);
        }
        else {
            return Pose.interpolatePose(prevPose, curPose, pt);
        }
    }

    public OpenMatrix4f[] getTFM(Pose p){
        return animItem.getArmature().getPoseAsTransformMatrix(p);
    }

    public RegItems.AnimatedItems.AnimatedItemModel getCurrModel(){
        return animItem;
    }

    public void SetItemAnim(ItemAnimation animation){

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
