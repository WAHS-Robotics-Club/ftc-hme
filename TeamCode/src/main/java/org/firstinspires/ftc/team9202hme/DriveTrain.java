package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class DriveTrain {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    public DriveTrain(DcMotor fl, DcMotor bl, DcMotor fr, DcMotor br){
        frontLeftMotor = fl;
        backLeftMotor = bl;
        frontRightMotor = fr;
        backRightMotor = br;
    }

    public void regDrive(){
        frontLeftMotor.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backRightMotor.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
    }
}