package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class Hanger extends RobotComponent {
    private DcMotor lift, winch;
    private final double WINCH_POWER = 0.2;

    @Override
    public void init(HardwareMap hardwareMap) {
        lift = hardwareMap.dcMotor.get(HardwareMapConstants.LIFT);
        winch = hardwareMap.dcMotor.get(HardwareMapConstants.WINCH);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void lift(double power) {
        lift.setPower(power);
    }

    public void lower(double power) {
        lift.setPower(-power);
    }

    public void stop() {
        lift.setPower(0);
    }

    public void tighten() {
        winch.setPower(-WINCH_POWER);
    }

    public void loosen() {
        winch.setPower(WINCH_POWER);
    }

    public void hold() {
        winch.setPower(0);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
