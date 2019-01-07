package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.motion.Manman;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.ServoComplex;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;

public class MainTeleOpProgram extends TeleOpProgram {
    private MecanumDriveTrain driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    private LinearElevator lift = new LinearElevator();
    private ServoComplex servoComplex = new ServoComplex();
    private Manman manman = new Manman();

    private boolean dualDriver;

    private Toggle samplerToggle = new Toggle();
    private Toggle controlsToggle = new Toggle();
    private Toggle claimerToggle = new Toggle();

    public MainTeleOpProgram(OpMode opMode, boolean dualDriver) {
        super(opMode);
        this.dualDriver = dualDriver;
    }

    @Override
    public void initialize() {
        driveTrain.init(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);
        servoComplex.init(opMode.hardwareMap);
        manman.init(opMode.hardwareMap);

        servoComplex.raiseClaimer();
        servoComplex.lookAway();
    }

    @Override
    public void update() {
        Gamepad primary = opMode.gamepad1;
        Gamepad secondary = dualDriver ? opMode.gamepad2 : opMode.gamepad1;

        float turnPower = primary.right_stick_x;
        Vector2 moveDirection = new Vector2();
        moveDirection.x = primary.left_stick_x;
        moveDirection.y = -primary.left_stick_y;

        if(primary.x) {
            controlsToggle.toggle();
        }

        if(controlsToggle.isToggled()) {
            moveDirection.x *= -1;
            moveDirection.y *= -1;
        }

        driveTrain.move(moveDirection, turnPower);

        if(secondary.dpad_up) {
            lift.lift();
        } else if(secondary.dpad_down) {
            lift.lower();
        } else {
            lift.stop();
        }

        if(primary.a) {
            samplerToggle.toggle();
        }

        if(primary.b) {
            claimerToggle.toggle();
        }

        if(samplerToggle.isToggled()) {
            servoComplex.lowerSampler();
        } else {
            servoComplex.raiseSampler();
        }

        if(claimerToggle.isToggled()) {
            servoComplex.lowerClaimer();
        } else {
            servoComplex.raiseClaimer();
        }

        if(secondary.right_bumper) {
            manman.extend(1);
        } else if(secondary.left_bumper) {
            manman.retract(1);
        } else {
            manman.hold();
        }

        if(secondary.right_trigger > 0.01) {
            manman.rotate(secondary.right_trigger);
        } else if(secondary.left_trigger > 0.01) {
            manman.rotate(-secondary.left_trigger);
        } else {
            manman.rotate(0);
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
