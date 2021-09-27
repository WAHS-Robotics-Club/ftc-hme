package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


public class beans {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor spoolMotor;
    //e

    Servo foundationGrabber;
    Servo capstoneDropper;
    Servo leftPaddle;
    Servo rightPaddle;

    public DriveTrain(DcMotor fl, DcMotor bl, DcMotor fr, DcMotor br, DcMotor spool, Servo fGrab, Servo capDrop, Servo lPad, Servo rPad){
        frontLeftMotor = fl;
        backLeftMotor = bl;
        frontRightMotor = fr;
        backRightMotor = br;
        spoolMotor = spool;

        foundationGrabber = fGrab;
        capstoneDropper = capDrop;
        leftPaddle = lPad;
        rightPaddle = rPad;
    }

    public void regularDrive(){
        frontLeftMotor.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backRightMotor.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
    }
}
