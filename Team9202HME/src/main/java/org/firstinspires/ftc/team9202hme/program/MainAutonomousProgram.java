package org.firstinspires.ftc.team9202hme.program;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.motion.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.DepotClaimer;
import org.firstinspires.ftc.team9202hme.sensory.Mineral;
import org.firstinspires.ftc.team9202hme.sensory.MineralDetector;

import java.util.List;

public class MainAutonomousProgram extends AutonomousProgram {
    public enum AdditionalSteps {
        NONE, CLAIM_ONLY, PARK_ONLY, CLAIM_AND_PARK;

        @Override
        public String toString() {
            switch(this) {
                case NONE:
                    return "Sample Only";
                case CLAIM_ONLY:
                    return "Sample and Claim";
                case PARK_ONLY:
                    return "Sample and Park";
                case CLAIM_AND_PARK:
                    return "Sample, Claim, and Park";
                default:
                    return "";
            }
        }
    }

    private HolonomicDriveTrain driveTrain = new MecanumDriveTrain();
    private LinearElevator lift = new LinearElevator();
    private DepotClaimer claimer = new DepotClaimer();

    private MineralDetector detector = new MineralDetector(true);
    private Mineral goldMineral;
    private int goldPosition = -2; //1 is left, 0 is middle, -1 is right, -2 is unknown

    private AdditionalSteps additionalSteps;

    public MainAutonomousProgram(LinearOpMode opMode, FieldPosition fieldPosition, AdditionalSteps additionalSteps) {
        super(opMode, AllianceColor.DONT_CARE, fieldPosition); //Alliance color doesn't matter in autonomous for FTC 2018-19 Rover Ruckus
        this.additionalSteps = additionalSteps;
    }

    private void locateGoldMineral(Mineral one, Mineral two, Mineral three) {
        Mineral silver1;
        Mineral silver2;

        if(one.isGold()) {
            goldMineral = one;
            silver1 = two;
            silver2 = three;
        } else if(two.isGold()) {
            goldMineral = two;
            silver1 = one;
            silver2 = three;
        } else {
            goldMineral = three;
            silver1 = one;
            silver2 = two;
        }

        double distGoldSilver1 = silver1.getOffset() - goldMineral.getOffset();
        double distGoldSilver2 = silver2.getOffset() - goldMineral.getOffset();

        if(distGoldSilver1 > 0 && distGoldSilver2 > 0) {
            goldPosition = 1;
        } else if((distGoldSilver1 < 0 && distGoldSilver2 > 0) || (distGoldSilver1 > 0 && distGoldSilver2 < 0)) {
            goldPosition = 0;
        } else {
            goldPosition = -1;
        }
    }

    @Override
    protected void initialize() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);
        claimer.init(opMode.hardwareMap);
        detector.init(opMode.hardwareMap);
        Thread.sleep(1000);

        //Watch field and track gold mineral until game starts
        while(!opMode.isStarted()) {
            List<Mineral> minerals = detector.getMineralsByCloseness();
            if(minerals.size() >= 3) {
                opMode.telemetry.addLine("Found 3 Minerals:");
                opMode.telemetry.addData("  Mineral 1", (minerals.get(0).isGold() ? "Gold" : "Silver") + " at " + minerals.get(0).getOffset());
                opMode.telemetry.addData("  Mineral 2", (minerals.get(1).isGold() ? "Gold" : "Silver") + " at " + minerals.get(1).getOffset());
                opMode.telemetry.addData("  Mineral 3", (minerals.get(2).isGold() ? "Gold" : "Silver") + " at " + minerals.get(2).getOffset());
                locateGoldMineral(minerals.get(0), minerals.get(1), minerals.get(2));

                String positionString = "Unknown";
                switch(goldPosition) {
                    case 1:
                        positionString = "Left";
                        break;
                    case 0:
                        positionString = "Middle";
                        break;
                    case -1:
                        positionString = "Right";
                        break;
                }

                opMode.telemetry.addLine();
                opMode.telemetry.addLine("Located Gold Mineral:");
                opMode.telemetry.addData("Type", goldMineral.isGold() ? "Gold" : "Silver");
                opMode.telemetry.addData("Position", positionString);
                opMode.telemetry.addData("Area", goldMineral.getArea());
                opMode.telemetry.addData("Offset", goldMineral.getOffset());
                opMode.telemetry.update();
            } else {
                opMode.telemetry.addLine("Looking for minerals...");
                opMode.telemetry.addLine("Only " + minerals.size() + " found.");
                opMode.telemetry.update();
            }

            Thread.sleep(500);
        }
    }

    @Override
    protected void start() throws InterruptedException {
        Thread lowerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lift.lowerToRest();
                } catch(InterruptedException e) {
                    opMode.requestOpModeStop();
                }
            }
        });

        //Lower down from lander
        lift.liftToCatch();

        //Move off of hook
        driveTrain.move(0, 0.5);
        Thread.sleep(1000);
        driveTrain.stop();

        //Move forward, away from lander
        driveTrain.move(90, 0.5);
        Thread.sleep(500);
        driveTrain.stop();

        //Re-center and lower elevator to resting position
        driveTrain.move(180, 0.5);
        Thread.sleep(1000);
        driveTrain.stop();
        lowerThread.start();

        //Sample mineral
        driveTrain.turnToHeading(goldPosition * 35);
        driveTrain.moveToDisplacement(90, goldPosition == 0 ? 24 : 28, 1.0);

        //Perform additional steps, if requested
        switch(additionalSteps) {
            case NONE:
                Thread.sleep(10000);
                break;
            case CLAIM_ONLY:
                claim();
                break;
            case PARK_ONLY:
                parkAfterSampling();
                break;
            case CLAIM_AND_PARK:
                claim();
                parkAfterClaiming();
                break;
        }
    }

    private void claim() throws InterruptedException {
        switch(fieldPosition) {
            case CRATER_FACING:
//                driveTrain.moveToDisplacement(90, goldPosition == 0 ? 24 : 28, 1.0);
//                driveTrain.turnToHeading();
                break;
            case DEPOT_FACING:
                driveTrain.rotateBy(-2 * goldPosition * 35);
                driveTrain.moveToDisplacement(90, 20, 1.0);
                claimer.claim();
                break;
            case DONT_CARE:
                break;
        }
    }

    private void parkAfterSampling() throws InterruptedException {
        switch(fieldPosition) {
            case CRATER_FACING:
                driveTrain.rotateBy(-2 * goldPosition * 35);
                driveTrain.moveToDisplacement(90, 15, 1.0);
                break;
            case DEPOT_FACING:
                driveTrain.moveToDisplacement(270, goldPosition == 0 ? 24 : 28, 1.0);
                driveTrain.turnToHeading(-85);
                driveTrain.moveToDisplacement(90, 60, 1.0);
                driveTrain.turnToHeading(-135);
                driveTrain.moveToDisplacement(90, 10, 1.0);
                break;
            case DONT_CARE:
                break;
        }
    }

    private void parkAfterClaiming() throws InterruptedException {
        driveTrain.turnToHeading(goldPosition <= 0 ? -135 : 135);
        driveTrain.moveToDisplacement(90, 72, 1.0);
    }
}
