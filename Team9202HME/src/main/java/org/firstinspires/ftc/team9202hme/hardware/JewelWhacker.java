package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

public class JewelWhacker extends HardwareComponent {
    private Servo whacker;
    private CRServo extender;
    private ColorSensor colorSensor;
    private DistanceSensor distanceSensor;

    public enum JewelColor {
        RED, BLUE, UNKNOWN
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        whacker = hardwareMap.servo.get(HardwareMapConstants.WHACKER_SERVO);
        extender = hardwareMap.crservo.get(HardwareMapConstants.WHACKER_EXTENDER_CRSERVO);
        colorSensor = hardwareMap.colorSensor.get(HardwareMapConstants.COLOR_DISTANCE_SENSOR);
        distanceSensor = hardwareMap.get(DistanceSensor.class, HardwareMapConstants.COLOR_DISTANCE_SENSOR);

        raise();
    }

    public void extend(double distance) throws InterruptedException {
        extender.setPower(-1);
        Thread.sleep((int) (1000 * distance / 3));
        stop();
    }

    public void stop() {
        extender.setPower(0);
    }

    public void retract(double distance) throws InterruptedException {
        extender.setPower(1);
        Thread.sleep((int) (1000 * distance / 3));
        stop();
    }

    public void raise() {
        whacker.setPosition(0.95);
    }

    public void lower() {
        whacker.setPosition(0.244);
    }

    public JewelColor readJewelColor() {
        final int TOLERANCE = 12;
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
