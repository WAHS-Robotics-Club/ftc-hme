package org.firstinspires.ftc.team9202hme.opmode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotConstants;

@TeleOp(name = "IMU Telemetry", group = "Tests")
//@Disabled
public class ImuTelemetry extends OpMode {
    private BNO055IMU imu;

    @Override
    public void init() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.calibrationDataFile = RobotConstants.IMU_CALIBRATION_FILE;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        imu = hardwareMap.get(BNO055IMU.class, HardwareMapConstants.IMU);
        imu.initialize(parameters);
    }

    @Override
    public void start() {
        imu.startAccelerationIntegration(new Position(DistanceUnit.METER, 0, 0, 0, 0), new Velocity(DistanceUnit.METER, 0, 0, 0, 0), 100);
    }

    @Override
    public void loop() {
        telemetry.addData("Heading", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
        telemetry.addData("Acceleration", imu.getAcceleration());
        telemetry.addData("Velocity", imu.getVelocity());
        telemetry.addData("Position", imu.getPosition());
        telemetry.update();
    }
}
