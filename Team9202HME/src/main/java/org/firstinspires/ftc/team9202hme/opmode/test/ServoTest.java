package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Servo Calibration", group = "Tests")
//@Disabled
public class ServoTest extends OpMode {
    private Servo claimer, collector, depositor;
    private double claimerPos = 0;
    private double collectorPos = 0;
    private double depositorPos = 0;

    @Override
    public void init() {
        claimer = hardwareMap.servo.get(HardwareMapConstants.DEPOT_CLAIMER);
        collector = hardwareMap.servo.get(HardwareMapConstants.MINERAL_COLLECTOR);
        depositor = hardwareMap.servo.get(HardwareMapConstants.MINERAL_DEPOSITOR);
    }

    @Override
    public void loop() {
        claimer.setPosition(claimerPos);
        collector.setPosition(collectorPos);
        depositor.setPosition(depositorPos);

        telemetry.addData("Claimer", claimerPos);
        telemetry.addData("Collector", collectorPos);
        telemetry.addData("Depositor", depositorPos);
        telemetry.update();

        if(gamepad1.x) {
            claimerPos += 0.0025;
        } else if(gamepad1.y) {
            claimerPos -= 0.0025;
        }

        if(gamepad1.left_bumper) {
            collectorPos += 0.0025;
        } else if(gamepad1.right_bumper) {
            collectorPos -= 0.0025;
        }

        if(gamepad1.a) {
            depositorPos += 0.0025;
        } else if(gamepad1.b) {
            depositorPos -= 0.0025;
        }
    }
}
