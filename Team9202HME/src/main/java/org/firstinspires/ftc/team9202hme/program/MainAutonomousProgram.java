package org.firstinspires.ftc.team9202hme.program;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.util.TelemetryManager;

public class MainAutonomousProgram extends AutonomousProgram {
    public MainAutonomousProgram(LinearOpMode opMode, AllianceColor allianceColor, FieldPosition fieldPosition) {
        super(opMode, allianceColor, fieldPosition);
    }

    @Override
    protected void initialize() throws InterruptedException {
        Telemetry t = TelemetryManager.getTelemetry();

        t.addData("Alliance Color", allianceColor);
        t.addData("Field Position", fieldPosition);
        t.update();
    }

    @Override
    protected void start() throws InterruptedException {

    }
}
