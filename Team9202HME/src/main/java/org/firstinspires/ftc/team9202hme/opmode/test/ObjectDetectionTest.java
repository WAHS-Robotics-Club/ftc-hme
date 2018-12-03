package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.team9202hme.sensory.MineralDetector;
import org.firstinspires.ftc.team9202hme.util.ComplexSensorFactory;

import java.util.List;

@TeleOp(name = "Object Detection Test", group = "Tests")
//@Disabled
public class ObjectDetectionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MineralDetector detector = new MineralDetector(true);
        detector.init(hardwareMap);

        waitForStart();

        while(opModeIsActive()) {
            detector.logTelemetry(telemetry);
            telemetry.update();
            idle();
        }
    }
}
