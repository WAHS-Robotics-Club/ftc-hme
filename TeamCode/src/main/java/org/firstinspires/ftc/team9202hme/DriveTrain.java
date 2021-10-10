package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class DriveTrain {
    //Local DcMotor variables:
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    //Constructor method that sets Testing local variables equal to DriveTrain local variables:
    public DriveTrain(DcMotor fl, DcMotor bl, DcMotor fr, DcMotor br){
        frontLeftMotor = fl;
        backLeftMotor = bl;
        frontRightMotor = fr;
        backRightMotor = br;
    }

    //regDrive method allows gamepad1 to control the drivetrain at full speed:
    public void regDrive(){
        frontLeftMotor.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x);
        backRightMotor.setPower(-gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x);

        /*
        OLD DRIVE TRAIN:
        frontLeftMotor.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backRightMotor.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        */
    }

    //regDrive method allows gamepad1 to control the drivetrain at 1/3 speed:
    public void slowDrive(){
        frontLeftMotor.setPower((gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x)/3);
        backLeftMotor.setPower((gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x)/3);
        frontRightMotor.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x)/3);
        backRightMotor.setPower((-gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x)/3);
    }
}