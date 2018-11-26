package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.motion.DepotClaimer;
import org.firstinspires.ftc.team9202hme.motion.MineralSampler;

@TeleOp(name = "Appendage Test", group = "Tests")
//@Disabled
public class AppendageTest extends OpMode {
    private MineralSampler sampler = new MineralSampler();
    private DepotClaimer marker = new DepotClaimer();

    @Override
    public void init() {
        sampler.init(hardwareMap);
        marker.init(hardwareMap);
    }

    @Override
    public void loop() {
        if(gamepad1.a) {
            sampler.sample();
            marker.deposit();
        } else {
            sampler.rest();
            marker.rest();
        }
    }
}
