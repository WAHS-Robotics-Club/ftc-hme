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

        while(opModeIsActive()) {
            driveTrain.turn(0.4);
            driveTrain.logTelemetry(telemetry);
        }
    }
}
