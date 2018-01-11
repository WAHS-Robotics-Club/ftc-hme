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

    private Toggle narrowGrabToggle = new Toggle();

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.servo.get(HardwareMapConstants.CLAW_LEFT_SERVO);
        right = hardwareMap.servo.get(HardwareMapConstants.CLAW_RIGHT_SERVO);
        spool = hardwareMap.dcMotor.get(HardwareMapConstants.PULLEY_DCMOTOR);

        left.setPosition(1);
        right.setPosition(0);

        spool.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        narrowGrabToggle.setToggle(false);
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
            narrowGrabToggle.toggle();
        }

        if(gamepad.left_bumper) {
            left.setPosition(left.getPosition() + 0.0075);
            right.setPosition(right.getPosition() - 0.0075);
        }

        if(narrowGrabToggle.isToggled()) {
            left.setPosition(0);
            right.setPosition(1);
        } else {
            left.setPosition(0.510);
            right.setPosition(0.370);
        }

        if(gamepad.right_trigger > 0.05) {
            spool.setPower(gamepad.right_trigger);
        } else if(gamepad.left_trigger > 0.05) {
            spool.setPower(-gamepad.left_trigger);
        } else {
            spool.setPower(0);
        }
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Left", left.getPosition());
        telemetry.addData("Right", right.getPosition());
    }
}
