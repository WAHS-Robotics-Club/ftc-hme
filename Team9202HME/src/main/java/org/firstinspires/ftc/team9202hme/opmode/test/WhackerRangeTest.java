package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Whacker Range Test", group = "Tests")
//@Disabled
public class WhackerRangeTest extends OpMode {
    private Servo whacker;
    private CRServo extender;

    @Override
    public void init() {
        whacker = hardwareMap.servo.get(HardwareMapConstants.WHACKER_SERVO);
        extender = hardwareMap.crservo.get(HardwareMapConstants.WHACKER_EXTENDER_CRSERVO);
        whacker.setPosition(0.8);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up) {
            whacker.setPosition(whacker.getPosition() + 0.005);
        } else if(gamepad1.dpad_down) {
            whacker.setPosition(whacker.getPosition() - 0.005);
        } else {
            whacker.setPosition(whacker.getPosition());
        }

        if(gamepad1.dpad_right) {
            extender.setPower(0.4);
        } else if(gamepad1.dpad_left) {
            extender.setPower(-0.4);
        } else {
            extender.setPower(0);
        }

        telemetry.addData("Position", whacker.getPosition());
        telemetry.update();
    }
}
