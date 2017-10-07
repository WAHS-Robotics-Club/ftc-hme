package org.firstinspires.ftc.team9202hme.hardware.driving;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.math.PowerScale;
import org.firstinspires.ftc.team9202hme.math.Vector2;

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
public class HolonomicDriveTrain extends DriveTrain {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private GyroSensor gyroSensor;

    private PowerScale powerScale;

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
        this.powerScale = new PowerScale();

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
        this.powerScale = powerScale;

        this.mmWheelDiameter = mmWheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
    }

    /**
     * Enumeration of the four possible motors that this DriveTrain will work with
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

    private void holonomicMove(Vector2 direction, double turnPower) {
        if(direction.x == 0.0 && direction.y == 0.0 && turnPower == 0.0) {
            stop();
        } else {
            frontLeft.setPower(powerScale.scalePower(holonomicMath(direction, turnPower, Motor.FRONT_LEFT)));
            frontRight.setPower(powerScale.scalePower(holonomicMath(direction, turnPower, Motor.FRONT_RIGHT)));
            backLeft.setPower(powerScale.scalePower(holonomicMath(direction, turnPower, Motor.BACK_LEFT)));
            backRight.setPower(powerScale.scalePower(holonomicMath(direction, turnPower, Motor.BACK_RIGHT)));
        }
    }

    private int millimetersToEncoderTicks(double millimeters) {
        double rotations = millimeters / (mmWheelDiameter * PI);
        return (int) (rotations * encoderTicksPerRotation);
    }

    private boolean motorsBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy();
    }

    private boolean encodersExceedTargetPosition() {
        return frontLeft.getCurrentPosition() >= frontLeft.getTargetPosition() &&
                frontRight.getCurrentPosition() >= frontRight.getTargetPosition() &&
                backLeft.getCurrentPosition() >= backLeft.getTargetPosition() &&
                backRight.getCurrentPosition() >= backRight.getTargetPosition();
    }

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

    @Override
    public void driveControlled(Gamepad controller) {
        final double COOLDOWN = 0.7f;

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if(controller.y) {
            if((System.nanoTime() - time) / 1e9f >= COOLDOWN) {
                toggle = !toggle;
                time = System.nanoTime();
            }
        }

        if(toggle) {
            holonomicMove(new Vector2(controller.left_stick_x, controller.left_stick_y), controller.right_stick_x);
        } else {
            holonomicMove(new Vector2(controller.left_stick_x, -controller.left_stick_y), controller.right_stick_x);
        }
    }

    @Override
    public void stop() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

    @Override
    public void move(double power, double angle) {
        double theta = toRadians(angle - 90);

        holonomicMove(new Vector2(power * cos(theta), power * sin(theta)), 0.0);
    }

    @Override
    public void move(double power, double angle, double distance) throws InterruptedException {
        double theta = toRadians(angle - 90);

        Vector2 direction = new Vector2(power * cos(theta), power * sin(theta));

        double frontLeftPower = holonomicMath(direction, 0.0, Motor.FRONT_LEFT);
        double frontRightPower = holonomicMath(direction, 0.0, Motor.FRONT_RIGHT);
        double backLeftPower = holonomicMath(direction, 0.0, Motor.BACK_LEFT);
        double backRightPower = holonomicMath(direction, 0.0, Motor.BACK_RIGHT);

        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int frontLeftPos = (int) (millimetersToEncoderTicks(distance) / sqrt(PI));
        int frontRightPos = (int) (millimetersToEncoderTicks(distance) / sqrt(PI));
        int backLeftPos = (int) (millimetersToEncoderTicks(distance) / sqrt(PI));
        int backRightPos = (int) (millimetersToEncoderTicks(distance) / sqrt(PI));

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

    @Override
    public void turn(double power) {
        holonomicMove(new Vector2(0, 0), power);
    }

    @Override
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

    @Override
    public void moveAndTurn(double movePower, double angle, double turnPower) {
        double theta = toRadians(angle - 90);

        Vector2 direction = new Vector2(movePower * cos(theta), movePower * sin(theta));

        holonomicMove(direction, turnPower);
    }

    @Override
    public double getMinimumMovePower() {
        return 0.2;
    }

    @Override
    public double getMinimumTurnPower() {
        return 0.1;
    }
}
