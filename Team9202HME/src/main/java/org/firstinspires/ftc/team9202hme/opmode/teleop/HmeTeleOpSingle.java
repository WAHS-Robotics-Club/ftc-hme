package org.firstinspires.ftc.team9202hme.opmode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.program.teleop.HmeTeleOpProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

@TeleOp(name = "HME TeleOp - Single Driver", group = "HME")
public class HmeTeleOpSingle extends OpMode {
    private HmeTeleOpProgram robot = new HmeTeleOpProgram(this, false);

    @Override
    public void init() {
        robot.init();
    }

    @Override
    public void start() {
        robot.start();
    }

    @Override
    public void loop() {
        robot.loop();
    }

    @Override
    public void stop() {
        robot.stop();
    }
}
