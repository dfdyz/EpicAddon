package com.jvn.epicaddon.api.forgeevent;

import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class RenderHandItemLayerEvent extends Event {
    public final LivingEntityPatch<?> entitypatch;
    public final float partialTicks;
    public final InteractionHand hand;

    public RenderHandItemLayerEvent(LivingEntityPatch<?> ep, InteractionHand hand, float partialTicks){
        entitypatch = ep;
        this.partialTicks = partialTicks;
        this.hand = hand;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
