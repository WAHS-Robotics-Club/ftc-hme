package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.motion.LinearElevator;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.util.Toggle;
import org.firstinspires.ftc.team9202hme.util.Vector2;

public class MainTeleOpProgram extends TeleOpProgram {
    private MecanumDriveTrain driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    private LinearElevator lift = new LinearElevator();
    private boolean dualDriver;

    public MainTeleOpProgram(OpMode opMode, boolean dualDriver) {
        super(opMode);
        this.dualDriver = dualDriver;
    }

    @Override
    public void initialize() {
        driveTrain.init(opMode.hardwareMap);
        lift.init(opMode.hardwareMap);
    }

    @Override
    public void update() {
        Gamepad primary = opMode.gamepad1;
        Gamepad secondary = dualDriver ? opMode.gamepad2 : opMode.gamepad1;

        float turnPower = primary.right_stick_x;
        Vector2 moveDirection = new Vector2();
        moveDirection.x = primary.left_stick_x;
        moveDirection.y = -primary.left_stick_y;

        driveTrain.move(moveDirection, turnPower);

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
//        lift.logTelemetry(telemetry);
//        telemetry.update();
    }

    @Override
    public void stop() {
        driveTrain.stop();
        lift.stop();
    }
}
