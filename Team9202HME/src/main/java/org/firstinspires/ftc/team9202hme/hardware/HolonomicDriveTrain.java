package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.math.PowerScale;
import org.firstinspires.ftc.team9202hme.math.Vector2;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

/**
 * Drive train made for robots using the holonomic drive
 * configuration. This class assumes that the wheels are
 * in "X" configuration, where wheels are at 45 degree
 * angles on the corners of the robot. It also assumes
 * that every wheel is the same diameter, and each
 * corresponding motor has the same number of encoder
 * ticks per rotation
 *
 * @author Nathaniel Glover
 * @author John Eichelberger
 * @author Sage Wibberley
 */
//TODO: Make rotations more mathematically consistent, such that positive angles correspond to counter-clockwise rotations
public class HolonomicDriveTrain extends HardwareComponent {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private GyroSensor gyroSensor;

    private final double mmWheelDiameter;
    private final int encoderTicksPerRotation;

    /**
     * Gives HolonomicDriveTrain the values it needs
     * to calculate how to properly apply motor powers
     * when moving and turning at set distances
     *
     * @param mmWheelDiameter The diameter of the robot's
     *                        wheels, in millimeters
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     *                                Neverest 40's, then this value is 1120
     */
    public HolonomicDriveTrain(double mmWheelDiameter, int encoderTicksPerRotation) {
        this.mmWheelDiameter = mmWheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
    }
    /**
     * Gives HolonomicDriveTrain the values it needs
     * to calculate how to properly apply motor powers
     * when moving and turning at set distances
     *
     * @param powerScale The user-defined function for transforming powers assigned to the
     *                   motors
     * @param mmWheelDiameter The diameter of the robot's
     *                        wheels, in millimeters
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     *                                Neverest 40's, then this value is 1120
     */
    public HolonomicDriveTrain(PowerScale powerScale, double mmWheelDiameter, int encoderTicksPerRotation) {
        this.mmWheelDiameter = mmWheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
    }

    /**
     * Enumeration of the four possible motors that this drive train will work with
     */
    private enum Motor {
        FRONT_LEFT, FRONT_RIGHT, BACK_LEFT, BACK_RIGHT
    }

    /**
     * Calculates the motor power necessary to move the
     * robot at the desired angle, which is specified by
     * a simple direction vector, while also turning the
     * robot at the desired motor power
     *
     * @param direction The direction at which the robot will move, should be
     *                  a unit vector
     * @param turnPower The power with which the robot will turn
     * @param motor The motor to calculate power for
     *
     * @return The motor power necessary for moving in holonomic drive configuration
     */
    private double holonomicMath(Vector2 direction, double turnPower, Motor motor) {
        double y = direction.y;
        double x = direction.x;

        double power = 0.0;

        switch(motor) {
            case FRONT_LEFT: power = - y - x - turnPower;
                break;
            case FRONT_RIGHT: power = + y - x - turnPower;
                break;
            case BACK_LEFT: power = - y + x - turnPower;
                break;
            case BACK_RIGHT: power = + y + x - turnPower;
                break;
        }

        return power;
    }

    /**
     * Moves the robot in a direction, while also rotating at a given power (can be zero, of course)
     *
     * @param direction A vector specifying the direction, of which the magnitude can range from 0 to 1
     * @param turnPower The power at which the robot will spin
     */
    private void holonomicMove(Vector2 direction, double turnPower) {
        if(direction.x == 0.0 && direction.y == 0.0 && turnPower == 0.0) {
            stop();
        } else {
            frontLeft.setPower(holonomicMath(direction, turnPower, Motor.FRONT_LEFT));
            frontRight.setPower(holonomicMath(direction, turnPower, Motor.FRONT_RIGHT));
            backLeft.setPower(holonomicMath(direction, turnPower, Motor.BACK_LEFT));
            backRight.setPower(holonomicMath(direction, turnPower, Motor.BACK_RIGHT));
        }
    }

    /**
     * Converts a distance in millimeters to the amount of encoder ticks
     * necessary for a wheel (with specified diameter and ticks per rotation)
     * to reach that distance
     *
     * @param millimeters The desired distance, in millimeters
     * @return The same desired distance, but in encoder ticks
     */
    private int millimetersToEncoderTicks(double millimeters) {
        double rotations = millimeters / (mmWheelDiameter * PI);
        return (int) (rotations * encoderTicksPerRotation);
    }

    /**
     * While moving to a specified position (given in encoder ticks), the motors are put in "busy" mode,
     * which is a convenient way of testing for when the motors have arrived at the target position
     *
     * @return Whether or not at least one motor is busy
     */
    private boolean motorsBusy() {
        return frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy();
    }

    /**
     * Due to physical imperfections, motors may slide past their target position
     * during run to position mode. This method checks to see if the motors have exceeded
     * position
     *
     * @return Whether or not the motors have passed their target position
     */
    private boolean encodersExceedTargetPosition() {
        return frontLeft.getCurrentPosition() >= frontLeft.getTargetPosition() &&
                frontRight.getCurrentPosition() >= frontRight.getTargetPosition() &&
                backLeft.getCurrentPosition() >= backLeft.getTargetPosition() &&
                backRight.getCurrentPosition() >= backRight.getTargetPosition();
    }

