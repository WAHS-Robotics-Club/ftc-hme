package org.firstinspires.ftc.team9202hme.program.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.CRServoClaw;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

public class HmeTeleOpProgram extends TeleOpProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
    private CRServoClaw claw = new CRServoClaw();
    private CRServo jewelWhacker;
    private DcMotor spool;
    
    private boolean dualControl;

    public HmeTeleOpProgram(OpMode opMode, boolean dualControl) {
        super(opMode);
        this.dualControl = dualControl;
    }

    @Override
    public void init() {
        driveTrain.init(opMode.hardwareMap);
        claw.init(opMode.hardwareMap);
        jewelWhacker = opMode.hardwareMap.crservo.get(HardwareMapConstants.JEWEL_WHACKER_CRSERVO);
        spool = opMode.hardwareMap.dcMotor.get(HardwareMapConstants.PULLEY_SPOOL_DCMOTOR);

        opMode.gamepad1.left_stick_y *= -1; //Our gamepad's joysticks have inverted y-axes for some reason
        opMode.gamepad1.right_stick_y *= -1;//Not sure why, but this negation seems to hold for the duration
        opMode.gamepad2.left_stick_y *= -1; //the program,;I guess the Gamepad class stores some internal state
        opMode.gamepad2.right_stick_y *= -1;//as opposed to just reading from the attached gamepad(s)
    }

    @Override
    public void loop() {
        Gamepad primary = opMode.gamepad1;
        Gamepad secondary = dualControl ? opMode.gamepad2 : primary;

        driveTrain.driveControlled(primary);
        claw.grabControlled(primary);

        if(secondary.right_trigger > 0.05) {
            spool.setPower(secondary.right_trigger);
        } else if(secondary.left_trigger > 0.05) {
            spool.setPower(-secondary.left_trigger);
        } else {
            spool.setPower(0);
        }

        if(secondary.a) {
            jewelWhacker.setPower(0.7);
        } else if(secondary.b) {
            jewelWhacker.setPower(-0.7);
        } else {
            jewelWhacker.setPower(0);
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

        opMode.telemetry.update();
    }
}
