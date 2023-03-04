package com.jvn.epicaddon.register;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class WeaponCollider {
    public static final Collider SAO_SWORD = new MultiOBBCollider(3, 0.6, 1.0, 1.4, 0.0, 0.0, -0.8D);
    public static final Collider SAO_SWORD_HUGE_L = new MultiOBBCollider(3, 1.0D, 1.0D, 1.8D, 0.7D, 0.0D, -0.2D);//
    public static final Collider SAO_SWORD_HUGE_R = new MultiOBBCollider(3, 1.0D, 1.0D, 1.8D, -0.7D, 0.0D, -0.2D);
    public static final Collider SAO_SWORD_DASH = new MultiOBBCollider(3, 1.56D, 1.1D, 1.8D, 0.0D, 0.8D, -0.732D);
    public static final Collider SAO_RAPIER_DASH = new MultiOBBCollider(8, 1.56D, 1.1D, 2.5D, 0.0D, 0.7D, -1.2D);

    public static final Collider GenShin_Bow_scan = new MultiOBBCollider(2, 6D, 4D, 10D, 0.0D, 1D, -10.5D);

    public static final Collider SAO_SWORD_DUAL_AUTO10 = new MultiOBBCollider(3, 1.1D, 1.1D, 1.8D, 0.0D, 0.8D, -0.732D);

    public static final Collider SAO_RAPIER_DASH_SHORT = new MultiOBBCollider(3, 0.6, 0.8, 1.4, 0.0, 0.9D, -0.98D);

    public static final Collider SAO_SWORD_AIR = new MultiOBBCollider(6, 1.75D, 1.2D, 1.8D, 0.0D, 1.6D, -0.7D);

    // new MultiOBBCollider(4, 0.6, 1.0, 2, 0.0, 0.0, -0.2);
}
