package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class LinearElevator extends RobotComponent {
    private DcMotorEx left, right;
    private double power;
    private final int TICKS_TO_CATCH = 24450;
    private final int TICKS_TO_COLLECTION_ZONE = 26500;

    private void setPower(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    private void setMode(DcMotor.RunMode mode) {
        left.setMode(mode);
        right.setMode(mode);
    }

    private void runToPosition(int position) throws InterruptedException {
        stop();

        left.setTargetPosition(position);
        right.setTargetPosition(position);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lift();
        while(left.isBusy() && right.isBusy()) {
            Thread.sleep(1);
        }

        stop();
    }

    public LinearElevator() {
        this(1.0);
    }

    public LinearElevator(double liftPower) {
        power = liftPower;
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        left = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.LIFT_LEFT);
        right = (DcMotorEx) hardwareMap.dcMotor.get(HardwareMapConstants.LIFT_RIGHT);

        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        left.setTargetPositionTolerance(20);
        right.setTargetPositionTolerance(20);

        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void liftToCatch() throws InterruptedException {
        runToPosition(TICKS_TO_CATCH);
    }

    public void liftToCollectionZone() throws InterruptedException {
        runToPosition(TICKS_TO_COLLECTION_ZONE);
    }

    public void lowerToRest() throws InterruptedException {
        runToPosition(-(left.getCurrentPosition() + right.getCurrentPosition()) / 2);
    }

    public void lift() {
        setPower(power);
    }

    public void lower() {
        setPower(-power);
    }

    public void stop() {
        setPower(0);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Lift", left.getCurrentPosition());
        telemetry.addData("Right Lift", right.getCurrentPosition());
    }
}
