package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name ="Main Autonomous - Charlie")
public class MainAutonomousProgram extends LinearOpMode {
    DriveTrain driveTrain;
    Grabber grabber;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
        grabber = Grabber.initGrabber(hardwareMap);

        DriveTrain.setTargetPosition(driveTrain, 9000);
        DriveTrain.setRunMode(driveTrain, DcMotor.RunMode.RUN_TO_POSITION);
        DriveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.addData("FL TARGET POSITION", driveTrain.flMotor.getTargetPosition());
        telemetry.addData("FL MODE", driveTrain.flMotor.getMode());
        telemetry.update();


        for(int i = 0; i < 10000; i++){
            DriveTrain.goDirection(driveTrain, "forwards");
            telemetry.update();
            sleep(1);
        }

    }



}
