package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name ="Test TeleOp")
public class Test extends OpMode {
DcMotor leftFront;
DcMotor rightFront;
DcMotor leftBack;
DcMotor rightBack;
Servo leftServo;
Servo rightServo;

        //hello EJ. I am inevitable. LOTR > Anime

    @Override
    public void init(){

        leftFront = hardwareMap.get(DcMotor.class, "fl");
        rightFront = hardwareMap.get(DcMotor.class, "fr");
        leftBack = hardwareMap.get(DcMotor.class, "bl");
        rightBack = hardwareMap.get(DcMotor.class, "br");

    }
/*
leftFront = 2
leftBack = 1
rightFront = 3
rightBack = 0
 */
    @Override public void loop(){




  double leftFront1 = (0);
  double leftBack1 = (0);
  double rightFront1 = (0);
  double rightBack1 = (0);






    leftBack.setPower(leftBack1);
    leftFront.setPower(leftFront1);
    rightBack.setPower(rightBack1);
    rightFront.setPower(rightFront1);






}

}