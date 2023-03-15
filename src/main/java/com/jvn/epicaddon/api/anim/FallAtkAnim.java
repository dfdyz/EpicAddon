package com.jvn.epicaddon.api.anim;

import net.minecraft.server.packs.resources.ResourceManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

public class FallAtkAnim extends StaticAnimation {
    public final StaticAnimation Start;
    public final StaticAnimation Loop;
    public final StaticAnimation End;

    public FallAtkAnim(StaticAnimation start, StaticAnimation loop, StaticAnimation end){
        Start = start;
        Loop = loop;
        End = end;
    }
}
