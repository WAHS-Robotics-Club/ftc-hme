package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.util.Toggle;

@TeleOp(name = "Linear Actuator Calibration", group = "Tests")
//@Disabled
public class LinearActuatorTest extends OpMode {
    private Toggle reverseToggle = new Toggle();
    private DcMotor left, right;

    @Override
    public void init() {
        reverseToggle.setToggle(false);
        left = hardwareMap.dcMotor.get(HardwareMapConstants.LIFT_LEFT);
        right = hardwareMap.dcMotor.get(HardwareMapConstants.LIFT_RIGHT);
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            reverseToggle.toggle();
        }

        double leftPower = reverseToggle.isToggled() ? -gamepad1.left_trigger : gamepad1.left_trigger;
        double rightPower = reverseToggle.isToggled() ? -gamepad1.right_trigger : gamepad1.right_trigger;

        left.setPower(leftPower);
        right.setPower(rightPower);
    }
}
