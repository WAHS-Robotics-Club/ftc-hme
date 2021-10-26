package org.firstinspires.ftc.team9202hme;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

public class Misc{
    //Local DcMotor variables:
    DcMotor spool;

    //Local CRServo and Servo variables:
    CRServo carousel;

    //Misc constructor method with hardwaremapping:
    public Misc(){
        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.crservo.get("carouselSpinner");
    }


    public void carouselSpin(){
        if(gamepad1.right_bumper == true){
            carousel.setPower(1);
        }
        else{
            carousel.setPower(0);
        }
    }

    public void spoolSpin(){
        if(gamepad1.left_trigger >= .01 || gamepad1.right_trigger >= .01){
            spool.setPower(gamepad1.left_trigger);
            spool.setPower(-gamepad1.right_trigger);
        }
    }
}