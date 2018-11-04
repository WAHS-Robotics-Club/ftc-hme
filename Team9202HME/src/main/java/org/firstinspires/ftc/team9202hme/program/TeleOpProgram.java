package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.util.TelemetryManager;

/**
 * Interface for creating programs to be used during
 * the driver controlled and endgame periods of the competition
 */
public abstract class TeleOpProgram {
    /**
     * The OpMode that will be running this program. Used for accessing
     * things like HardwareMap, Gamepad, and Telemetry
     */
    protected OpMode opMode;

    /**
     * Initializes protected members so that
     * they may be used by subclasses
     *
     * @param opMode The OpMode that will be running this program
     */
    public TeleOpProgram(OpMode opMode) {
        this.opMode = opMode;
    }

    protected abstract void initialize();

    /**
     * Code to be run when the driver presses the initialize button
     * on the driver station phone. Should be called in
     * {@link OpMode#init()}
     */
    public void init() {
        TelemetryManager.setTelemetryInstance(opMode.telemetry);
        initialize();
    }

    /**
     * Code to be run when the driver presses the play button
     * on the driver station phone. Should be called in
     * {@link OpMode#start()}
     */
    public void start() {

    }

    /**
     * Called in {@link TeleOpProgram#loop()} as part of repeating update sequence
     */
    protected abstract void update();

    /**
     * Code that will update continuously as the OpMode runs, until
     * a stop is requested or it crashes. Should be called in
     * {@link OpMode#loop()}
     */
    public void loop() {
        update();
        TelemetryManager.getTelemetry().update();
    }

    /**
     * Code to be run when the driver presses the stop button
     * on the driver station phone. Should be called in
     * {@link OpMode#stop()}
     */
    public void stop() {

    }
}
