package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.autonomous.JewelAutonomous;

@Autonomous(name = "Jewel Autonomous - Red")
public class JewelAutonomousRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        JewelAutonomous autonomous = new JewelAutonomous(this, AutonomousProgram.FieldSide.RED);
        autonomous.run();
    }
}
