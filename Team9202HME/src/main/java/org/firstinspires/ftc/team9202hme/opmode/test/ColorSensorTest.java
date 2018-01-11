package org.firstinspires.ftc.team9202hme.opmode.test;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

@TeleOp(name = "Color Sensor Test", group = "Tests")
//@Disabled
public class ColorSensorTest extends OpMode {
    private ColorSensor colorSensor;

    @Override
    public void init() {
        colorSensor = hardwareMap.colorSensor.get(HardwareMapConstants.COLOR_DISTANCE_SENSOR);

        colorSensor.enableLed(true);
    }

    @Override
    public void loop() {
        telemetry.addData("Red", colorSensor.red());
        telemetry.addData("Green", colorSensor.green());
        telemetry.addData("Blue", colorSensor.blue());
        telemetry.addData("Alpha", colorSensor.alpha());
    }
}
