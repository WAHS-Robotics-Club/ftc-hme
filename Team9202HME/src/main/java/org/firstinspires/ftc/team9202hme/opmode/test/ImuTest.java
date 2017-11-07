package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;

@TeleOp(name = "IMU Test", group = "Tests")
//@Disabled
public class ImuTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(3.92, 1120);
        driveTrain.init(hardwareMap);

        waitForStart();

        driveTrain.turn(0.3, 90);

        telemetry.addData("Heading", driveTrain.getHeading());
        telemetry.update();

        Thread.sleep(10000);
    }
}
