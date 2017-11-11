package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.util.Toggle;

public class JewelWhacker extends HardwareComponent {
    private Servo whacker;
    private ColorSensor colorSensor;

    private Toggle whackerToggle = new Toggle();

    private final double WHACKER_DOWN = 0.27;
    private final double WHACKER_UP = 0.875;

    public enum JewelColor {
        RED, BLUE, UNKNOWN
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        whacker = hardwareMap.servo.get(HardwareMapConstants.JEWEL_WHACKER_SERVO);
        colorSensor = hardwareMap.colorSensor.get(HardwareMapConstants.COLOR_SENSOR);

        whacker.setPosition(WHACKER_UP);
        whackerToggle.setToggle(true);
    }

    public void whackControlled(Gamepad gamepad) {
        if(gamepad.b) {
            whackerToggle.toggle();
        }

        if(whackerToggle.isToggled()) {
            raise();
        } else {
            lower();
        }
    }

    public void raise() {
        whacker.setPosition(WHACKER_UP);
    }

    public void lower() {
        whacker.setPosition(WHACKER_DOWN);
    }

    public JewelColor readJewelColor() {
        boolean blueJewel = false, redJewel = false;

        if(colorSensor.blue() - colorSensor.red() >= 1) {
            blueJewel = true;
            redJewel = false;
        } else if(colorSensor.red() - colorSensor.blue() >= 1) {
            redJewel = true;
            blueJewel = false;
        }

        return blueJewel ? JewelColor.BLUE : (redJewel ? JewelColor.RED : JewelColor.UNKNOWN);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());
    }
}
