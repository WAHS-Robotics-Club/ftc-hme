package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

/**
 * A simple continuous-rotation servo claw, made for picking up
 * 6x6x6 inch foam cubes
 *
 * @author Nathaniel Glover
 */
public class CRServoClaw extends HardwareComponent {
    private CRServo left, right;

    @Override
    public void init(HardwareMap hardwareMap) {
        left = hardwareMap.crservo.get(HardwareMapConstants.CLAW_LEFT_CRSERVO);
        right = hardwareMap.crservo.get(HardwareMapConstants.CLAW_RIGHT_CRSERVO);

        right.setDirection(DcMotorSimple.Direction.REVERSE);
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
     *
     * @see TeleOpProgram
     */
    public void grabControlled(Gamepad gamepad) {
        final double CLAW_POWER = 0.7;

        if(gamepad.right_bumper) {
            left.setPower(CLAW_POWER);
            right.setPower(CLAW_POWER);
        } else if(gamepad.left_bumper) {
            left.setPower(-CLAW_POWER);
            right.setPower(-CLAW_POWER);
        } else {
            left.setPower(0);
            right.setPower(0);
        }
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
