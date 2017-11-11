package org.firstinspires.ftc.team9202hme.program.teleop;


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

    @Override
    public void loop() {
        Gamepad primary = new Gamepad();
        Gamepad secondary = new Gamepad();

        try { //Multiplying gamepad1 and 2's y-axes by -1 continuously inverts them back and forth, so copy their states first to avoid that
            primary.copy(opMode.gamepad1);
            secondary.copy(dualControl ? opMode.gamepad2 : opMode.gamepad1);
        } catch(RobotCoreException e) {
            e.printStackTrace();
        }

        primary.left_stick_y *= -1; //Apparently gamepads have inverted y-axes for some reason
        primary.right_stick_y *= -1;

        driveTrain.driveControlled(primary);
        cubeGrabber.grabControlled(primary);
        relicGrabber.grabControlled(secondary);
        jewelWhacker.whackControlled(secondary);

        updateTelemetry();
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);
        cubeGrabber.logTelemetry(opMode.telemetry);
        relicGrabber.logTelemetry(opMode.telemetry);
        jewelWhacker.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
