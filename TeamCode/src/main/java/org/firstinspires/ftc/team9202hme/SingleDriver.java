package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@TeleOp(name ="Single Driver TeleOp")
public class SingleDriver extends OpMode {
    DcMotor spoolMotor;

    //Local Servo & CRServo variables:
    CRServo carouselSpinner;

    //Classes:
    DriveTrain driveTrain;

    //Initiation process:
    @Override
    public void init(){
        //Instantiation of objects:
        driveTrain = new DriveTrain();

        spoolMotor = hardwareMap.dcMotor.get("spoolMotor");
        carouselSpinner = hardwareMap.crservo.get("carouselSpinner");
    }

    //Loop process:
    @Override
    public void loop(){
        //Drive train with 0.01 deadspace:
        driveTrain.Drive();

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