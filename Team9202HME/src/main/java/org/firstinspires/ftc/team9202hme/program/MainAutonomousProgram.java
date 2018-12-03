package org.firstinspires.ftc.team9202hme.program;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.motion.DepotClaimer;
import org.firstinspires.ftc.team9202hme.motion.Hanger;
import org.firstinspires.ftc.team9202hme.motion.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.MineralSampler;
import org.firstinspires.ftc.team9202hme.sensory.Mineral;
import org.firstinspires.ftc.team9202hme.sensory.MineralDetector;

public class MainAutonomousProgram extends AutonomousProgram {
    private HolonomicDriveTrain driveTrain = new MecanumDriveTrain();
    private MineralSampler sampler = new MineralSampler();
    private DepotClaimer claimer = new DepotClaimer();
    private Hanger hanger = new Hanger();

    private MineralDetector detector = new MineralDetector(true);

    public MainAutonomousProgram(LinearOpMode opMode, FieldPosition fieldPosition) {
        super(opMode, AllianceColor.DONT_CARE, fieldPosition); //Alliance color doesn't matter in autonomous for FTC 2018-19 Rover Ruckus
    }

    @Override
    protected void initialize() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);
        sampler.init(opMode.hardwareMap);
        claimer.init(opMode.hardwareMap);
        hanger.init(opMode.hardwareMap);

        detector.init(opMode.hardwareMap);
    }

    @Override
    protected void start() throws InterruptedException {

    }
}
