package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Whacker Range Test", group = "Tests")
//@Disabled
public class WhackerRangeTest extends OpMode {
    private Servo whacker;
    private Servo extender;

    @Override
    public void init() {
        whacker = hardwareMap.servo.get(HardwareMapConstants.WHACKER_SERVO);
        extender = hardwareMap.servo.get(HardwareMapConstants.WHACKER_EXTENDER_SERVO);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up) {
            whacker.setPosition(whacker.getPosition() + 0.002);
        } else if(gamepad1.dpad_down) {
            whacker.setPosition(whacker.getPosition() - 0.002);
        } else {
            whacker.setPosition(whacker.getPosition());
        }

        if(gamepad1.dpad_right) {
            extender.setPosition(extender.getPosition() + 0.002);
        } else if(gamepad1.dpad_left) {
            extender.setPosition(extender.getPosition() - 0.002);
        } else {
            extender.setPosition(extender.getPosition());
        }

        telemetry.addData("Whacker", whacker.getPosition());
        telemetry.addData("Extender", extender.getPosition());
        telemetry.update();
    }
}
