package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Elijah's robot test")
public class Test extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    Servo leftServo;
    Servo rightServo;

    @Override
    public void init(){
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("frontLeftMotor");

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
    }

    @Override
    public void loop() {
        if(Math.abs(gamepad1.left_stick_y) >= 0.01){
            frontLeftMotor.setPower(gamepad1.left_stick_y);
            backLeftMotor.setPower(gamepad1.left_stick_y);
        }
        if(Math.abs(gamepad1.right_stick_y) >= 0.01){
            frontRightMotor.setPower(-gamepad1.right_stick_y);
            backRightMotor.setPower(-gamepad1.right_stick_y);
        }

        if(gamepad2.left_bumper){
            leftServo.setPosition(0);
        }
        else{
            leftServo.setPosition(1);
        }
        if(gamepad2.right_bumper){
            rightServo.setPosition(1);
        }
        else{
            rightServo.setPosition(0);
        }
    }
}