package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name ="Main TeleOp - Charlie")
public class MainTeleOp extends OpMode {
    //Initializing the servo objects:

    Grabber grabber;
    DriveTrain driveTrain;


    @Override
    public void init(){
        //Hardware mapping the servos:
        grabber = Grabber.initGrabber(hardwareMap);
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
    }

    /*
    leftFront = 2
    leftBack = 1
    rightFront = 3
    rightBack = 0
     */

    @Override public void loop(){
        //Drive Train manual control system
        DriveTrain.manualDrive(driveTrain, gamepad1);
        DriveTrain.checkToggleSpeed(driveTrain, gamepad1);
        DriveTrain.logTelemetry(telemetry, driveTrain);

        //Grabber System (Servos)
        Grabber.ManualToggleGrabber(grabber, gamepad1);

        //Spool controls
        Grabber.ManualSpoolMotor(grabber, gamepad1);
    }

}