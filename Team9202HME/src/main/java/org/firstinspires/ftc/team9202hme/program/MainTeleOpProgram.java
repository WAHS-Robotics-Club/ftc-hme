package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.util.Vector2;

public class MainTeleOpProgram extends TeleOpProgram {
    private MecanumDriveTrain driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);

    public MainTeleOpProgram(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        Gamepad primary = opMode.gamepad1;

        float turnPower = primary.right_stick_x;
        Vector2 moveDirection = new Vector2();
        moveDirection.x = primary.left_stick_x;
        moveDirection.y = primary.left_stick_y;

        driveTrain.move(moveDirection, turnPower);
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
//        driveTrain.logTelemetry(opMode.telemetry);
//        opMode.telemetry.update();
    }
}
