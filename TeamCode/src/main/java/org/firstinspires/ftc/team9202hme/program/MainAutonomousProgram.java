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

    private MecanumDriveTrain driveTrain = new MecanumDriveTrain();
    private LinearElevator lift = new LinearElevator();
    private DepotClaimer claimer = new DepotClaimer();

    private MineralDetector detector = new MineralDetector(true);
    private Mineral goldMineral;
    private int goldPosition = -2; //1 is left, 0 is middle, -1 is right, -2 is unknown
    private double angle;

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
        driveTrain.initImu(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);
        claimer.init(opMode.hardwareMap);
        detector.init(opMode.hardwareMap);
        Thread.sleep(1000);

        //Watch field and track gold mineral until game starts
        while(!opMode.isStarted()) {
            List<Mineral> minerals = detector.getMineralsByCloseness();
            if(minerals.size() >= 3) {
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
                opMode.telemetry.addLine("Located Target Mineral:");
                opMode.telemetry.addData("Type", goldMineral.isGold() ? "Gold" : "Silver");
                opMode.telemetry.addData("Position", positionString);
                opMode.telemetry.addData("Angle", goldMineral.getAngle());
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

    private final int MINERAL_ANGLE = 38;
    private double landedHeading;

    @Override
    protected void start() throws InterruptedException {
        Thread liftThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lift.liftToCollectionZone();
                } catch(InterruptedException e) {
                    opMode.requestOpModeStop();
                }
            }
        });

        //Lower down from lander
        lift.liftToCatch();

        //Move off of hook
        driveTrain.moveToDisplacement(90, 2, 0.5);
        Thread.sleep(500);
        driveTrain.move(0, 0.5);
        Thread.sleep(550);
        driveTrain.stop();

        //Move forward, away from lander
        driveTrain.moveToDisplacement(90, 4, 0.5);

        //Re-center and lower elevator to collecting position
        driveTrain.move(180, 0.5);
        Thread.sleep(550);
        driveTrain.stop();
        liftThread.start();

        Thread.sleep(100);
        landedHeading = driveTrain.getHeading();

        //Sample mineral
        driveTrain.rotateBy(goldPosition * MINERAL_ANGLE);
        driveTrain.moveToDisplacement(90, goldPosition == 0 ? 24 : 38, 1.0);

        //Perform additional steps, if requested
        switch(additionalSteps) {
            case NONE:
                //Wait for elevator to lower
                Thread.sleep(10000);
                break;
            case CLAIM_ONLY:
                claim();
                //Back out of depot
                driveTrain.moveToDisplacement(270, 10, 1.0);
                //Wait for elevator to lower
                Thread.sleep(5000);
                break;
            case PARK_ONLY:
                parkAfterSampling();
                //Wait for elevator to lower
                Thread.sleep(5000);
                break;
            case CLAIM_AND_PARK:
                claim();
                parkAfterClaiming();
                break;
        }
    }

    private void parkAfterSampling() throws InterruptedException {
        switch(fieldPosition) {
            case CRATER_FACING:
                //Rotate to face crater
//                driveTrain.turnToHeading(landedHeading);
                //Drive forward onto crater edge
                driveTrain.moveToDisplacement(90, 2 - Math.abs(goldPosition), 1.0);
                break;
            case DEPOT_FACING:
                //Don't know what to do here (TODO)
                Thread.sleep(10000);
                break;
            case DONT_CARE:
                break;
        }
    }

    private void claim() throws InterruptedException {
        switch(fieldPosition) {
            case CRATER_FACING:
                //Don't know what to do here (TODO)
                Thread.sleep(10000);
                break;
            case DEPOT_FACING:
                //Rotate to face depot
                driveTrain.turnToHeading(-goldPosition * 45 + landedHeading);
                //Drive into depot and claim
                driveTrain.moveToDisplacement(90, goldPosition == 0 ? 15 : 28, 1.0);
                //If on the right side, turn inwards a bit to make sure that crown is scored properly in depot. Otherwise, just drop the crown.
                if(goldPosition == -1) {
                    driveTrain.rotateBy(-10);
                    claimer.claim();
                    driveTrain.rotateBy(10);
                } else {
                    claimer.claim();
                }
                break;
            case DONT_CARE:
                break;
        }
    }

    private void parkAfterClaiming() throws InterruptedException {
        switch(fieldPosition) {
            case CRATER_FACING:
                //Don't know what to do here (TODO)
                Thread.sleep(10000);
                break;
            case DEPOT_FACING:
                if(goldPosition != 0) {
                    //Strafe to wall (should already be pretty close to wall) to avoid messing up another sampling field
                    driveTrain.move(goldPosition == 1 ? 180 : 0, 0.5);
                    Thread.sleep(500);
                    //Back up against crater
                    driveTrain.moveToDisplacement(270, 72, 1.0);
                } else {
                    //Back up behind sampling row
                    driveTrain.moveToDisplacement(270, 35, 1.0);
                    //Strafe to wall
                    driveTrain.move(0, 1.0);
                    Thread.sleep(3000);
                    //Rotate to face depot
                    driveTrain.turnToHeading(45 + landedHeading);
                    //Back up into crater
                    driveTrain.moveToDisplacement(270, 20, 1.0);
                }
                break;
            case DONT_CARE:
                break;
        }
    }
}
