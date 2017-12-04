package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.hardware.RelicGrabber;
import org.firstinspires.ftc.team9202hme.hardware.CubeGrabber;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

public class MainTeleOpProgram extends TeleOpProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
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

//    private int counter = 0;
//    private double startTime = System.nanoTime() / 1e9d;

    @Override
    public void loop() {
//        startTime = System.nanoTime() / 1e9d;
        Gamepad primary = opMode.gamepad1;
        Gamepad secondary = dualControl ? opMode.gamepad2 : primary;

        driveTrain.driveControlled(primary);
        cubeGrabber.grabControlled(primary);
        relicGrabber.grabControlled(secondary);
        jewelWhacker.whackControlled(secondary);

//        counter++;
//        opMode.telemetry.addData("Loops per second", counter / ((System.nanoTime() / 1e9d) - startTime));

//        updateTelemetry();
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
