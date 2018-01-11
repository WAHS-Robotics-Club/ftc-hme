package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.Toggle;

public class JewelWhacker extends HardwareComponent {
    private CRServo whacker;
    private CRServo extender;
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    public enum JewelColor {
        RED, BLUE, UNKNOWN
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        whacker = hardwareMap.crservo.get(HardwareMapConstants.WHACKER_CRSERVO);
        extender = hardwareMap.crservo.get(HardwareMapConstants.WHACKER_EXTENDER_CRSERVO);
        colorSensor = hardwareMap.colorSensor.get(HardwareMapConstants.COLOR_DISTANCE_SENSOR);
        distanceSensor = hardwareMap.get(DistanceSensor.class, HardwareMapConstants.COLOR_DISTANCE_SENSOR);
    }

    public void extend() {
        extender.setPower(1);
    }

    public void stop() {
        extender.setPower(0);
        whacker.setPower(0);
    }

    public void retract() {
        extender.setPower(-1);
    }

    public void raise() {
        whacker.setPower(0.5);
    }

    public void lower() {
        whacker.setPower(-0.5);
    }

    public JewelColor readJewelColor() {
        final int TOLERANCE = 20;
        boolean blueJewel = false, redJewel = false;

        if(colorSensor.blue() - colorSensor.red() >= TOLERANCE) {
            blueJewel = true;
            redJewel = false;
        } else if(colorSensor.red() - colorSensor.blue() >= TOLERANCE) {
            redJewel = true;
            blueJewel = false;
        }

        return blueJewel ? JewelColor.BLUE : (redJewel ? JewelColor.RED : JewelColor.UNKNOWN);
    }

    public double readJewelDistance() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.addData("Alpha", colorSensor.alpha());
    }
}
