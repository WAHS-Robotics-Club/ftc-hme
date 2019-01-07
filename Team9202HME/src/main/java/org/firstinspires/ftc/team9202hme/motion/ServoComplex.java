package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class ServoComplex extends RobotComponent {
    private final double PIVOT_LEFT = 0.38;
    private final double PIVOT_CENTER = 0.59;
    private final double PIVOT_RIGHT = 0.79;
    private final double SAMPLER_UP = 0.10;
    private final double SAMPLER_DOWN = 0.88;
    private final double CLAIMER_UP = 0;
    private final double CLAIMER_DOWN = 0.6;

    private Servo cameraPivot, claimer, sampler;

    @Override
    public void init(HardwareMap hardwareMap) {
        cameraPivot = hardwareMap.servo.get(HardwareMapConstants.CAMERA_PIVOT);
        claimer = hardwareMap.servo.get(HardwareMapConstants.DEPOT_CLAIMER);
        sampler = hardwareMap.servo.get(HardwareMapConstants.MINERAL_SAMPLER);
    }

    public void lookAway() {
        cameraPivot.setPosition(1);
    }

    public void lookLeft() {
        cameraPivot.setPosition(PIVOT_LEFT);
    }

    public void lookForward() {
        cameraPivot.setPosition(PIVOT_CENTER);
    }

    public void lookRight() {
        cameraPivot.setPosition(PIVOT_RIGHT);
    }

    public void raiseSampler() {
        sampler.setPosition(SAMPLER_UP);
    }

    public void prepareSampler() {
        sampler.setPosition((SAMPLER_DOWN + SAMPLER_UP) / 2);
    }

    public void lowerSampler() {
        sampler.setPosition(SAMPLER_DOWN);
    }

    public void raiseClaimer() {
        claimer.setPosition(CLAIMER_UP);
    }

    public void lowerClaimer() {
        claimer.setPosition(CLAIMER_DOWN);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
