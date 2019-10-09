package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name ="motor test charlie")
public class testing extends OpMode {
    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;


    @Override
    public void init(){
        //Hardware mapping the servos:
        flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        brMotor = hardwareMap.dcMotor.get("backRightMotor");
    }

    /*
    leftFront = 2
    leftBack = 1
    rightFront = 3
    rightBack = 0
     */

    @Override public void loop(){
        flMotor.setPower(1);
        frMotor.setPower(-1);
        blMotor.setPower(1);
        brMotor.setPower(-1);


    }

}