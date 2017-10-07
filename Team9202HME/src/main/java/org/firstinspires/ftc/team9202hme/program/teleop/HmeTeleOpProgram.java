package org.firstinspires.ftc.team9202hme.program.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

public class HmeTeleOpProgram extends TeleOpProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);

    public HmeTeleOpProgram(OpMode opMode) {
        super(opMode);
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        opMode.gamepad1.left_stick_y *= -1; //Our gamepad's joysticks have inverted y-axes for some reason
        opMode.gamepad1.right_stick_y *= -1;
        opMode.gamepad2.left_stick_y *= -1;
        opMode.gamepad2.right_stick_y *= -1;

        driveTrain.driveControlled(opMode.gamepad1);

        updateTelemetry();
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
