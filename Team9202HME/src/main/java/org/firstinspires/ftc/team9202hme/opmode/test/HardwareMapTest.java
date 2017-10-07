package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Hardware Map Test")
public class HardwareMapTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor m11, m12, m21, m22, m31, m32, m41, m42;

        m11 = hardwareMap.dcMotor.get("frontLeft");
        m12 = hardwareMap.dcMotor.get("backLeft");

        m21 = hardwareMap.dcMotor.get("frontRight");
        m22 = hardwareMap.dcMotor.get("backRight");

        m31 = hardwareMap.dcMotor.get("lift");
        m32 = hardwareMap.dcMotor.get("spinner");

        m41 = hardwareMap.dcMotor.get("left");
        m42 = hardwareMap.dcMotor.get("right");

        waitForStart();

        m11.setPower(1);
        m12.setPower(1);
        Thread.sleep(2500);
        m11.setPower(0);
        m12.setPower(0);

        m21.setPower(1);
        m22.setPower(1);
        Thread.sleep(2500);
        m21.setPower(0);
        m22.setPower(0);

        m31.setPower(1);
        m32.setPower(1);
        Thread.sleep(2500);
        m31.setPower(0);
        m32.setPower(0);

        m41.setPower(1);
        m42.setPower(1);
        Thread.sleep(2500);
        m41.setPower(0);
        m42.setPower(0);
    }
}
