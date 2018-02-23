package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.MainAutonomousProgram;

@Autonomous(name = "Blue (Jewel Only)")
public class NoMoveBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MainAutonomousProgram mainAutonomousProgram =
                new MainAutonomousProgram(this, AutonomousProgram.FieldSide.BLUE, AutonomousProgram.FieldPosition.LOWER, false);
        mainAutonomousProgram.run();
    }
}
