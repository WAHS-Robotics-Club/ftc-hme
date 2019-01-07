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
        List<Mineral> craterMinerals = new ArrayList<>();

        for(int i = 0; i < minerals.length; i++) {
            minerals[i] = new Mineral(recognitions.get(i));
            if(minerals[i].getArea() <= 7000) {
                craterMinerals.add(minerals[i]);
            }
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
        sortedMinerals.removeAll(craterMinerals);

        return sortedMinerals;
    }

    public Mineral getGoldMineral() {
        update();

        List<Mineral> minerals = getMineralsByCloseness();
        List<Mineral> goldMinerals = new ArrayList<>();

        if(minerals != null) {
            for(Mineral m : minerals) {
//                if(m.isGold()) goldMinerals.add(m);
                if(m.isGold()) return m;
            }
        }

        return null;

//        if(goldMinerals.size() == 0) {
//            return null;
//        } else if(goldMinerals.size() == 1) {
//            return goldMinerals.get(0);
//        } else {
//            Object[] sortedMinerals = goldMinerals.toArray();
//            Arrays.sort(sortedMinerals, new Comparator<Object>() {
//                @Override
//                public int compare(Object mineral, Object t1) {
//                    Mineral m0 = (Mineral) mineral;
//                    Mineral m1 = (Mineral) t1;
//
//                    if(m0.getArea() == m1.getArea()) {
//                        return 0;
//                    } else {
//                        return m0.getArea() > m1.getArea() ? -1 : 1;
//                    }
//                }
//            });
//
//            return (Mineral) sortedMinerals[0];
//        }
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
