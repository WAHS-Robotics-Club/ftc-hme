package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name ="Main TeleOp - Charlie")
public class MainTeleOp extends OpMode {
    //Initializing the servo objects:

    Grabber grabber;
    DriveTrain driveTrain;


    @Override
    public void init(){
        //Hardware mapping the servos:
        Grabber grabber = Grabber.initGrabber(hardwareMap);
        DriveTrain driveTrain = DriveTrain.initDriveTrain(hardwareMap);

    }
    /*
    leftFront = 2
    leftBack = 1
    rightFront = 3
    rightBack = 0
     */
    @Override public void loop(){
        //If else statements for the right servo controls (bumpers):
        if(gamepad1.right_bumper) {
            grabber.rightServo.setPosition(0.25);
        }
        else{
            grabber.rightServo.setPosition(1);
        }

        //If else statements for the left servo controls (bumpers);
        if(gamepad1.left_bumper){
            grabber.leftServo.setPosition(0.75);
        }
        else{
            grabber.leftServo.setPosition(0);
        }

        //Drive Train controls w/ math for diagonal controls

        DriveTrain.manualDrive(driveTrain, gamepad1);

        //Spool controls
        //Left Trigger and Right trigger move opposite directions
        //Both triggers cancel each other out
        if(gamepad1.left_trigger >= .1 && gamepad1.right_trigger >= .1){
            grabber.spoolMotor.setPower(0);
        }else if(gamepad1.left_trigger >= .1){
            grabber.spoolMotor.setPower(gamepad1.right_trigger);
        }else if(gamepad1.right_trigger >= .1){
            grabber.spoolMotor.setPower(-gamepad1.left_trigger);
        }else{
            grabber.spoolMotor.setPower(0);
        }


    }

}