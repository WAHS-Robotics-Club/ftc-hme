package org.firstinspires.ftc.team9202hme;

public class DriveTrain {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor spoolMotor;

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

    public regularDrive(){

    }
}
