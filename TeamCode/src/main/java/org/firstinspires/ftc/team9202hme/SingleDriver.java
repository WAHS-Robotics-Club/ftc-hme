package org.firstinspires.ftc.team9202hme;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PwmControl;
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
    DcMotor spool;
    DcMotor grab;

    //Local CRServo and Servo variables:
    CRServoImplEx carousel;

    //Initiation process:
    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl= hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        grab = hardwareMap.dcMotor.get("grab");
        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = (CRServoImplEx) hardwareMap.crservo.get("carouselSpinner");
        carousel.setPwmRange(PwmControl.PwmRange.defaultRange);
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
        if(gamepad1.left_bumper == true){
            carousel.setPower(1);
        }
        else{
            carousel.setPower(0);
        }

        //SpoolSpin:
        if(gamepad1.left_trigger >= .05 || gamepad1.right_trigger >= .05){
            spool.setPower(gamepad1.left_trigger);
            spool.setPower(-gamepad1.right_trigger);
        }
        else{
            spool.setPower(0);
        }

        //BoxGrabber:
        if(gamepad1.dpad_up == true){
            grab.setPower(.25);
        }
        else if(gamepad1.dpad_down == true){
            grab.setPower(-.25);
        }
        else{
            grab.setPower(0);
        }
    }
}