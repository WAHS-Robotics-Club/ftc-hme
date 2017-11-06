package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;

@TeleOp(name = "Encoder Test", group = "Tests")
//@Disabled
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
        driveTrain.init(hardwareMap);

        waitForStart();

        driveTrain.move(0.3, 30, 1000); //39.37 inches
    }
}