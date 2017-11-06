package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

public class IdolGrabber extends HardwareComponent {
    private DcMotor lift;
    private Servo grabber;
    private CRServo extender;

    @Override
    public void init(HardwareMap hardwareMap) {
        lift = hardwareMap.dcMotor.get(HardwareMapConstants.IDOL_LIFT_DCMOTOR);
        grabber = hardwareMap.servo.get(HardwareMapConstants.IDOL_GRABBER_SERVO);
        extender = hardwareMap.crservo.get(HardwareMapConstants.IDOL_EXTENDER_CRSERVO);
    }

    public void grabControlled(Gamepad gamepad) {
        if(gamepad.dpad_up) {
            lift.setPower(gamepad.x ? -1.0 : -0.5);
        } else if(gamepad.dpad_down) {
            lift.setPower(0.1);
        } else {
            lift.setPower(0);
        }

        if(gamepad.dpad_right) {
            extender.setPower(-0.4);
        } else if(gamepad.dpad_left) {
            extender.setPower(0.4);
        } else {
            extender.setPower(gamepad.x ? -0.05 : 0.0);
        }

        if(gamepad.x) {
            grabber.setPosition(1);
        } else {
            grabber.setPosition(0);
        }
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
