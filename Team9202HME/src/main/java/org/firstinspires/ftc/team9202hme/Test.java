package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name ="Test TeleOp")
public class Test extends OpMode {
DcMotor leftFront;
DcMotor rightFront;
DcMotor leftBack;
DcMotor rightBack;
Servo leftServo;
Servo rightServo;



    @Override
    public void init(){

        leftFront = hardwareMap.get(DcMotor.class, "fl");
        rightFront = hardwareMap.get(DcMotor.class, "fr");
        leftBack = hardwareMap.get(DcMotor.class, "bl");
        rightBack = hardwareMap.get(DcMotor.class, "br");

        leftServo = hardwareMap.get(Servo.class, "ls");
        rightServo = hardwareMap.get(Servo.class, "rs");

    }
/*
leftFront = 2
leftBack = 1
rightFront = 3
rightBack = 0
 */
    @Override public void loop(){


  double leftFront1 = (-gamepad1.left_stick_x  + -gamepad1.left_stick_y);
  double leftBack1 = (gamepad1.left_stick_x  + -gamepad1.left_stick_y);
  double rightFront1 = (-gamepad1.left_stick_x  + gamepad1.left_stick_y);
  double rightBack1 = (gamepad1.left_stick_x  + gamepad1.left_stick_y);






    leftBack.setPower(leftBack1);
    leftFront.setPower(leftFront1);
    rightBack.setPower(rightBack1);
    rightFront.setPower(rightFront1);


    leftServo.setPosition(0);
    rightServo.setPosition(0);





}

}