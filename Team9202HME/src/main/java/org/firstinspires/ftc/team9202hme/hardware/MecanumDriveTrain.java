package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.PowerScale;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;

import static java.lang.Math.*;

/**
 * Drive train made for robots using mecanum wheels in a simple
 * tank drive configuration. This class assumes that all wheels
 * are pointing in the same direction and have the same diameter
 * and motor type. Left and right motion may be inverted depending
 * on the manufacturer and placement of your wheels.
 */
public class MecanumDriveTrain extends OmniDirectionalDrive {
    private BNO055IMU imu;

    private final Toggle preciseControlsToggle = new Toggle();
    private final PowerScale turnPowerScale = PowerScale.CreateMonomialScaleFunction(2, getMinimumTurnPower(), 1.0);

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
        if(abs(direction.x) < 0.01 && abs(direction.y) < 0.01 && abs(turnPower) < 0.01) {
            stop();
        } else {
            frontLeft.setPower(-direction.y + direction.x - turnPower);
            backLeft.setPower(-direction.y - direction.x - turnPower);
            frontRight.setPower(direction.y - direction.x - turnPower);
            backRight.setPower(direction.y + direction.x - turnPower);
        }
    }

    private void mecanumMoveAndTurn(double movePower, double angle, double turnPower) {
        mecanumMoveAndTurn(new Vector2(cos(toRadians(angle)), sin(toRadians(angle))).times(movePower), turnPower);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);

        preciseControlsToggle.setToggle(true);

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

            direction = direction.times(0.8);
            turnPower = turnPowerScale.scale(gamepad.right_stick_x, 0.8);
        } else { //Normal 360 degree motion, movement speed unscaled
            direction = new Vector2(x, y);
            turnPower = turnPowerScale.scale(gamepad.right_stick_x);
        }

        mecanumMoveAndTurn(direction, turnPower);
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
        stop();
        resetEncoders();

        int position = (int) (encoderTicksPerRotation * (abs(distance) / wheelCircumference));

        //TODO: Find the actual values for motor positions besides the above variable. Will likely involve multiplying by sin/cos of some angle
        //They will work for now however, since we only need to move forwards in autonomous

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setTargetPosition((int) (-position * signum(sin(angle))));
        backLeft.setTargetPosition((int) (-position * signum(sin(angle))));
        frontRight.setTargetPosition((int) (position * signum(sin(angle))));
        backRight.setTargetPosition((int) (position * signum(sin(angle))));

        mecanumMoveAndTurn(power, angle, 0);

        while(frontLeft.isBusy() || backLeft.isBusy() || frontRight.isBusy() || backRight.isBusy()) {
            Thread.sleep(1);
        }

        stop();
        resetEncoders();
    }

    @Override
    public void turn(double power, double angle) throws InterruptedException {
        double startHeading = getHeading();

        double error = angle;

        double startTime = System.nanoTime() / 1e9;

        while(abs(error) > 0.5 && (System.nanoTime() / 1e9) - startTime < 4) {
            double scale = (1 - getMinimumTurnPower()) * sin(toRadians(90 * (abs(error) / angle))) + getMinimumTurnPower();
            turn(power * abs(scale) * signum(error));
            error = angle - (getHeading() - startHeading);
        }
    }

    @Override
    public void absoluteTurn(double power, double angle) throws InterruptedException {
        //TODO: Implement this later, not important for competition
    }

    @Override
    public double getMinimumMovePower() {
        return 0.1;
    }

    @Override
    public double getMinimumTurnPower() {
        return 0.1;
    }
}
