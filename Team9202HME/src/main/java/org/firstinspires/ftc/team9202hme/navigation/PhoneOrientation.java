package org.firstinspires.ftc.team9202hme.navigation;

/**
 * The four possible orientations of the robot controller
 * phone on the robot. These are necessary to give proper
 * readings of an image's location in 3D space
 */
public enum PhoneOrientation {
    /**
     * The phone is facing upright
     */
    UPRIGHT,
    /**
     * The phone is flipped upside down
     */
    UPSIDE_DOWN,
    /**
     * The phone is on its side, with the charger port
     * facing upwards
     */
    VOLUME_SIDE_DOWN,
    /**
     * The phone is on its side, with the volume buttons
     * facing upwards
     */
    VOLUME_SIDE_UP
}
