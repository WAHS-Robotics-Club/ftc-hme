package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.Toggle;

public class RelicGrabber extends HardwareComponent {
    private DcMotor extender;
    private DcMotor retractor;
    private DcMotor rotator;
    private Servo grabber;

    private Toggle grabToggle = new Toggle();

    @Override
    public void init(HardwareMap hardwareMap) {
        extender = hardwareMap.dcMotor.get(HardwareMapConstants.ARM_PULLEY_DCMOTOR);
        retractor = hardwareMap.dcMotor.get(HardwareMapConstants.ARM_RETRACTOR_DCMOTOR);
        rotator = hardwareMap.dcMotor.get(HardwareMapConstants.RELIC_CLAW_ROTATOR);
        grabber = hardwareMap.servo.get(HardwareMapConstants.RELIC_CLAW_SERVO);

        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        retractor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rotator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        grabToggle.setToggle(true);
    }

    private final double SPEED = 0.5;

    public void extend() {
        extender.setPower(SPEED);
        retractor.setPower(-SPEED);
    }

    public void stop() {
        extender.setPower(0);
    }

    public void retract() {
        extender.setPower(-SPEED);
        retractor.setPower(SPEED);
    }

    public void grabControlled(Gamepad gamepad) {
        if(gamepad.x) {
            grabToggle.toggle();
        }

        final double SPEED = 0.5;

        if(gamepad.left_bumper) {
            extender.setPower(SPEED);
        } else if(gamepad.left_trigger > 0.05) {
            extender.setPower(-SPEED);
        } else {
            extender.setPower(0);
        }

        if(gamepad.right_bumper) {
            retractor.setPower(-SPEED);
        } else if(gamepad.right_trigger > 0.05) {
            retractor.setPower(SPEED);
        } else {
            retractor.setPower(0);
        }

        if(gamepad.dpad_up) {
            rotator.setPower(-0.45);
        } else if(gamepad.dpad_down) {
            rotator.setPower(0.15);
        } else {
            rotator.setPower(0);
        }

        if(grabToggle.isToggled()) {
            grabber.setPosition(0);
        } else {
            grabber.setPosition(1);
        }
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
