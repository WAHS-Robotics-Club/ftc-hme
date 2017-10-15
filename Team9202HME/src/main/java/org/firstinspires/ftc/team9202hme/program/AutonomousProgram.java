package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Interface for creating programs to be used during
 * the autonomous period of the competition
 *
 * @author Nathaniel Glover
 */
public abstract class AutonomousProgram {
    /**
     * The two possible sides of the field
     *
     * @author Nathaniel Glover
     */
    public enum FieldSide {
        RED, BLUE
    }

    /**
     * The LinearOpMode that will be running this program. Used for accessing
     * things like HardwareMap, Gamepad, and Telemetry
     */
    final protected LinearOpMode opMode;

    /**
     * The side of the field that the robot will be on when this program is run
     */
    protected FieldSide fieldSide;

    /**
     * Initializes protected members so that
     * they may be used by subclasses
     *
     * @param opMode The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     *                  when this program is run
     */
    public AutonomousProgram(LinearOpMode opMode, FieldSide fieldSide) {
        this.opMode = opMode;
        this.fieldSide = fieldSide;
    }

    /**
     * Runs the program. <b>NOTE:</b> Do not catch the InterruptedException.
     * This method is intended for use in {@link LinearOpMode#runOpMode()},
     * which will properly handle the InterruptedException should it occur.
     *
     * @throws InterruptedException Will be thrown if the robot is stopped while the thread is sleeping;
     *                              a very common occurrence in autonomous mode, and {@link LinearOpMode#runOpMode()}
     *                              should handle it properly
     */
    public abstract void run() throws InterruptedException;
}
