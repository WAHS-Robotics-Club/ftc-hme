package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class DepotClaimer extends RobotComponent {
    private Servo marker;

    private final double RESTING_POS = 1;
    private final double DEPOSIT_POS = 0;

    @Override
    public void init(HardwareMap hardwareMap) {
        marker = hardwareMap.servo.get(HardwareMapConstants.MARKER);
        rest();
    }

    public void rest() {
        marker.setPosition(RESTING_POS);
    }

    public void deposit() {
        marker.setPosition(DEPOSIT_POS);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
