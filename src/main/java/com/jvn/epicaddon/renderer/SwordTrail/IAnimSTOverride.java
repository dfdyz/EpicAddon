package com.jvn.epicaddon.renderer.SwordTrail;

import com.jvn.epicaddon.utils.Trail;

public interface IAnimSTOverride {
    boolean isColorOverride();
    boolean isPosOverride();
    boolean isLifetimeOverride();
    boolean isEnable();
    Trail getTrail();
    IAnimSTOverride setColorOverride(Trail tr);
    IAnimSTOverride setPosOverride(Trail tr);
    IAnimSTOverride setLifeTimeOverride(int lt);
    void EnableST(boolean a);
}
