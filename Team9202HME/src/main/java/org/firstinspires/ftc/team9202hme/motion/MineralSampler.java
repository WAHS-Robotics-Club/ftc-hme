package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class MineralSampler extends RobotComponent {
    private Servo sampler;

    private final double RESTING_POS = 0;
    private final double SAMPLE_POS = 1;

    @Override
    public void init(HardwareMap hardwareMap) {
        sampler = hardwareMap.servo.get(HardwareMapConstants.SAMPLER);
        rest();
    }

    public void rest() {
        sampler.setPosition(RESTING_POS);
    }

    public void sample() {
        sampler.setPosition(SAMPLE_POS);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
