package com.jvn.epicaddon.utils;

import yesman.epicfight.api.client.animation.JointMask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JointMasks {
    public static final List<JointMask> ALL = new ArrayList(
            Arrays.asList(
                    JointMask.of("Root"),
                    JointMask.of("Thigh_R"),
                    JointMask.of("Leg_R"),
                    JointMask.of("Knee_R"),

                    JointMask.of("Thigh_L"),
                    JointMask.of("Leg_L"),
                    JointMask.of("Knee_L"),
                    JointMask.of("Torso"),

                    JointMask.of("Chest"),
                    JointMask.of("Head"),
                    JointMask.of("Shoulder_R"),
                    JointMask.of("Arm_R"),

                    JointMask.of("Hand_R"),
                    JointMask.of("Tool_R"),
                    JointMask.of("Elbow_R"),
                    JointMask.of("Shoulder_L"),

                    JointMask.of("Arm_L"),
                    JointMask.of("Hand_L"),
                    JointMask.of("Tool_L"),
                    JointMask.of("Elbow_L")
            ));
}
