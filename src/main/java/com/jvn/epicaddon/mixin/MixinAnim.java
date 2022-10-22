package com.jvn.epicaddon.mixin;

import com.jvn.epicaddon.renderer.SwordTrail.IAnimST;
import com.jvn.epicaddon.tools.Trail;
import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;

@Mixin(value = AttackAnimation.class)
public class MixinAnim implements IAnimST {
    protected Trail trail;
    protected boolean Special = false;

    @Override
    public boolean isSpecial() {
        return Special;
    }

    @Override
    public Trail getTrail() {
        return trail;
    }

    @Override
    public IAnimST SetTrail(Trail tr) {
        trail = tr;
        return this;
    }

    @Override
    public IAnimST SetSpecial(boolean i) {
        Special = i;
        return this;
    }

}
