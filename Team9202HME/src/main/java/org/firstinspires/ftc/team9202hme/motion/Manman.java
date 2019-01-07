package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.RobotComponent;

public class Manman extends RobotComponent {
    private CRServo extender;
    private DcMotor manman;

    @Override
    public void init(HardwareMap hardwareMap) {
        extender = hardwareMap.crservo.get(HardwareMapConstants.EXTENDER);
        manman = hardwareMap.dcMotor.get(HardwareMapConstants.MANMAN);
    }

    public void extend(double power) {
        extender.setPower(power);
    }

    public void retract(double power) {
        extender.setPower(-power);
    }

    public void hold() {
        extender.setPower(0);
    }

    public void rotate(double power) {
        manman.setPower(power / 1.5);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
