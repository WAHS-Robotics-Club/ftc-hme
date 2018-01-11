package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

/**
 * Class representing a holonomic drive train (capable of omni-directional movement).
 * Assumes that there are four motors present, each in a different corner of a rectangular
 * robot. Also assumes that each motor has access to an encoder and is plugged into a REV
 * Robotics expansion hub.
 */
public abstract class OmniDirectionalDrive extends HardwareComponent {
    protected final double wheelCircumference;
    protected final int encoderTicksPerRotation;
    protected DcMotorEx frontLeft, frontRight, backLeft, backRight;

    /**
     * Gives drive train the values it needs to calculate how to properly apply motor powers
     * and handle encoder positions when moving and turning autonomously
     *
     * @param wheelDiameter           The diameter of the robot's wheels; unit is unnecessary as long as it is consistent with other distances
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     *                                Neverest 40's, then this value is 1120
     */
    public OmniDirectionalDrive(double wheelDiameter, int encoderTicksPerRotation) {
        wheelCircumference = Math.PI * wheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
    }

    /**
     * Sets the run mode for all four motors
     *
     * @param runMode The run mode (reset encoders, run to position, etc.)
     */
    protected void setRunMode(DcMotor.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    /**
     * Resets encoder positions to 0 for all four motors. Motors will briefly
     * stop receiving power on reset.
     */
    protected void resetEncoders() {
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        frontLeft = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_LEFT);
        frontRight = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_RIGHT);
        backLeft = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_LEFT);
        backRight = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_RIGHT);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setTargetPositionTolerance(12);
        frontRight.setTargetPositionTolerance(12);
        backLeft.setTargetPositionTolerance(12);
        backRight.setTargetPositionTolerance(12);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        stop();
    }

    /**
     * Returns the angle at which the robot is currently turned relative
     * to its position on initialization. This angle is on the plane on
     * which the robot is driving.
     *
     * @return The heading, ranging from 0 to 359, where increasing angles
     * correspond to counter-clockwise rotation
     */
    public abstract double getHeading();

    /**
     * Sets motor powers to drive the robot based
     * on input from a gamepad. This is meant to be
     * used in {@link TeleOpProgram#loop()}
     *
     * @param gamepad The gamepad that will be used to control the robot.
     *                It should ideally come from the OpMode in the
     *                program that will be controlling the robot, as either
     *                gamepad1 or gamepad2
     * @see TeleOpProgram
     */
    public abstract void driveControlled(Gamepad gamepad);

    /**
     * Sets all motor powers to zero, effectively
     * bring the robot to a complete stop
     */
    public void stop() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

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
     * Sets the motor powers so that the robot moves
     * at the desired angle, while simultaneously
     * spinning
     *
     * @param movePower The power that will be applied
     *                  to the motors, ranging from 0.0
     *                  to 1.0
     * @param angle     The angle, in degrees, where
     *                  0 degrees is the front of the
     *                  robot, at which the robot will
     *                  move
     * @param turnPower The power that will be applied
     *                  to the motors, ranging from -1.0
     *                  to 1.0, where a negative power
     *                  will cause the robot to spin
     *                  counter-clockwise
     */
    public abstract void moveAndTurn(double movePower, double angle, double turnPower);

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
     * @param power    The power that will be applied
     *                 to the motors, ranging from 0.0
     *                 to 1.0
     * @param angle    The angle, in degrees, where
     *                 0 degrees is the front of the
     *                 robot, at which the robot will
     *                 move
     * @param distance The distance, in millimeters,
     *                 that the robot will move
     * @throws InterruptedException This method will put the current thread to sleep while the motors are moving to the target distance,
     *                              which will throw an InterruptedException if the thread is terminated
     * @see AutonomousProgram
     */
    public abstract void move(double power, double angle, double distance) throws InterruptedException;

    /**
     * Sets the motor powers so that the robot spins,
     * until it reaches the desired angle relative to its
     * position when the method is called. For instance, if
     * the robot was tilted 30 degrees and told to turn 50, its
     * final heading would be 80.
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
     *              and positive values indicate
     *              an angle left of 0
     * @throws InterruptedException This method will put the current thread to sleep while the robot is turning to the target angle,
     *                              which will throw an InterruptedException if the thread is terminated
     * @see AutonomousProgram
     */
    public abstract void turn(double power, double angle) throws InterruptedException;

    /**
     * Sets motor powers so that the robot spins,
     * until its heading reaches the desired angle relative
     * to its original position at initialization. For instance,
     * if the robot was tilted 30 degrees and told to turn to 50,
     * its final heading would be 50.
     *
     * @param power The power that will be applied to
     *              the motors, ranging from -1.0 to 1.0,
     *              where a negative power will cause the
     *              robot to spin counter-clockwise
     * @param angle The absolute angle in degrees to which
     *              the robot will turn, ranging from 0 to
     *              359, where 0 is the robot's position at
     *              initialization and increasing angles respond
     *              to counter-clockwise rotation
     * @throws InterruptedException This method will put the current thread to sleep while the robot is turning to the target angle,
     *                              which will throw an InterruptedException if the thread is terminated
     */
    public abstract void absoluteTurn(double power, double angle) throws InterruptedException;

    /**
     * Due to friction, most drive trains' motors will stall
     * if given a sufficiently low power, which this method returns
     *
     * @return The lowest absolute power at which the drive train will be able to move
     */
    public abstract double getMinimumMovePower();

    /**
     * Due to friction, most drive trains' motors will stall
     * if given a sufficiently low power, which this method returns
     *
     * @return The lowest absolute power at which the drive train will be able to turn
     */
    public abstract double getMinimumTurnPower();
}
