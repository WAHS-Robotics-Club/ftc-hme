package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.MainAutonomousProgram;

@Autonomous(name = "Lower Blue")
public class LowerBlue extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MainAutonomousProgram mainAutonomousProgram =
                new MainAutonomousProgram(this, AutonomousProgram.FieldSide.BLUE, AutonomousProgram.FieldPosition.LOWER, true);
        mainAutonomousProgram.run();
    }
}
