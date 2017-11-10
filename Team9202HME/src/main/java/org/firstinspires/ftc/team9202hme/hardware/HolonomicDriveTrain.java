package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.TempUnit;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.PowerScale;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;
import org.firstinspires.ftc.team9202hme.util.Vector3;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
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
 * @author John Eichelberger
 * @author Nathaniel Glover
 * @author Sage Wibberley
 */
//TODO: Make minimum move/turn powers programmable
public class HolonomicDriveTrain extends HardwareComponent {
    private DcMotorEx frontLeft, frontRight, backLeft, backRight;
    private BNO055IMU imu;

    private Toggle preciseControlsToggle = new Toggle();
    private final PowerScale turnPowerScale = PowerScale.CreateMonomialScaleFunction(2, getMinimumTurnPower(), 1.0);

    private final double wheelCircumference;
    private final int encoderTicksPerRotation;

    /**
     * Gives HolonomicDriveTrain the values it needs to calculate how to properly apply motor powers
     * when moving and turning autonomously
     *
     * @param wheelDiameter The diameter of the robot's wheels; unit is unnecessary as long as it is consistent with other distances
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     *                                Neverest 40's, then this value is 1120
     */
    public HolonomicDriveTrain(double wheelDiameter, int encoderTicksPerRotation) {
        this.wheelCircumference = wheelDiameter * PI;
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
     * @param movePower The power at which the robot will move
     * @param angle The direction at which the robot will move
     * @param turnPower The power with which the robot will turn
     * @param motor The motor to calculate power for
     *
     * @return The motor power necessary for moving in holonomic drive configuration
     */
    private double holonomicMath(double movePower, double angle, double turnPower, Motor motor) {
        angle = toRadians(angle);
        double x = movePower * cos(angle);
        double y = movePower * sin(angle);

        return holonomicMath(new Vector2(x, y), turnPower, motor);
    }

    /**
     * Calculates the motor power necessary to move the
     * robot at the desired angle, which is specified by
     * a simple direction vector, while also turning the
     * robot at the desired motor power
     *
     * @param direction The direction at which the robot will move, should have a
     *                  magnitude less than or equal to 1
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
     *  @param movePower The power at which the robot will move (in the specified direction)
     * @param angle An angle (in degrees) specifying direction
     * @param turnPower The power at which the robot will spin
     */
    private void holonomicMove(double movePower, double angle, double turnPower) {
        holonomicMove(new Vector2(movePower * cos(toRadians(angle)), movePower * sin(toRadians(angle))), turnPower);
    }

    /**
     * Moves the robot in a direction, while also rotating at a given power (can be zero, of course)
     *
     * @param direction A vector specifying the direction, of which the magnitude can range from 0 to 1
     * @param turnPower The power at which the robot will spin
     */
    private void holonomicMove(Vector2 direction, double turnPower) {
        if(abs(direction.x) <= 0.01 && abs(direction.y) <= 0.01 && abs(turnPower) <= 0.01) {
            stop();
        } else {
            frontLeft.setPower(holonomicMath(direction, turnPower, Motor.FRONT_LEFT));
            frontRight.setPower(holonomicMath(direction, turnPower, Motor.FRONT_RIGHT));
            backLeft.setPower(holonomicMath(direction, turnPower, Motor.BACK_LEFT));
            backRight.setPower(holonomicMath(direction, turnPower, Motor.BACK_RIGHT));
        }
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
        frontLeft = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_LEFT);
        frontRight = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_RIGHT);
        backLeft = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_LEFT);
        backRight = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_RIGHT);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hardwareMap.get(BNO055IMU.class, HardwareMapConstants.IMU);
        imu.initialize(parameters);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        frontLeft.setTargetPositionTolerance(15);
        frontRight.setTargetPositionTolerance(15);
        backLeft.setTargetPositionTolerance(15);
        backRight.setTargetPositionTolerance(15);

        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        preciseControlsToggle.setToggle(true);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Precision Controls", (preciseControlsToggle.isToggled() ? "On" : "Off") + "\n");

        AngularVelocity velocity = imu.getAngularVelocity();

        Vector3 angularVelocity = new Vector3(velocity.xRotationRate, velocity.yRotationRate, velocity.zRotationRate);

        telemetry.addData("Angular Velocity", angularVelocity + " degrees/sec\n");
        telemetry.addData("Heading", getHeading() + " degrees\n");
        telemetry.addData("Temperature", imu.getTemperature().toUnit(TempUnit.KELVIN).temperature + " kelvins\n");

        telemetry.addData("FL Power", frontLeft.getPower());
        telemetry.addData("FR Power", frontRight.getPower());
        telemetry.addData("BL Power", backLeft.getPower());
        telemetry.addData("BR Power", backRight.getPower() + "\n");

        telemetry.addData("FL Encoder Position", frontLeft.getCurrentPosition());
        telemetry.addData("FR Encoder Position", frontRight.getCurrentPosition());
        telemetry.addData("BL Encoder Position", backLeft.getCurrentPosition());
        telemetry.addData("BR Encoder Position", backRight.getCurrentPosition() + "\n");

        telemetry.addData("FL Velocity", frontLeft.getVelocity(AngleUnit.DEGREES) + " degrees/sec");
        telemetry.addData("FR Velocity", frontRight.getVelocity(AngleUnit.DEGREES) + " degrees/sec");
        telemetry.addData("BL Velocity", backLeft.getVelocity(AngleUnit.DEGREES) + " degrees/sec");
        telemetry.addData("BR Velocity", backRight.getVelocity(AngleUnit.DEGREES) + " degrees/sec");
    }

    public double getHeading() {
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }

    public void setHeading(double heading) throws InterruptedException {

    }

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
        if(gamepad.y) {
            preciseControlsToggle.toggle();
        }

        double x = gamepad.left_stick_x;
        double y = gamepad.left_stick_y;

        Vector2 direction = new Vector2();
        double turnPower;

        if(preciseControlsToggle.isToggled()) { //Four directional movement only, rotation and movement sensitivity reduced considerably
            if(abs(x) < abs(y)) {
                direction.x = 0;
                direction.y = y;
            } else {
                direction.x = x;
                direction.y = 0;
            }

            direction = direction.times(0.45);
            turnPower = turnPowerScale.scale(gamepad.right_stick_x, 0.45);
        } else {
            direction = new Vector2(gamepad.left_stick_x, gamepad.left_stick_y);
            turnPower = turnPowerScale.scale(gamepad.right_stick_x);
        }

        holonomicMove(direction, turnPower);
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
        holonomicMove(power, angle, 0);
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
        holonomicMove(0, 0, power);
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
        holonomicMove(movePower, angle, turnPower);
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
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        double basicPosition = encoderTicksPerRotation * (distance / wheelCircumference);
        double leftRightDiagonalPos = abs(basicPosition * cos(toRadians(angle + 45)));
        double rightLeftDiagonalPos = abs(basicPosition * sin(toRadians(angle + 45)));

        holonomicMove(power, angle, 0);

        frontRight.setTargetPosition((int) (leftRightDiagonalPos * signum(frontRight.getPower())));
        backLeft.setTargetPosition((int) (leftRightDiagonalPos * signum(backLeft.getPower())));

        frontLeft.setTargetPosition((int) (rightLeftDiagonalPos * signum(frontLeft.getPower())));
        backRight.setTargetPosition((int) (rightLeftDiagonalPos * signum(backRight.getPower())));

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        holonomicMove(power, angle, 0); /*Have to do again, since RUN_TO_POSITION makes all powers positive, which nullifies the above signums
                                          if done prior to fetching motor powers*/

        while(motorsBusy()) {
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double startHeading = getHeading();

        double currentHeading = getHeading() - startHeading;

        while(abs(currentHeading - angle) > 1) { //1 degree error margin
            currentHeading = getHeading() - startHeading;
            turn(-power * signum(angle - currentHeading));
            Thread.sleep(1);
        }

        stop();
        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
