package org.firstinspires.ftc.team9202hme.sensory;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.team9202hme.RobotComponent;
import org.firstinspires.ftc.team9202hme.util.ComplexSensorFactory;

public class VuMarkTracker extends RobotComponent {
    private VuforiaLocalizer vuforia;

    @Override
    public void init(HardwareMap hardwareMap) {
        vuforia = ComplexSensorFactory.getVuforiaInstance(hardwareMap, false);
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
