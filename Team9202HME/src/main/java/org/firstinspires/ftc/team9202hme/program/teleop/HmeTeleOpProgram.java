package org.firstinspires.ftc.team9202hme.program.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.RelicGrabber;
import org.firstinspires.ftc.team9202hme.hardware.ServoClaw;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;
import org.firstinspires.ftc.team9202hme.util.Toggle;

public class HmeTeleOpProgram extends TeleOpProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
    private ServoClaw claw = new ServoClaw();
    private RelicGrabber grabber = new RelicGrabber();
    private Servo jewelWhacker;
    private DcMotor spool;
    private boolean dualControl;

    private Toggle whackerToggle = new Toggle(0.25);

    public HmeTeleOpProgram(OpMode opMode, boolean dualControl) {
        super(opMode);
        this.dualControl = dualControl;
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
        claw.init(opMode.hardwareMap);
        grabber.init(opMode.hardwareMap);

        jewelWhacker = opMode.hardwareMap.servo.get(HardwareMapConstants.JEWEL_WHACKER_SERVO);
        spool = opMode.hardwareMap.dcMotor.get(HardwareMapConstants.PULLEY_SPOOL_DCMOTOR);

        whackerToggle.setToggle(true);
    }

    @Override
    public void loop() {
        Gamepad primary = new Gamepad();
        Gamepad secondary = new Gamepad();

        try { //Multiplying by -1 changes opMode.gamepad1 and 2's continuously inverts the power, so copy their states first to avoid that
            primary.copy(opMode.gamepad1);
            secondary.copy(dualControl ? opMode.gamepad2 : opMode.gamepad1);
        } catch(RobotCoreException e) {
            e.printStackTrace();
        }

        primary.left_stick_y *= -1; //Our gamepads have inverted y-axes for some reason
        primary.right_stick_y *= -1;

        driveTrain.driveControlled(primary);
        claw.grabControlled(primary);
        grabber.grabControlled(secondary);

        if(secondary.right_trigger > 0.05) {
            spool.setPower(secondary.right_trigger);
        } else if(secondary.left_trigger > 0.05) {
            spool.setPower(-secondary.left_trigger);
        } else {
            spool.setPower(0);
        }

        if(secondary.b) {
            whackerToggle.toggle();
        }

        if(whackerToggle.isToggled()) {
            jewelWhacker.setPosition(0.87);
        } else {
            jewelWhacker.setPosition(0.3);
        }

        updateTelemetry();
    }

    @Override
    public void stop() {
        driveTrain.stop();
    }

    private void updateTelemetry() {
        driveTrain.logTelemetry(opMode.telemetry);
        claw.logTelemetry(opMode.telemetry);
        grabber.logTelemetry(opMode.telemetry);

        opMode.telemetry.update();
    }
}
