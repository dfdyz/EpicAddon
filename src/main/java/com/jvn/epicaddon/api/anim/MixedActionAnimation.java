package com.jvn.epicaddon.api.anim;

import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.model.Model;

public class MixedActionAnimation extends ActionAnimation {
    public MixedActionAnimation(float convertTime, String path, Model model) {
        super(convertTime, path, model);
    }

    public MixedActionAnimation(float convertTime, float postDelay, String path, Model model) {
        super(convertTime, postDelay, path, model);
    }

    public EntityState getStateEx(float t){
        return getState(t);
    }
}
