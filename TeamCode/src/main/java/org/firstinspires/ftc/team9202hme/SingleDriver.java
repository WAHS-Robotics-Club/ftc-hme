package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
YOU ARE ON THE ERIN BRANCH (!) (!) (!) (!) (!) ONLY ERIN SHOULD CODE HERE (!) (!) (!)
*/

@TeleOp(name ="Single Driver TeleOp")
public class SingleDriver extends OpMode {
    //Local DcMotor variables:
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    //Initiation process:
    @Override
    public void init(){
        //Hardwaremap ALL motors:
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
    }

    //Loop process:
    @Override
    public void loop(){
        frontLeftMotor.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x);
        backRightMotor.setPower(-gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x);
    }
}