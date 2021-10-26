package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@TeleOp(name ="Single Driver TeleOp")
public class SingleDriver extends OpMode {
    //Local DcMotor variables:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;
    DcMotor spoolMotor;

    //Local Servo & CRServo variables:
    CRServo carouselSpinner;

    //Initiation process:
    @Override
    public void init(){
        //Hardwaremap ALL motors:
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        spoolMotor = hardwareMap.dcMotor.get("spoolMotor");

        carouselSpinner = hardwareMap.crservo.get("carouselSpinner");
    }

    //Loop process:
    @Override
    public void loop(){
        //Drive train with 0.01 deadspace:


        //Carousel spinner:
        if(gamepad1.right_bumper == true){
            carouselSpinner.setPower(1);
        }

        //Spool wind and unwind:
        if(gamepad1.left_trigger >= .01 || gamepad1.right_trigger >= .01){
            spoolMotor.setPower(gamepad1.left_trigger);
            spoolMotor.setPower(-gamepad1.right_trigger);
        }
    }
}