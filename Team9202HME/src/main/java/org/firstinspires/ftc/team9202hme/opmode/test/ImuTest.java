package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.OmniDirectionalDrive;

@TeleOp(name = "IMU Test", group = "Tests")
//@Disabled
public class ImuTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        OmniDirectionalDrive driveTrain = new MecanumDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
        driveTrain.init(hardwareMap);

        waitForStart();

        double previousHeading = driveTrain.getHeading();

        double startTime = System.nanoTime() / 1e9d;
        double accumulatedTime = 0;

        int updates = 0;
        int accumulatedUpdates = 0;

        double updatesPerSecond = 0;

        while(opModeIsActive()) {
            driveTrain.turn(gamepad1.right_stick_x);

            double heading = driveTrain.getHeading();

            if(Math.abs(previousHeading - heading) > 1) {
                updates++;
                accumulatedUpdates++;
            }

            previousHeading = heading;
            double time = System.nanoTime() / 1e9d - startTime;
            accumulatedTime += time;

            if(accumulatedTime >= 1) {
                updatesPerSecond = accumulatedUpdates / accumulatedTime;
                accumulatedTime = 0;
                accumulatedUpdates = 0;
            }

            telemetry.addData("Updates per second", updatesPerSecond);
            telemetry.addData("Average updates per second", updates / time);
        }
    }
}