    /**
     * Sets the run mode for all four motors
     *
     * @param runMode The run mode (reset encoders, run to position, etc.)
     */
    private void setRunMode(DcMotor.RunMode runMode) {
        frontLeft.setMode(runMode);
        frontRight.setMode(runMode);
        backLeft.setMode(runMode);
        backRight.setMode(runMode);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_LEFT);
        frontRight = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_RIGHT);
        backLeft = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_LEFT);
        backRight = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_RIGHT);

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Front Left Motor", frontLeft.getPower());
        telemetry.addData("Front Right Motor", frontRight.getPower());
        telemetry.addData("Back Left Motor", backLeft.getPower());
        telemetry.addData("Back Right Motor", backRight.getPower());

//        telemetry.addData("Gyro X", gyroSensor.rawX());
//        telemetry.addData("Gyro Y", gyroSensor.rawY());
//        telemetry.addData("Gyro Z", gyroSensor.rawZ());
//        telemetry.addData("Gyro H", gyroSensor.getHeading());
    }

    private double time = 0;
    private boolean toggle = false;

    /**
     * Sets motor powers to drive the robot based
     * on input from a gamepad. This is meant to be
     * used in {@link TeleOpProgram#loop()}
     *
     * @param gamepad The gamepad that will be used to control the robot.
     *                   It should ideally come from the OpMode in the
     *                   program that will be controlling the robot, as either
     *                   gamepad1 or gamepad2
     *
     * @see TeleOpProgram
     */
    public void driveControlled(Gamepad gamepad) {
        final double COOLDOWN = 0.7f;

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(gamepad.y) {
            if((System.nanoTime() - time) / 1e9f >= COOLDOWN) {
                toggle = !toggle;
                time = System.nanoTime();
            }
        }

        if(toggle) {
            holonomicMove(new Vector2(gamepad.left_stick_x, gamepad.left_stick_y), gamepad.right_stick_x);
        } else {
            holonomicMove(new Vector2(gamepad.left_stick_x, -gamepad.left_stick_y), gamepad.right_stick_x);
        }
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
    public void move(double power, double angle) {
        double theta = toRadians(angle - 90);

        holonomicMove(new Vector2(power * cos(theta), power * sin(theta)), 0.0);
    }

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
     * @throws InterruptedException This method will put the current thread to sleep while the motors are moving to the target distance,
     *                              which will throw an InterruptedException if the thread is terminated
     *
     * @see AutonomousProgram
     */
    public void move(double power, double angle, double distance) throws InterruptedException {
        double theta = toRadians(angle - 90);

        Vector2 direction = new Vector2(power * cos(theta), power * sin(theta));

        double frontLeftPower = holonomicMath(direction, 0.0, Motor.FRONT_LEFT);
        double frontRightPower = holonomicMath(direction, 0.0, Motor.FRONT_RIGHT);
        double backLeftPower = holonomicMath(direction, 0.0, Motor.BACK_LEFT);
        double backRightPower = holonomicMath(direction, 0.0, Motor.BACK_RIGHT);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int frontLeftPos = (int) (millimetersToEncoderTicks(distance) * sin(theta));
        int frontRightPos = (int) (millimetersToEncoderTicks(distance) * sin(theta) * sqrt(2));
        int backLeftPos = (int) (millimetersToEncoderTicks(distance) * sin(theta));
        int backRightPos = (int) (millimetersToEncoderTicks(distance) * sin(theta));

        frontLeft.setTargetPosition(frontLeftPower >= 0 ? frontLeftPos : -frontLeftPos);
        frontRight.setTargetPosition(frontRightPower >= 0 ? frontRightPos : -frontRightPos);
        backLeft.setTargetPosition(backLeftPower >= 0 ? backLeftPos : -backLeftPos);
        backRight.setTargetPosition(backRightPower >= 0 ? backRightPos : -backRightPos);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        while(motorsBusy() && !encodersExceedTargetPosition()) {
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

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
    public void turn(double power) {
        holonomicMove(new Vector2(0, 0), power);
    }

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
     * @throws InterruptedException This method will put the current thread to sleep while the robot is turning to the target angle,
     *                              which will throw an InterruptedException if the thread is terminated
     *
     * @see AutonomousProgram
     */
    public void turn(double power, double angle) throws InterruptedException {
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        gyroSensor.resetZAxisIntegrator();

        Thread.sleep(5);

        while(!gyroSensor.isCalibrating()) {
            gyroSensor.calibrate();
            Thread.sleep(5);
        }

        while(gyroSensor.isCalibrating()) {
            Thread.sleep(5);
        }

        boolean negative;

        if(angle >= 0) {
            negative = false;
            power *= 1;
        } else {
            negative = true;
            power *= -1;
        }

        int currentHeading = 0;

        while(currentHeading < abs(angle)) {
            if(gyroSensor.getHeading() == 0) {
                currentHeading = 0;
            } else {
                currentHeading = negative ? 359 - gyroSensor.getHeading() : gyroSensor.getHeading();
            }

            frontLeft.setPower(power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);

            Thread.sleep(1);
        }

        stop();
    }

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
    public void moveAndTurn(double movePower, double angle, double turnPower) {
        double theta = toRadians(angle - 90);

        Vector2 direction = new Vector2(movePower * cos(theta), movePower * sin(theta));

        holonomicMove(direction, turnPower);
    }

    /**
     * Due to friction, most drive trains' motors will stall
     * if given a sufficiently low power, which this method returns
     *
     * @return The lowest absolute power at which the drive train will be able to move
     */
    public double getMinimumMovePower() {
        return 0.15;
    }

    /**
     * Due to friction, most drive trains' motors will stall
     * if given a sufficiently low power, which this method returns
     *
     * @return The lowest absolute power at which the drive train will be able to turn
     */
    public double getMinimumTurnPower() {
        return 0.1;
    }
}
