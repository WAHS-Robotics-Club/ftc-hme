package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@TeleOp(name ="Dual Driver TeleOp")
public class DualDriver extends OpMode {
    //Local DcMotor variables:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;
    DcMotor spool;

    //Local CRServo and Servo variables:
    CRServo carousel;

    //Initiation process:
    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl= hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.crservo.get("carouselSpinner");
    }

    //Loop process:
    @Override
    public void loop(){
        //DriveTrain:
        if(gamepad1.left_stick_y >= .05 || gamepad1.left_stick_y <= -.05 || gamepad1.left_stick_x >= .05 || gamepad1.left_stick_x <= -.05 || gamepad1.right_stick_x >= .05 || gamepad1.right_stick_x <= -.05){
            fl.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            bl.setPower(-gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x);
            fr.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            br.setPower(gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x);
        }
        else{
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }

        //CarouselSpin:
        if(gamepad2.right_bumper == true){
            carousel.setPower(1);
        }
        else{
            carousel.setPower(0);
        }

        //SpoolSpin:
        if(gamepad2.left_trigger >= .05 || gamepad2.right_trigger >= .05){
            spool.setPower(gamepad1.left_trigger);
            spool.setPower(-gamepad1.right_trigger);
        }
        else{
            spool.setPower(0);
        }
    }
}