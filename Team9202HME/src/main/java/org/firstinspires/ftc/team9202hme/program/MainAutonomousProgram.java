package org.firstinspires.ftc.team9202hme.program;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.motion.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.sensory.MineralDetector;

public class MainAutonomousProgram extends AutonomousProgram {
    public enum AdditionalSteps {
        None, SampleOnly, ClaimOnly, SampleAndClaim
    }

    private HolonomicDriveTrain driveTrain = new MecanumDriveTrain();
    private LinearElevator lift = new LinearElevator();

    private MineralDetector detector = new MineralDetector(true);

    private AdditionalSteps additionalSteps;

    public MainAutonomousProgram(LinearOpMode opMode, FieldPosition fieldPosition, AdditionalSteps additionalSteps) {
        super(opMode, AllianceColor.DONT_CARE, fieldPosition); //Alliance color doesn't matter in autonomous for FTC 2018-19 Rover Ruckus
        this.additionalSteps = additionalSteps;
    }

    @Override
    protected void initialize() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);

        detector.init(opMode.hardwareMap);
    }

    @Override
    protected void start() throws InterruptedException {

    }
}
