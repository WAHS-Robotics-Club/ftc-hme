package org.firstinspires.ftc.team9202hme.sensory;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.team9202hme.RobotComponent;
import org.firstinspires.ftc.team9202hme.util.ComplexSensorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MineralDetector extends RobotComponent {
    private TFObjectDetector tfObjectDetector;
    private List<Recognition> recognitions = new ArrayList<>();

    private final double SCREEN_WIDTH = 0;
    private final double SCREEN_HEIGHT = 0;

    private boolean provideCameraFeedback;

    public MineralDetector(boolean provideCameraFeedback) {
        this.provideCameraFeedback = provideCameraFeedback;
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        tfObjectDetector = ComplexSensorFactory.getTensorFlowObjectDetector(hardwareMap, provideCameraFeedback);
        tfObjectDetector.activate();
        update();
    }

    public void update() {
        List<Recognition> updatedList = tfObjectDetector.getUpdatedRecognitions();

        if(updatedList != null) {
            recognitions = updatedList;
        }
    }

    public List<Mineral> getMinerals() {
        update();
        List<Mineral> minerals = new ArrayList<>();

        for(Recognition recognition : recognitions) {
            minerals.add(new Mineral(recognition));
        }

        return minerals;
    }

    public Mineral getGoldMineral() {
        update();

        List<Mineral> minerals = getMinerals();
        if(minerals != null) {
            for(Mineral m : minerals) {
                if(m.isGold()) return m;
            }
        }

        return null;
    }

    @Override
    public void logTelemetry(Telemetry telemetry) {
        update();
        if(recognitions != null && recognitions.size() > 0) {
            for(Recognition r : recognitions) {
                telemetry.addData("Angle", r.estimateAngleToObject(AngleUnit.DEGREES));
                telemetry.addData("Left", r.getLeft());
                telemetry.addData("Right", r.getRight());
                telemetry.addData("Top", r.getTop());
                telemetry.addData("Bottom", r.getBottom());
                telemetry.addData("Label", r.getLabel());
            }
        }
    }
}
