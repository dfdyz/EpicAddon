package com.jvn.epicaddon.renderer.SwordTrail;

import com.jvn.epicaddon.tools.Trail;

public interface IAnimST {
    boolean isSpecial();
    Trail getTrail();

    IAnimST SetTrail(Trail tr);

    IAnimST SetSpecial(boolean i);

}
