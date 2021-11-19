package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@Autonomous(name ="Test Auto")
public class TestAutonomous extends LinearOpMode {

    //Local DcMotor variables:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;
    DcMotor spool;

    //Local CRServo and Servo variables:
    CRServo carousel;


    @Override
    public void runOpMode() throws InterruptedException {
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl= hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.crservo.get("carouselSpinner");

        //Wait for 1 sec after start:
        waitForStart();
        sleep(500);

        //Move forward and raise slides for 3 sec:
        fl.setPower(1);
        bl.setPower(1);
        fr.setPower(-1);
        br.setPower(-1);

        spool.setPower(1);

        sleep(5000);

        //Stop all motors and spin carousel for 2 sec:
        fl.setPower(0);
        bl.setPower(0);
        fr.setPower(0);
        br.setPower(0);

        spool.setPower(0);
        carousel.setPower(1);

        sleep(5000);

        //Stop carousel:
        carousel.setPower(0);
    }
}