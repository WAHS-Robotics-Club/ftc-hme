package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name ="Churles's Teleop")
public class Test extends OpMode {
    //Initializing the servo objects:
    Servo leftServo;
    Servo rightServo;

    //Initializing the dc motor objects:
    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;

    DcMotor spoolMotor;


    @Override
    public void init(){

        //Hardware mapping the servos:
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        //Hardware mapping the motors:
        flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        brMotor = hardwareMap.dcMotor.get("backRightMotor");

        spoolMotor = hardwareMap.dcMotor.get("spoolMotor");

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
            rightServo.setPosition(0.25);
        }
        else{
            rightServo.setPosition(1);
        }

        //If else statements for the left servo controls (bumpers);
        if(gamepad1.left_bumper){
            leftServo.setPosition(0.75);
        }
        else{
            leftServo.setPosition(0);
        }

    //Drive Train controls w/ math for diagonal controls
    blMotor.setPower(gamepad1.left_stick_x  + -gamepad1.left_stick_y);
    flMotor.setPower(-gamepad1.left_stick_x  + -gamepad1.left_stick_y);
    brMotor.setPower(gamepad1.left_stick_x  + gamepad1.left_stick_y);
    frMotor.setPower(-gamepad1.left_stick_x  + gamepad1.left_stick_y);

        //Spool controls
        //Left Trigger and Right trigger move opposite directions
        //Both triggers cancel each other out
        if(gamepad1.left_trigger >= .1 && gamepad1.right_trigger >= .1){
            spoolMotor.setPower(0);
        }else if(gamepad1.left_trigger >= .1){
            spoolMotor.setPower(gamepad1.right_trigger);
        }else if(gamepad1.right_trigger >= .1){
            spoolMotor.setPower(-gamepad1.left_trigger);
        }else{
            spoolMotor.setPower(0);
        }


}

}