package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OrientationSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.PowerScale;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.signum;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 * Drive train made for robots using the omni-wheel drive
 * configuration. This class assumes that the wheels are
 * in "X" configuration, where wheels are at 45 degree
 * angles on the corners of the robot. It also assumes
 * that every wheel has the same diameter, and each
 * corresponding motor has the same number of encoder
 * ticks per rotation
 */
public class HolonomicDriveTrain extends OmniDirectionalDrive {
    private BNO055IMU imu;

    private Toggle preciseControlsToggle = new Toggle();
    private final PowerScale turnPowerScale = PowerScale.CreateMonomialScaleFunction(2, getMinimumTurnPower(), 1.0);

    /**
     * Gives drive train the values it needs to calculate how to properly apply motor powers
     * when moving and turning autonomously
     *
     * @param wheelDiameter           The diameter of the robot's wheels; unit is unnecessary as long as it is consistent with other distances
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     */
    public HolonomicDriveTrain(double wheelDiameter, int encoderTicksPerRotation) {
        super(wheelDiameter, encoderTicksPerRotation);
    }

    /**
     * Moves the robot in a direction, while also rotating at a given power (can be zero, of course)
     *
     * @param movePower The power at which the robot will move (in the specified direction)
     * @param angle     An angle (in degrees) specifying direction
     * @param turnPower The power at which the robot will spin
     */
    private void holonomicMoveAndTurn(double movePower, double angle, double turnPower) {
        holonomicMoveAndTurn(new Vector2(movePower * cos(toRadians(angle)), movePower * sin(toRadians(angle))), turnPower);
    }

    /**
     * Moves the robot in a direction, while also rotating at a given power (can be zero, of course)
     *
     * @param direction A vector specifying the direction, of which the magnitude can range from 0 to 1
     * @param turnPower The power at which the robot will spin
     */
    private void holonomicMoveAndTurn(Vector2 direction, double turnPower) {
        if(abs(direction.x) < 0.01 && abs(direction.y) < 0.01 && abs(turnPower) < 0.01) {
            stop();
        } else {
            frontLeft.setPower(-direction.y - direction.x - turnPower);
            frontRight.setPower(direction.y - direction.x - turnPower);
            backLeft.setPower(-direction.y + direction.x - turnPower);
            backRight.setPower(direction.y + direction.x - turnPower);
        }
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hardwareMap.get(BNO055IMU.class, HardwareMapConstants.IMU);
        imu.initialize(parameters);

        preciseControlsToggle.setToggle(true);
    }

    @Override
    public double getHeading() {
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Precision Controls", (preciseControlsToggle.isToggled() ? "On" : "Off") + "\n");
        telemetry.addData("Heading", getHeading() + " degrees");
    }

    @Override
    public void driveControlled(Gamepad gamepad) {
        if(gamepad.y) {
            preciseControlsToggle.toggle();
        }

        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y; //All gamepad y-axes are flipped, which doesn't work well with this control scheme

        Vector2 direction = new Vector2();
        double turnPower;

        if(preciseControlsToggle.isToggled()) { //Four directional movement only, rotation and movement sensitivity scaled by 0.45
            if(abs(x) < abs(y)) {
                direction.x = 0;
                direction.y = y;
            } else {
                direction.x = x;
                direction.y = 0;
            }

            direction = direction.times(0.45);
            turnPower = turnPowerScale.scale(gamepad.right_stick_x, 0.45);
        } else { //Normal 360 degree motion, movement speed unscaled
            direction = new Vector2(x, y);
            turnPower = turnPowerScale.scale(gamepad.right_stick_x);
        }

        holonomicMoveAndTurn(direction, turnPower);
    }

    @Override
    public void move(double power, double angle) {
        holonomicMoveAndTurn(power, angle, 0);
    }

    @Override
    public void turn(double power) {
        holonomicMoveAndTurn(0, 0, power);
    }

    @Override
    public void moveAndTurn(double movePower, double angle, double turnPower) {
        holonomicMoveAndTurn(movePower, angle, turnPower);
    }

    @Override
    public void move(double power, double angle, double distance) throws InterruptedException {
        stop();
        resetEncoders();

        double basicPosition = encoderTicksPerRotation * (distance / wheelCircumference);
        double blFrPos = abs(basicPosition * cos(toRadians(angle + 45))); //Target position for backLeft and frontRight
        double brFlPos = abs(basicPosition * sin(toRadians(angle + 45))); //Target position for backRight and frontLeft

        holonomicMoveAndTurn(power, angle, 0);

        frontRight.setTargetPosition((int) (blFrPos * signum(frontRight.getPower())));
        backLeft.setTargetPosition((int) (blFrPos * signum(backLeft.getPower())));

        frontLeft.setTargetPosition((int) (brFlPos * signum(frontLeft.getPower())));
        backRight.setTargetPosition((int) (brFlPos * signum(backRight.getPower())));

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        holonomicMoveAndTurn(power, angle, 0); /*Have to do again, since RUN_TO_POSITION makes all powers positive, which nullifies the above signums
                                                 if done prior to fetching motor powers*/
        while(frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()) {
            Thread.sleep(1);
        }

        stop();
        resetEncoders();
    }

    @Override
    public void turn(double power, double angle) throws InterruptedException {
        //TODO: Implement this, just gotta get the gyro to be more accurate
    }

    @Override
    public void absoluteTurn(double power, double angle) throws InterruptedException {
        //TODO: This as well
    }

    @Override
    public double getMinimumMovePower() {
        return 0.15;
    }

    @Override
    public double getMinimumTurnPower() {
        return 0.1;
    }
}
