package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareMap {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor spoolMotor;

    Servo foundationGrabber;
    Servo capstoneDropper;
    Servo leftPaddle;
    Servo rightPaddle;

    public HardwareMap(DcMotor fl, DcMotor bl, DcMotor fr, DcMotor br, DcMotor spool, Servo fGrab, Servo capDrop, Servo lPad, Servo rPad){
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

    public void motorHardware(){
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        spoolMotor = harwareMap.dcMotor.get("spoolMotor");
    }

    public void servoHardware(){
        foundationGrabber = hardwareMap.servo.get("foundationGrabberServo");
        capstoneDropper = hardwareMap.servo.get("capstoneDropperServo");
        leftPaddle = hardwareMap.servo.get("leftPaddleServo");
        rightPaddle = hardwareMap.servo.get("rightPaddleServo");
    }
}
