package org.firstinspires.ftc.team9202hme.program;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.motion.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.ServoComplex;
import org.firstinspires.ftc.team9202hme.sensory.Mineral;
import org.firstinspires.ftc.team9202hme.sensory.MineralDetector;

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
    private ServoComplex servoComplex = new ServoComplex();

    private MineralDetector detector = new MineralDetector(true);
    private Mineral goldMineral;
    private int goldPosition;

    private AdditionalSteps additionalSteps;

    public MainAutonomousProgram(LinearOpMode opMode, FieldPosition fieldPosition, AdditionalSteps additionalSteps) {
        super(opMode, AllianceColor.DONT_CARE, fieldPosition); //Alliance color doesn't matter in autonomous for FTC 2018-19 Rover Ruckus
        this.additionalSteps = additionalSteps;
    }

//    private void locateGoldMineral(Mineral one, Mineral two, Mineral three) {
//        Mineral silver1;
//        Mineral silver2;
//
//        if(one.isGold()) {
//            goldMineral = one;
//            silver1 = two;
//            silver2 = three;
//        } else if(two.isGold()) {
//            goldMineral = two;
//            silver1 = one;
//            silver2 = three;
//        } else {
//            goldMineral = three;
//            silver1 = one;
//            silver2 = two;
//        }
//
//        double distGoldSilver1 = silver1.getOffset() - goldMineral.getOffset();
//        double distGoldSilver2 = silver2.getOffset() - goldMineral.getOffset();
//
//        if(distGoldSilver1 > 0 && distGoldSilver2 > 0) {
//            goldPosition = 2;
//        } else if(distGoldSilver1 < 0 && distGoldSilver2 > 0) {
//            goldPosition = 1;
//        } else {
//            goldPosition = 0;
//        }
//    }

    @Override
    protected void initialize() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);
        servoComplex.init(opMode.hardwareMap);
        servoComplex.lookAway();

        detector.init(opMode.hardwareMap);

        //Locate mineral
//        List<Mineral> minerals = detector.getMineralsByCloseness();
//        locateGoldMineral(minerals.get(0), minerals.get(1), minerals.get(2));
//
//        opMode.telemetry.addData("Position", goldPosition);
//        opMode.telemetry.addData("Type", goldMineral.isGold() ? "Gold" : "Silver");
//        opMode.telemetry.addData("Area", goldMineral.getArea());
//        opMode.telemetry.addData("Angle", goldMineral.getAngle());
//        opMode.telemetry.addData("Offset", goldMineral.getOffset());
//        opMode.telemetry.update();
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

        //Detach from lander
        lift.liftToCatch();

        driveTrain.move(0, 0.5);
        servoComplex.prepareSampler();
        Thread.sleep(1000);
        driveTrain.stop();

        driveTrain.move(90, 0.5);
        Thread.sleep(500);
        driveTrain.stop();

        driveTrain.move(180, 0.5);
        Thread.sleep(1000);
        driveTrain.stop();

        lowerThread.start();
        servoComplex.lookForward();
        driveTrain.turnToHeading(0);
        Thread.sleep(2000);

        //Locate mineral
        goldMineral = detector.getGoldMineral();
        goldPosition = 0;
        if(goldMineral == null) {
            servoComplex.lookLeft();
            Thread.sleep(2000);
            goldMineral = detector.getGoldMineral();
            goldPosition = 1;

            if(goldMineral == null) {
                servoComplex.lookRight();
                Thread.sleep(2000);
                goldMineral = detector.getGoldMineral();
                goldPosition = -1;
            }
        }

        //Sample mineral
        driveTrain.rotateBy(goldPosition * 35);
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
                servoComplex.lowerClaimer();
                Thread.sleep(750);
                servoComplex.raiseClaimer();
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
