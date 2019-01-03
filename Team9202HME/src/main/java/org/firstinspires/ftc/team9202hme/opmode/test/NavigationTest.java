package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.sensory.VuforiaNavigator;

@TeleOp(name = "Navigation Test", group = "Tests")
@Disabled
public class NavigationTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaNavigator navigator = new VuforiaNavigator();
        navigator.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            navigator.logTelemetry(telemetry);
            telemetry.update();
            idle();
        }
    }
}
