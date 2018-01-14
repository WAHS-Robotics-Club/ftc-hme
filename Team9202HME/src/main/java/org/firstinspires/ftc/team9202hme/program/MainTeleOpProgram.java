package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.OmniDirectionalDrive;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.hardware.RelicGrabber;
import org.firstinspires.ftc.team9202hme.hardware.CubeGrabber;

public class MainTeleOpProgram extends TeleOpProgram {
    private OmniDirectionalDrive driveTrain = new MecanumDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
    private CubeGrabber cubeGrabber = new CubeGrabber();
    private RelicGrabber relicGrabber = new RelicGrabber();
    private JewelWhacker jewelWhacker = new JewelWhacker();
    private boolean dualControl;

    public MainTeleOpProgram(OpMode opMode, boolean dualControl) {
        super(opMode);
        this.dualControl = dualControl;
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
        cubeGrabber.init(opMode.hardwareMap);
        relicGrabber.init(opMode.hardwareMap);
        jewelWhacker.init(opMode.hardwareMap);
    }

    @Override
    public void loop() {
        driveTrain.driveControlled(opMode.gamepad1);
        cubeGrabber.grabControlled(opMode.gamepad1);
        if(opMode.gamepad1.a) jewelWhacker.raise();
        if(dualControl) {
            relicGrabber.grabControlled(opMode.gamepad2);
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
        relicGrabber.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
