package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.hardware.CubeGrabber;

public class MainTeleOpProgram extends TeleOpProgram {
    private MecanumDriveTrain driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    private CubeGrabber cubeGrabber = new CubeGrabber();
    private JewelWhacker jewelWhacker = new JewelWhacker();
    private boolean dualControl;

    public MainTeleOpProgram(OpMode opMode, boolean dualControl) {
        super(opMode);
        this.dualControl = dualControl;
    }

    @Override
    public void init() {
        cubeGrabber.init(opMode.hardwareMap);
        jewelWhacker.init(opMode.hardwareMap);
        driveTrain.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        Gamepad primary = opMode.gamepad1;
        Gamepad secondary = dualControl ? opMode.gamepad2 : opMode.gamepad1;

        driveTrain.driveControlled(primary);
        cubeGrabber.grabControlled(primary);

        if(secondary.a) {
            jewelWhacker.raise();
        } else if(secondary.b) {
            jewelWhacker.retract();
        }

//        updateTelemetry();
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);
        cubeGrabber.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
