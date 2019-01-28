package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class MineralCollector extends RobotComponent {
    private Servo collector, depositor;

    @Override
    public void init(HardwareMap hardwareMap) {
        collector = hardwareMap.servo.get(HardwareMapConstants.MINERAL_COLLECTOR);
        depositor = hardwareMap.servo.get(HardwareMapConstants.MINERAL_DEPOSITOR);
    }

    public void collect() {
        collector.setPosition(0);
    }

    public void store() {
        collector.setPosition(1);
    }

    public void prepareStorage() {
        depositor.setPosition(0);
    }

    public void deposit() {
        depositor.setPosition(1);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
