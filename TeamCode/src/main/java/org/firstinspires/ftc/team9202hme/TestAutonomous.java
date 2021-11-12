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
public class TestAutonomous extends OpMode {
    //Local DcMotor variables:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;
    DcMotor spool;

    //Local CRServo and Servo variables:
    CRServo carousel;

    //Local misc variables:
    boolean[] action = new boolean[50];

    //Initiation process:
    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl= hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.crservo.get("carouselSpinner");

        for(int i = 0; i < 50; i++){
            action[i] = false;
        }

        action[0] = true;
    }

    //Loop process:
    @Override
    public void loop(){
        if(action[0] == true){
            fl.setPower(1);
            bl.setPower(1);
            fr.setPower(1);
            br.setPower(1);
        }
    }
}