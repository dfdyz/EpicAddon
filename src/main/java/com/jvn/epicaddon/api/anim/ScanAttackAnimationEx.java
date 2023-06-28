package com.jvn.epicaddon.api.anim;

import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Model;

public class ScanAttackAnimationEx extends ScanAttackAnimation implements IRenderInvisible{
    public ScanAttackAnimationEx(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, String scanner, String path, Model model) {
        super(convertTime, antic, contact, recovery, hand, collider, scanner, path, model);
    }

    public ScanAttackAnimationEx(float convertTime, float antic, float contact, float recovery, InteractionHand hand, boolean MoveCancel, int maxStrikes, @Nullable Collider collider, String scanner, String path, Model model) {
        super(convertTime, antic, contact, recovery, hand, MoveCancel, maxStrikes, collider, scanner, path, model);
    }

    public ScanAttackAnimationEx(float convertTime, float antic, float contact, float recovery, InteractionHand hand, int maxStrikes, @Nullable Collider collider, String scanner, String path, Model model) {
        super(convertTime, antic, contact, recovery, hand, maxStrikes, collider, scanner, path, model);
    }

    protected InvisiblePhase invisiblePhase;
    public ScanAttackAnimationEx SetInvisiblePhase(InvisiblePhase p){
        invisiblePhase = p;
        return this;
    }

    public ScanAttackAnimationEx SetInvisiblePhase(float start, float end){
        invisiblePhase = new InvisiblePhase(start,end);
        return this;
    }

    @Override
    public boolean shouldRender(float et) {
        if (invisiblePhase == null) return true;
        else return et < invisiblePhase.start || et > invisiblePhase.end;
    }

    public record InvisiblePhase(float start, float end) { }
}
