package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.util.TelemetryManager;

/**
 * Interface for creating programs to be used during
 * the autonomous period of the competition
 */
public abstract class AutonomousProgram {
    /**
     * The two possible alliance colors, which determine location on the field
     */
    public enum AllianceColor {
        RED, BLUE, DONT_CARE;

        @Override
        public String toString() {
            switch(this) {
                case RED: return "Red";
                case BLUE: return "Blue";
                case DONT_CARE: return "";
                default: return "";
            }
        }
    }

    /**
     * The two possible field positions on a given alliance
     */
    public enum FieldPosition {
        CRATER_FACING, DEPOT_FACING, DONT_CARE;

        @Override
        public String toString() {
            switch(this) {
                case CRATER_FACING: return "Crater";
                case DEPOT_FACING: return "Depot";
                case DONT_CARE: return "";
                default: return "";
            }
        }
    }

    /**
     * The LinearOpMode that will be running this program. Used for accessing
     * things like HardwareMap, Gamepad, and Telemetry
     */
    final protected LinearOpMode opMode;

    /**
     * The side of the field that the robot will be on when this program is run
     */
    protected AllianceColor allianceColor;

    /**
     * The position on the field that the robot will be in when this program is run
     */
    protected FieldPosition fieldPosition;

    /**
     * Initializes protected members so that they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param allianceColor The side of the field that the robot will be on
     *                  when this program is run
     * @param fieldPosition The position on the field that the robot will be in
     *                      when this program is run
     */
    public AutonomousProgram(LinearOpMode opMode, AllianceColor allianceColor, FieldPosition fieldPosition) {
        this.opMode = opMode;
        this.allianceColor = allianceColor;
        this.fieldPosition = fieldPosition;
    }

    protected abstract void initialize() throws InterruptedException;

    protected abstract void start() throws InterruptedException;

    /**
     * Runs the program. <b>NOTE:</b> Do not catch the InterruptedException.
     * This method is intended for use in {@link LinearOpMode#runOpMode()},
     * which will properly handle the InterruptedException should it occur.
     *
     * @throws InterruptedException Will be thrown if the robot is stopped while the thread is sleeping;
     *                              a very common occurrence in autonomous mode, and {@link LinearOpMode#runOpMode()}
     *                              should handle it properly
     */
    public void run() throws InterruptedException {
        TelemetryManager.setTelemetryInstance(opMode.telemetry);
        initialize();
        opMode.waitForStart();
        start();
    }
}
