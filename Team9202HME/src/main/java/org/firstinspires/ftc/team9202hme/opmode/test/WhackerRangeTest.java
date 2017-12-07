package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Whacker Range Test", group = "Tests")
//@Disabled
public class WhackerRangeTest extends OpMode {
    private Servo whacker;

    @Override
    public void init() {
        whacker = hardwareMap.servo.get(HardwareMapConstants.JEWEL_WHACKER_SERVO);
        whacker.setPosition(0.8);
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            whacker.setPosition(whacker.getPosition() + 0.0005);
        } else if(gamepad1.b) {
            whacker.setPosition(whacker.getPosition() - 0.0005);
        } else {
            whacker.setPosition(whacker.getPosition());
        }

        telemetry.addData("Position", whacker.getPosition());
        telemetry.update();
    }
}
