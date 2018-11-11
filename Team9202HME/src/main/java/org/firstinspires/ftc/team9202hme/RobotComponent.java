package org.firstinspires.ftc.team9202hme;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

/**
 * An interface for initializing components (hardware or sensory software)
 * on the robot, and logging any necessary data
 * to the telemetry
 */
public abstract class RobotComponent {
    /**
     * Initializes the class's members that are tied
     * to physical hardware devices on the robot
     *
     * @param hardwareMap The hardware map that will be used to
     *                    initialize hardware devices. It should
     *                    ideally come from the OpMode in the
     *                    program that will be using the hardware
     * @see OpMode
     * @see TeleOpProgram
     * @see AutonomousProgram
     */
    public abstract void init(HardwareMap hardwareMap);

    /**
     * Logs any data that the class may wish to show,
     * such as hardware status or sensor readings to
     * the telemetry, so that it may be viewed on the
     * driver station phone
     *
     * @param telemetry The telemetry that will receive the desired
     *                  data. It should ideally come from the OpMode
     *                  in the program that will be displaying the
     *                  data to the driver station phone
     * @see OpMode
     * @see TeleOpProgram
     * @see AutonomousProgram
     */
    public abstract void logTelemetry(Telemetry telemetry);
}