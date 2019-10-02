package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name ="Charlie's Teleop Simple Tank Drive")
public class SampleThis extends OpMode {

    //Initializing the dc motor objects:
    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;

    @Override
    public void init(){

        //Hardware mapping the motors:
        flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        brMotor = hardwareMap.dcMotor.get("backRightMotor");

    }



    @Override public void loop(){
        //If else statements for the right servo controls (bumpers):

        //Drive Train controls w/ math for diagonal controls
        blMotor.setPower(gamepad1.left_stick_x);
        flMotor.setPower(gamepad1.left_stick_x);
        brMotor.setPower(-gamepad1.right_stick_x);
        frMotor.setPower(-gamepad1.right_stick_x);



    }

}