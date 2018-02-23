package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.OmniDirectionalDrive;

@TeleOp(name = "Encoder Test", group = "Tests")
//@Disabled
public class EncoderTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        OmniDirectionalDrive driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
        driveTrain.init(hardwareMap);

        waitForStart();

        driveTrain.move(0.4, 0, 24); //Move 2 feet to the right (or 0 degrees) at 0.4 power
    }
}
