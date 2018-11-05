package org.firstinspires.ftc.team9202hme.opmode;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Wheel Test", group = "Tests")
//@Disabled
public class WheelTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor frontLeft = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_LEFT);
        DcMotor frontRight = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_FRONT_RIGHT);
        DcMotor backLeft = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_LEFT);
        DcMotor backRight = hardwareMap.dcMotor.get(HardwareMapConstants.DRIVE_BACK_RIGHT);

        waitForStart();

        frontLeft.setPower(1);
        Thread.sleep(500);
        frontLeft.setPower(0);

        frontRight.setPower(1);
        Thread.sleep(500);
        frontRight.setPower(0);

        backLeft.setPower(1);
        Thread.sleep(500);
        backLeft.setPower(0);

        backRight.setPower(1);
        Thread.sleep(500);
        backRight.setPower(0);
    }
}
