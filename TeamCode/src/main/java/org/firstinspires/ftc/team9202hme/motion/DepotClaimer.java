package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class DepotClaimer extends RobotComponent {
    private Servo claimer;

    @Override
    public void init(HardwareMap hardwareMap) {
        claimer = hardwareMap.servo.get(HardwareMapConstants.DEPOT_CLAIMER);
    }

    public void raise() {
        claimer.setPosition(0.9);
    }

    public void lower() {
        claimer.setPosition(0.2);
    }

    public void claim() throws InterruptedException {
        lower();
        Thread.sleep(500);
        raise();
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
