package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.Toggle;

public class RelicGrabber extends HardwareComponent {
    private DcMotor lift;
    private Servo grabber;
    private CRServo extender;

    private Toggle grabToggle = new Toggle();

    @Override
    public void init(HardwareMap hardwareMap) {
        lift = hardwareMap.dcMotor.get(HardwareMapConstants.IDOL_LIFT_DCMOTOR);
        grabber = hardwareMap.servo.get(HardwareMapConstants.IDOL_GRABBER_SERVO);
        extender = hardwareMap.crservo.get(HardwareMapConstants.IDOL_EXTENDER_CRSERVO);
    }

    public void grabControlled(Gamepad gamepad) {
        if(gamepad.x) {
            grabToggle.toggle();
        }

        if(gamepad.dpad_up) {
            lift.setPower(grabToggle.isToggled() ? -1.0 : -0.5);
        } else if(gamepad.dpad_down) {
            lift.setPower(0.05);
        } else {
            lift.setPower(0);
        }

        if(gamepad.dpad_right) {
            extender.setPower(-0.5);
        } else if(gamepad.dpad_left) {
            extender.setPower(0.5);
        } else {
            extender.setPower(grabToggle.isToggled() ? -0.01 : 0.0);
        }

        if(grabToggle.isToggled()) {
            grabber.setPosition(1);
        } else {
            grabber.setPosition(0);
        }
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
