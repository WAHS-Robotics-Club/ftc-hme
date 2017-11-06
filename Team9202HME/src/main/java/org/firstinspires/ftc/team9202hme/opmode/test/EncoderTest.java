package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;

@TeleOp(name = "Encoder Test", group = "Tests")
//@Disabled
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(3.92, 1120); //4 inch diameter omni wheels, -.8 because omni's deform wheel a bit
        driveTrain.init(hardwareMap);

        waitForStart();

        driveTrain.move(0.4, 90, 96); //Move 8 feet forward (or 90 degrees) at 0.4 power
    }
}
