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
import java.util.Comparator;
import java.util.List;

public class MineralDetector extends RobotComponent {
    private TFObjectDetector tfObjectDetector;
    private List<Recognition> recognitions = new ArrayList<>();

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

    public List<Mineral> getMineralsByCloseness() {
        update();
        Mineral[] minerals = new Mineral[recognitions.size()];
//        List<Mineral> craterMinerals = new ArrayList<>();

        for(int i = 0; i < minerals.length; i++) {
            minerals[i] = new Mineral(recognitions.get(i));
        }

        Arrays.sort(minerals, new Comparator<Mineral>() {
            @Override
            public int compare(Mineral mineral, Mineral t1) {
                if(mineral.getArea() == t1.getArea()) {
                    return 0;
                } else {
                    return mineral.getArea() > t1.getArea() ? -1 : 1;
                }
            }
        });

        List<Mineral> sortedMinerals = new ArrayList<>(Arrays.asList(minerals));
//        sortedMinerals.removeAll(craterMinerals);

        return sortedMinerals;
    }

    public Mineral getGoldMineral() {
        update();

        List<Mineral> minerals = getMineralsByCloseness();

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
            for(Mineral mineral : getMineralsByCloseness()) {
                telemetry.addData("Type", mineral.isGold() ? "Gold" : "Silver");
                telemetry.addData("Area", mineral.getArea());
                telemetry.addData("Offset", mineral.getOffset());
                telemetry.addLine();
            }
        }
    }
}
