package org.firstinspires.ftc.team9202hme.opmode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.program.teleop.MainTeleOpProgram;

@TeleOp(name = "HME TeleOp - Dual Driver", group = "HME")
public class HmeTeleOpDual extends OpMode {
    private MainTeleOpProgram robot = new MainTeleOpProgram(this, true);

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
