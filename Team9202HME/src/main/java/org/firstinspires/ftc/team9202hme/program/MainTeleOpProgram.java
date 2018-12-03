package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.motion.Hanger;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.MineralSampler;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;

public class MainTeleOpProgram extends TeleOpProgram {
    private MecanumDriveTrain driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    private Hanger hanger = new Hanger();
    private MineralSampler sampler = new MineralSampler();

    private Toggle samplerToggle = new Toggle();

    public MainTeleOpProgram(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void initialize() {
        driveTrain.init(opMode.hardwareMap);
        hanger.init(opMode.hardwareMap);
        sampler.init(opMode.hardwareMap);
    }

    @Override
    public void update() {
        Gamepad primary = opMode.gamepad1;

        float turnPower = primary.right_stick_x;
        Vector2 moveDirection = new Vector2();
        moveDirection.x = primary.left_stick_x;
        moveDirection.y = -primary.left_stick_y;

        driveTrain.move(moveDirection, turnPower);

        if(primary.right_trigger > 0) {
            hanger.lift(primary.right_trigger);
        } else if(primary.left_trigger > 0) {
            hanger.lower(primary.left_trigger);
        } else {
            hanger.stop();
        }

        if(primary.right_bumper) {
            hanger.tighten();
        } else if(primary.left_bumper) {
            hanger.loosen();
        } else {
            hanger.hold();
        }

        if(primary.a) {
            samplerToggle.toggle();
        }

        if(samplerToggle.isToggled()) {
            sampler.sample();
        } else {
            sampler.rest();
        }
    }

    @Override
    protected void updateTelemetry(Telemetry telemetry) {
//        driveTrain.logTelemetry(telemetry);
//        telemetry.update();
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }
}
