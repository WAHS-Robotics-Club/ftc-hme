package org.firstinspires.ftc.team9202hme.hardware.driving;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

/**
 * Hardware interface for controlling the drive train
 * of a robot. This class makes the assumption that the
 * drive train can move in any direction and has access to
 * encoders
 *
 * @author Nathaniel Glover
 */
public abstract class DriveTrain extends HardwareComponent {
    /**
     * Sets motor powers to drive the robot based
     * on input from a gamepad. This is meant to be
     * used in {@link TeleOpProgram#loop()}
     *
     * @param controller The gamepad that will be used to control the robot.
     *                   It should ideally come from the OpMode in the
     *                   program that will be controlling the robot, as either
     *                   gamepad1 or gamepad2
     *
     * @see TeleOpProgram
     */
    public abstract void driveControlled(Gamepad controller);

    /**
     * Sets all motor powers to zero, effectively
     * bring the robot to a complete stop
     */
    public abstract void stop();

    /**
     * Sets the motor powers so that the robot
     * moves at the specified speed and angle
     *
     * @param power The power that will be applied
     *              to the motors, ranging from 0.0
     *              to 1.0
     * @param angle The angle, in degrees, where
     *              0 degrees is the front of the
     *              robot, at which the robot will
     *              move
     */
    public abstract void move(double power, double angle);

    /**
     * Sets the motor powers so that the robot
     * moves at the specified speed and angle,
     * until it has reached the specified
     * distance
     * <p>
     * <b>NOTE:</b> Do not catch the InterruptedException.
     * This method is intended for use in {@link AutonomousProgram#run()},
     * which will properly handle the InterruptedException should it occur.
     *
     * @param power The power that will be applied
     *              to the motors, ranging from 0.0
     *              to 1.0
     * @param angle The angle, in degrees, where
     *              0 degrees is the front of the
     *              robot, at which the robot will
     *              move
     * @param distance The distance, in millimeters,
     *                 that the robot will move
     *
     * @throws InterruptedException
     *
     * @see AutonomousProgram
     */
    public abstract void move(double power, double angle, double distance) throws InterruptedException;

    /**
     * Sets the motor powers so that the robot
     * spins
     *
     * @param power The power that will be applied
     *              to the motors, ranging from -1.0
     *              to 1.0, where a negative power
     *              will cause the robot to spin
     *              counter-clockwise
     */
    public abstract void turn(double power);

    /**
     * Sets the motor powers so that the robot spins,
     * until it reaches the desired angle
     * <p>
     * <b>NOTE:</b> Do not catch the InterruptedException.
     * This method is intended for use in {@link AutonomousProgram#run()},
     * which will properly handle the InterruptedException should it occur.
     *
     * @param power The power that will be applied
     *              to the motors, ranging from -1.0
     *              to 1.0, where a negative power
     *              will cause the robot to spin
     *              counter-clockwise
     * @param angle The angle in degrees to
     *              which the robot will turn,
     *              ranging from -359 to 359, where
     *              0 is the front of the robot
     *              and negative values cause
     *              a counter-clockwise turn
     * @throws InterruptedException
     *
     * @see AutonomousProgram
     */
    public abstract void turn(double power, double angle) throws InterruptedException;

    /**
     * Sets the motor powers so that the robot moves
     * at the desired angle, while simultaneously
     * spinning
     *
     * @param movePower The power that will be applied
     *                  to the motors, ranging from 0.0
     *                  to 1.0
     * @param angle The angle, in degrees, where
     *              0 degrees is the front of the
     *              robot, at which the robot will
     *              move
     * @param turnPower The power that will be applied
     *                  to the motors, ranging from -1.0
     *                  to 1.0, where a negative power
     *                  will cause the robot to spin
     *                  counter-clockwise
     */
    public abstract void moveAndTurn(double movePower, double angle, double turnPower);

    /**
     * Due to friction, most drive trains will start
     * stalling when given a motor power low enough
     *
     * @return The lowest power at which the drive train will be able to move
     */
    public abstract double getMinimumMovePower();

    /**
     * Due to friction, most drive trains will start
     * stalling when given a motor power low enough
     *
     * @return The lowest power at which the drive train will be able to turn
     */
    public abstract double getMinimumTurnPower();
}
