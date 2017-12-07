package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.Vector2;

import static java.lang.Math.*;

/**
 * Drive train made for robots using mecanum wheels in a simple
 * tank drive configuration. This class assumes that all wheels
 * are pointing in the same direction and have the same diameter
 * and motor type.
 */
public class MecanumDriveTrain extends OmniDirectionalDrive {
    private BNO055IMU imu;

    /**
     * Gives drive train the values it needs to calculate how to properly apply motor powers
     * when moving and turning autonomously
     *
     * @param wheelDiameter           The diameter of the robot's wheels; unit is unnecessary as long as it is consistent with other distances
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     */
    public MecanumDriveTrain(double wheelDiameter, int encoderTicksPerRotation) {
        super(wheelDiameter, encoderTicksPerRotation);
    }

    private void mecanumMoveAndTurn(Vector2 direction, double turnPower) {
        if(direction.x < 0.01 && direction.y < 0.01 && turnPower < 0.01) {
            stop();
        } else {
            frontLeft.setPower(direction.y + direction.x + turnPower);
            backLeft.setPower(direction.y - direction.x + turnPower);
            frontRight.setPower(-direction.y - direction.x - turnPower);
            backRight.setPower(-direction.y + direction.x - turnPower);
        }
    }

    private void mecanumMoveAndTurn(double movePower, double angle, double turnPower) {
        mecanumMoveAndTurn(new Vector2(cos(toRadians(angle)), sin(toRadians(angle))).times(movePower), turnPower);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hardwareMap.get(BNO055IMU.class, HardwareMapConstants.IMU);
        imu.initialize(parameters);
    }

    @Override
    public double getHeading() {
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Heading", getHeading() + " degrees");
    }

    @Override
    public void driveControlled(Gamepad gamepad) {
        mecanumMoveAndTurn(new Vector2(gamepad.left_stick_x, gamepad.left_stick_y), gamepad.right_stick_x);
    }

    @Override
    public void move(double power, double angle) {
        mecanumMoveAndTurn(power, angle, 0);
    }

    @Override
    public void turn(double power) {
        mecanumMoveAndTurn(Vector2.ZERO, power);
    }

    @Override
    public void moveAndTurn(double movePower, double angle, double turnPower) {
        mecanumMoveAndTurn(movePower, angle, turnPower);
    }

    @Override
    public void move(double power, double angle, double distance) throws InterruptedException {

    }

    @Override
    public void turn(double power, double angle) throws InterruptedException {

    }

    @Override
    public void absoluteTurn(double power, double angle) throws InterruptedException {

    }

    @Override
    public double getMinimumMovePower() {
        return 0.1;
    }

    @Override
    public double getMinimumTurnPower() {
        return 0.05;
    }
}
