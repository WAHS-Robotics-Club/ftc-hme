package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.motion.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;

@TeleOp(name = "Encoder Test", group = "Tests")
//@Disabled
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new MecanumDriveTrain();
        driveTrain.init(hardwareMap);

        waitForStart();

        driveTrain.moveToDisplacement(90, 24, 0.4);

        Thread.sleep(1000);

        driveTrain.moveToDisplacement(270, 24, 0.4);
    }
}
