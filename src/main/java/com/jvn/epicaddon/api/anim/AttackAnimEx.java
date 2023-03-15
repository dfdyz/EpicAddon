package com.jvn.epicaddon.api.anim;

import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;

public class AttackAnimEx extends AttackAnimation {
    public AttackAnimEx(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, collider, index, path, model);
    }

    public AttackAnimEx(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, String index, String path, Model model) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, index, path, model);
    }

    public AttackAnimEx(float convertTime, String path, Model model, Phase... phases) {
        super(convertTime, path, model, phases);
    }


    public static class PhaseEx extends AttackAnimation.Phase{

        public PhaseEx(float start, float antic, float contact, float recovery, float end, String jointName, Collider collider) {
            super(start, antic, contact, recovery, end, jointName, collider);
        }

        public PhaseEx(float start, float antic, float contact, float recovery, float end, InteractionHand hand, String jointName, Collider collider) {
            super(start, antic, contact, recovery, end, hand, jointName, collider);
        }

        public PhaseEx(float start, float antic, float preDelay, float contact, float recovery, float end, String jointName, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, jointName, collider);
        }

        public PhaseEx(float start, float antic, float preDelay, float contact, float recovery, float end, InteractionHand hand, String jointName, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, hand, jointName, collider);
        }

        public String getJoint(){
            return this.jointName;
        }
        public float getContact(){
            return this.contact;
        }
    }

}
