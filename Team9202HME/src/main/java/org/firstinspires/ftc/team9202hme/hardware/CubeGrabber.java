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
    private Servo lowerLeft, lowerRight, upperLeft, upperRight;
    private DcMotor spool;

    private Toggle grabToggle = new Toggle();
    private Toggle grabTypeToggle = new Toggle(); //Toggled = narrow grab, Untoggled = wide grab

    @Override
    public void init(HardwareMap hardwareMap) {
        lowerLeft = hardwareMap.servo.get(HardwareMapConstants.CLAW_LOWER_LEFT_SERVO);
        lowerRight = hardwareMap.servo.get(HardwareMapConstants.CLAW_LOWER_RIGHT_SERVO);
//        upperLeft = hardwareMap.servo.get(HardwareMapConstants.CLAW_UPPER_LEFT_SERVO);
//        upperRight = hardwareMap.servo.get(HardwareMapConstants.CLAW_UPPER_RIGHT_SERVO);
        spool = hardwareMap.dcMotor.get(HardwareMapConstants.PULLEY_DCMOTOR);

        spool.setPower(0);

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
            grab();
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
        lowerLeft.setPosition(0);
        lowerRight.setPosition(1);
//        upperLeft.setPosition(1);
//        upperRight.setPosition(0);
    }

    public void release() {
        lowerLeft.setPosition(0.51);
        lowerRight.setPosition(0.37);
//        upperLeft.setPosition(0.49);
//        upperRight.setPosition(0.63);
    }

    public void openWide() {
        lowerLeft.setPosition(0.81);
        lowerRight.setPosition(0.07);
//        upperLeft.setPosition(0.19);
//        upperRight.setPosition(0.93);
    }

    public void lift() {
        spool.setPower(1);
    }

    public void stop() {
        spool.setPower(0);
    }

    public void lower() {
        spool.setPower(-1);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
