package org.firstinspires.ftc.team9202hme.opmode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.program.MainTeleOpProgram;

@TeleOp(name = "HME TeleOp - Single Driver", group = "HME")
public class SingleDriverTeleOp extends OpMode {
    private MainTeleOpProgram program = new MainTeleOpProgram(this, false);

    @Override
    public void init() {
        program.init();
        //hello world
        //this is a test by charlie
        //hi
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
