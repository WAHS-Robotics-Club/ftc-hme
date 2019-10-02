package org.firstinspires.ftc.team9202hme.opmode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.program.MainTeleOpProgram;

@TeleOp(name = "HME TeleOp - Dual Driver", group = "HME")
public class DualDriverTeleOp extends OpMode {
    private MainTeleOpProgram program = new MainTeleOpProgram(this, true);

    @Override
    public void init() {
        program.init();
    }

    @Override
    public void start() {
        program.start();
    }

    @Override
    public void loop() {
        program.loop();
    }

    @Override
    public void stop() {
        program.stop();
    }
}
