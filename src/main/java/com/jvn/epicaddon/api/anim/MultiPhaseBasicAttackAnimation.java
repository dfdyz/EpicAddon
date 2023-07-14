package com.jvn.epicaddon.api.anim;

import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.model.Armature;

public class MultiPhaseBasicAttackAnimation extends AttackAnimation {
    public MultiPhaseBasicAttackAnimation(float convertTime, String path, Armature model, Phase... phases) {
        super(convertTime, path, model, phases);
    }

    @Override
    public boolean isBasicAttackAnimation() {
        return true;
    }

}
