package org.firstinspires.ftc.team9202hme.motion;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;
import org.firstinspires.ftc.team9202hme.util.Vector2;

/**
 * Class representing a holonomic drive train (capable of omni-directional movement).
 * Assumes that there are four motors present, each in a different corner of a rectangular
 * robot. Also assumes that each motor has access to an encoder and is plugged into a REV
 * Robotics expansion hub.
 */
public abstract class HolonomicDriveTrain extends RobotComponent {
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
    public HolonomicDriveTrain(double wheelDiameter, int encoderTicksPerRotation) {
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
     * @return The heading, ranging from -180 to 180, where increasing angles
     * correspond to counter-clockwise rotation
     */
    public abstract double getHeading();

    public abstract void move(Vector2 velocity, double turnPower);

    public void move(Vector2 velocity) {
        move(velocity, 0);
    }

    public void move(double direction, double movePower, double turnPower) {
        move((new Vector2(Math.cos(Math.toRadians(direction)), Math.sin(Math.toRadians(direction)))).times(movePower), turnPower);
    }

    public void move(double direction, double movePower) {
        move(direction, movePower, 0);
    }

    public void turn(double power) {
        move(Vector2.ZERO, power);
    }

    public abstract void moveToDisplacement(Vector2 displacement, double movePower) throws InterruptedException;

    public void moveToDisplacement(double direction, double distance, double movePower) throws InterruptedException {
        moveToDisplacement((new Vector2(Math.cos(Math.toRadians(direction)), Math.sin(Math.toRadians(direction)))).times(distance), movePower);
    }

    public abstract void turnToHeading(double heading) throws InterruptedException;

    public void rotateBy(double degrees) throws InterruptedException {
        turnToHeading(getHeading() + degrees);
    }

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
}
