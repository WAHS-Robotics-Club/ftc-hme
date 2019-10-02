package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.DepotClaimer;
import org.firstinspires.ftc.team9202hme.motion.MineralCollector;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;

public class MainTeleOpProgram extends TeleOpProgram {
    private MecanumDriveTrain driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    private LinearElevator lift = new LinearElevator();
    private MineralCollector collector = new MineralCollector();
    private DepotClaimer claimer = new DepotClaimer();

    private boolean dualDriver;

    private Toggle controlsToggle = new Toggle();
    private Toggle depositorToggle = new Toggle();
    private Toggle prepareHangToggle = new Toggle();
    private Toggle claimerToggle = new Toggle();

    public MainTeleOpProgram(OpMode opMode, boolean dualDriver) {
        super(opMode);
        this.dualDriver = dualDriver;
    }

    @Override
    public void initialize() {
        driveTrain.init(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);
        collector.init(opMode.hardwareMap);
        claimer.init(opMode.hardwareMap);

        claimer.raise();
    }

    @Override
    public void update() {
        Gamepad primary = opMode.gamepad1;
        Gamepad secondary = dualDriver ? opMode.gamepad2 : opMode.gamepad1;

        processPrimaryInput(primary);
        processSecondaryInput(secondary);
    }

    private void processPrimaryInput(Gamepad primary) {
        //Movement control
        if(primary.x) {
            controlsToggle.toggle();
        }

        float turnPower = primary.right_stick_x;
        Vector2 moveDirection = new Vector2();
        moveDirection.x = primary.left_stick_x * (controlsToggle.isToggled() ? -1 : 1);
        moveDirection.y = -primary.left_stick_y * (controlsToggle.isToggled() ? -1 : 1);

        driveTrain.move(moveDirection, turnPower);

        //Collector control
        if(primary.left_bumper) {
            collector.collect(0.5);
        } else {
            collector.store(primary.left_trigger > 0.85 ? primary.left_trigger : primary.left_trigger / 2);
        }

        //Depot claimer control
        if(primary.y) {
            claimerToggle.toggle();
        }

        if(claimerToggle.isToggled()) {
            claimer.lower();
        } else {
            claimer.raise();
        }

        //Depositor control
        if(primary.right_bumper) {
            depositorToggle.toggle();
        }

        if(primary.b) {
            prepareHangToggle.toggle();
        }

        if(prepareHangToggle.isToggled()) {
            collector.prepareForHanging();
        } else {
            if(depositorToggle.isToggled()) {
                collector.deposit();
            } else {
                collector.prepareStorage();
            }
        }
    }

    private void processSecondaryInput(Gamepad secondary) {
        //Linear actuator control
        if(secondary.dpad_up) {
            lift.lift();
        } else if(secondary.dpad_down) {
            lift.lower();
        } else {
            lift.stop();
        }
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
//        driveTrain.logTelemetry(telemetry);
        lift.logTelemetry(telemetry);
        telemetry.update();
    }

    @Override
    public void stop() {
        driveTrain.stop();
        lift.stop();
    }
}
