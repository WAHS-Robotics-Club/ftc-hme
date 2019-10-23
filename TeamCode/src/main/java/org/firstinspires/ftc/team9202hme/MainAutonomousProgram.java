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

        DriveTrain.setRunMode(driveTrain, DcMotor.RunMode.RUN_TO_POSITION);
        DriveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.addData("FL TARGET POSITION", driveTrain.flMotor.getTargetPosition());
        telemetry.addData("FL MODE", driveTrain.flMotor.getMode());
        telemetry.update();

        waitForStart();
        DriveTrain.goForwardsTo(driveTrain, 420);

        while(driveTrain.flMotor.isBusy() && driveTrain.frMotor.isBusy() && driveTrain.blMotor.isBusy() && driveTrain.brMotor.isBusy()){
            telemetry.update();
            sleep(1);
        }
    }
}