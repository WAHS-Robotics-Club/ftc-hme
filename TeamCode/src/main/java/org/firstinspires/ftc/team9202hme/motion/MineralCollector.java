package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class MineralCollector extends RobotComponent {
    private DcMotor collector;
    private Servo depositor;

    @Override
    public void init(HardwareMap hardwareMap) {
        collector = hardwareMap.dcMotor.get(HardwareMapConstants.MINERAL_COLLECTOR);
        depositor = hardwareMap.servo.get(HardwareMapConstants.MINERAL_DEPOSITOR);
        collector.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void collect(double power) {
        collector.setPower(power);
    }

    public void store(double power) {
        collector.setPower(-power);
    }

    public void prepareStorage() {
        depositor.setPosition(1);
    }

    public void deposit() {
        depositor.setPosition(0);
    }

    public void prepareForHanging() {
        depositor.setPosition(0.75);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
