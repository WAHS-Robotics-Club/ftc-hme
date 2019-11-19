package org.firstinspires.ftc.team9202hme.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.Objects.DriveTrain;
import org.firstinspires.ftc.team9202hme.Objects.Grabber;


@TeleOp(name ="Main TeleOp - Charlie")
public class MainTeleOp extends OpMode {
    //Initializing the servo objects:

    Grabber grabber;
    DriveTrain driveTrain;
    Servo foundationGrabber;


    @Override
    public void init(){
        //Hardware mapping the servos:
        grabber = Grabber.initGrabber(hardwareMap);
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
        foundationGrabber = hardwareMap.servo.get("foundationServo");
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

        if(gamepad1.left_bumper){
            foundationGrabber.setPosition(180);
        }else{
            foundationGrabber.setPosition(0);
        }


    }

}