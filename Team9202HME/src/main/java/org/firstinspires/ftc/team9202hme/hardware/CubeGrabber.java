package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;
import org.firstinspires.ftc.team9202hme.util.Toggle;

/**
 * A simple continuous-rotation servo claw, made for picking up
 * 6x6x6 inch foam cubes
 */
public class CubeGrabber extends HardwareComponent {
    private Servo left, right;
    private DcMotor spool;

    private Toggle grabToggle = new Toggle();
    private Toggle grabTypeToggle = new Toggle(); //Toggled = narrow grab, Untoggled = wide grab

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.servo.get(HardwareMapConstants.CLAW_LEFT_SERVO);
        right = hardwareMap.servo.get(HardwareMapConstants.CLAW_RIGHT_SERVO);
        spool = hardwareMap.dcMotor.get(HardwareMapConstants.PULLEY_DCMOTOR);

        spool.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Sets motor powers to operate the claw based
     * on input from a gamepad. This is meant to be
     * used in {@link TeleOpProgram#loop()}
     *
     * @param gamepad The gamepad that will be used to control the robot.
     *                It should ideally come from the OpMode in the
     *                program that will be controlling the robot, as either
     *                gamepad1 or gamepad2
     * @see TeleOpProgram
     */
    public void grabControlled(Gamepad gamepad) {
        if(gamepad.right_bumper) {
            grabToggle.toggle();
        }

        if(gamepad.left_bumper) {
            grabTypeToggle.toggle();
        }

        if(grabToggle.isToggled()) {
            left.setPosition(0);
            right.setPosition(1);
        } else {
            if(grabTypeToggle.isToggled()) { //Narrow grab
                release();
            } else { //Wide grab
                openWide();
            }
        }

        if(gamepad.right_trigger > 0.05) {
            spool.setPower(gamepad.right_trigger);
        } else if(gamepad.left_trigger > 0.05) {
            spool.setPower(-gamepad.left_trigger);
        } else {
            spool.setPower(0);
        }
    }

    public void grab() {
        left.setPosition(0);
        right.setPosition(1);
    }

    public void release() {
        left.setPosition(0.51);
        right.setPosition(0.37);
    }

    public void openWide() {
        left.setPosition(0.81);
        right.setPosition(0.07);
    }

    public void lift() {
        spool.setPower(0.8);
    }

    public void stop() {
        spool.setPower(0);
    }

    public void lower() {
        spool.setPower(-0.8);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
