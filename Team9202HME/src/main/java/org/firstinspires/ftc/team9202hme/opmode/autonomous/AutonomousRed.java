package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.MainAutonomousProgram;

@Autonomous(name = "Red")
public class AutonomousRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MainAutonomousProgram autonomous = new MainAutonomousProgram(this, AutonomousProgram.FieldSide.RED, true);
        autonomous.run();
    }
}
