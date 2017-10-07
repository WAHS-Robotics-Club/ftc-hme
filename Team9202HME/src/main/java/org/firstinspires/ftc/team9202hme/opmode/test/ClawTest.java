package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@Autonomous(name = "Claw Test", group = "Tests")
public class ClawTest extends LinearOpMode {
    private CRServo left, right;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor arm = hardwareMap.dcMotor.get(HardwareMapConstants.CLAW_ARM_DCMOTOR);
        left = hardwareMap.crservo.get(HardwareMapConstants.CLAW_LEFT_CRSERVO);
        right = hardwareMap.crservo.get(HardwareMapConstants.CLAW_RIGHT_CRSERVO);

        arm.setDirection(DcMotorSimple.Direction.REVERSE); //Arm should lift off of robot with positive power
        right.setDirection(DcMotorSimple.Direction.REVERSE); //Right pincher should turn inward with positive power

        waitForStart();

        arm.setPower(0.5);
        Thread.sleep(500);

        arm.setPower(-0.5);
        Thread.sleep(500);

        setClawPower(0.1);
        Thread.sleep(1000);

        setClawPower(-0.1);
        Thread.sleep(1000);
    }

    private void setClawPower(double power) {
        left.setPower(power);
        right.setPower(power);
    }
}
