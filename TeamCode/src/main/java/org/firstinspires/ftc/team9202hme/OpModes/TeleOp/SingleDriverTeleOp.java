package org.firstinspires.ftc.team9202hme.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name ="Single Driver TeleOp - HME", group = "TeleOp")
public class SingleDriverTeleOp extends OpMode {
    //Initializing the servo objects:

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

    @Override public void loop() {
        //Drive Train manual control system
        flMotor.setPower(-gamepad1.left_stick_y);
        frMotor.setPower(gamepad1.right_stick_y);
        blMotor.setPower(-gamepad1.left_stick_y);
        brMotor.setPower(gamepad1.right_stick_y);
    }

}