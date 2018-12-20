package org.firstinspires.ftc.team9202hme.motion;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.util.Vector2;

import static java.lang.Math.*;

/**
 * Drive train made for robots using mecanum wheels in a simple
 * tank drive configuration. This class assumes that all wheels
 * are pointing in the same direction and have the same diameter
 * and motor type. Left and right motion may be inverted depending
 * on the manufacturer and placement of your wheels.
 */
public class MecanumDriveTrain extends HolonomicDriveTrain {
    private BNO055IMU imu;

    /**
     * Gives drive train the values it needs to calculate how to properly apply motor powers
     * when moving and turning autonomously
     *
     * @param wheelDiameter           The diameter of the robot's wheels; unit is unnecessary as
     *                                long as it is consistent with other distance units
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     */
    public MecanumDriveTrain(double wheelDiameter, int encoderTicksPerRotation) {
        super(wheelDiameter, encoderTicksPerRotation);
    }

    public MecanumDriveTrain() {
        this(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.calibrationDataFile = RobotConstants.IMU_CALIBRATION_FILE;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hardwareMap.get(BNO055IMU.class, HardwareMapConstants.IMU);
        imu.initialize(parameters);
        BNO055IMU.CalibrationData calibrationData = imu.readCalibrationData();
        imu.writeCalibrationData(calibrationData);
    }

    @Override
    public double getHeading() {
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return orientation.firstAngle;
    }

    @Override
    public void move(Vector2 velocity, double turnPower) {
        Vector2 clampedVelocity = velocity.magnitude() > 1 ? velocity.normalize() : velocity;

        if(abs(clampedVelocity.x) < 0.01 && abs(clampedVelocity.y) < 0.01 && abs(turnPower) < 0.01) {
            stop();
        } else {
            frontLeft.setPower(-clampedVelocity.y - clampedVelocity.x - turnPower);
            backLeft.setPower(-clampedVelocity.y + clampedVelocity.x - turnPower);
            frontRight.setPower(clampedVelocity.y - clampedVelocity.x - turnPower);
            backRight.setPower(clampedVelocity.y + clampedVelocity.x - turnPower);
        }
    }

    @Override
    public void moveToDisplacement(Vector2 displacement, double movePower) throws InterruptedException {
        final double timeout = 7; //Seconds until the robot should stop moving

        double initialHeading = getHeading();

        double distance = displacement.y; //TODO: Support all directions, not just forward/backward
        int position = (int) (encoderTicksPerRotation * (abs(distance) / wheelCircumference));

        stop();
        resetEncoders();

        move(90, movePower * signum(distance));

        frontLeft.setTargetPosition((int) (position * signum(frontLeft.getPower())));
        backLeft.setTargetPosition((int) (position * signum(backLeft.getPower())));
        frontRight.setTargetPosition((int) (position * signum(frontRight.getPower())));
        backRight.setTargetPosition((int) (position * signum(backRight.getPower())));

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);

        move(90, movePower * signum(distance));
        double startTime = System.nanoTime() / 1e9;

        while((frontLeft.isBusy() || backLeft.isBusy() || frontRight.isBusy() || backRight.isBusy())
                && (System.nanoTime() / 1e9) - startTime < timeout) {
            Thread.sleep(1);
        }

        stop();
        resetEncoders();

        turnToHeading(initialHeading); //Correct any slight rotation that occurred during movement
    }

    @Override
    public void turnToHeading(double heading) throws InterruptedException {
        stop();

        while(abs(getHeading() - heading) > 1) {
            turn(signum(getHeading() - heading) * max(abs(getHeading() - heading) / 120, 0.125));
            Thread.sleep(0, 1);
        }

        Thread.sleep(50);

        while(abs(getHeading() - heading) > 0.25) {
            turn(signum(getHeading() - heading) * 0.115);
            Thread.sleep(0, 1);
        }

        stop();
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Heading", getHeading() + " degrees");

        telemetry.addData("FL Pos", frontLeft.getCurrentPosition());
        telemetry.addData("FR Pos", frontRight.getCurrentPosition());
        telemetry.addData("BL Pos", backLeft.getCurrentPosition());
        telemetry.addData("BR Pos", backRight.getCurrentPosition());
    }
}
