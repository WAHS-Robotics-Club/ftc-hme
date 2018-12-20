package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class LinearElevator extends RobotComponent {
    private DcMotorEx left, right;
    private final double POWER = 1.0;
    private final int TICKS_TO_CATCH = 0; //TODO: Measure this

    private void setPower(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    private void setMode(DcMotor.RunMode mode) {
        left.setMode(mode);
        right.setMode(mode);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        left = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.LIFT_LEFT);
        right = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.LIFT_RIGHT);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setTargetPositionTolerance(10);
        right.setTargetPositionTolerance(10);

        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); //TODO: Connect encoders and change this to RUN_USING_ENCODER
    }

    public void raiseToCatch() throws InterruptedException {
        stop();

        left.setTargetPosition(TICKS_TO_CATCH);
        right.setTargetPosition(TICKS_TO_CATCH);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lift();
        while(left.isBusy() && right.isBusy()) {
            Thread.sleep(1);
        }

        stop();
    }

    public void lowerToRest() throws InterruptedException {
        stop();

        left.setTargetPosition(-TICKS_TO_CATCH);
        right.setTargetPosition(-TICKS_TO_CATCH);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lower();
        while(left.isBusy() && right.isBusy()) {
            Thread.sleep(1);
        }

        stop();
    }

    public void lift() {
        setPower(POWER);
    }

    public void lower() {
        setPower(-POWER);
    }

    public void stop() {
        setPower(0);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
