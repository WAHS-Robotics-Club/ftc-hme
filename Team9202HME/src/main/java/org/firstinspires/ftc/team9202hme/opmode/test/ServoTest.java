package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Servo Calibration", group = "Tests")
//@Disabled
public class ServoTest extends OpMode {
    private Servo pivot, claimer, sampler;
    double pivotPos = 0;
    double claimerPos = 0;
    double samplerPos = 0;

    @Override
    public void init() {
        pivot = hardwareMap.servo.get(HardwareMapConstants.CAMERA_PIVOT);
        claimer = hardwareMap.servo.get(HardwareMapConstants.DEPOT_CLAIMER);
        sampler = hardwareMap.servo.get(HardwareMapConstants.MINERAL_SAMPLER);
    }

    @Override
    public void loop() {
        pivot.setPosition(pivotPos);
        claimer.setPosition(claimerPos);
        sampler.setPosition(samplerPos);

        telemetry.addData("Pivot", pivotPos);
        telemetry.addData("Claimer", claimerPos);
        telemetry.addData("Sampler", samplerPos);
        telemetry.update();

        if(gamepad1.left_bumper) {
            pivotPos += 0.0025;
        } else if(gamepad1.right_bumper) {
            pivotPos -= 0.0025;
        }

        if(gamepad1.a) {
            samplerPos += 0.0025;
        } else if(gamepad1.b) {
            samplerPos -= 0.0025;
        }

        if(gamepad1.x) {
            claimerPos += 0.0025;
        } else if(gamepad1.y) {
            claimerPos -= 0.0025;
        }
    }
}
