package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;


@TeleOp(name = "Straightness Test", group = "Tests")
@Disabled
public class StraightnessTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final double TIME = 10.0;

        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
        driveTrain.init(hardwareMap);

        waitForStart();

        driveTrain.move(0.3, 0);
        Thread.sleep((long) (TIME * 1000));
    }
}
