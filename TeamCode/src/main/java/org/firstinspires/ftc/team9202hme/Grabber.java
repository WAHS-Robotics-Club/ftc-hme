package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    Servo leftServo;
    Servo rightServo;

    DcMotor spoolMotor;

    private Toggle toggleGrabber;

    public static Grabber initGrabber(HardwareMap hardwareMap){
        //Creates and hardware maps the grabber element

        Grabber grabber = new Grabber();

        grabber.leftServo = hardwareMap.servo.get("leftServo");
        grabber.rightServo = hardwareMap.servo.get("rightServo");

        grabber.spoolMotor = hardwareMap.dcMotor.get("spoolMotor");

        grabber.toggleGrabber = new Toggle();

        return grabber;
    }

    public static void CheckToggleGrabber(Grabber grabber){
        if(grabber.toggleGrabber.isToggled()){
            grabber.rightServo.setPosition(0.25);
            grabber.leftServo.setPosition(0.75);
        }else{
            grabber.rightServo.setPosition(1);
            grabber.leftServo.setPosition(0);
        }
    }
    public static void ManualToggleGrabber(Grabber grabber, Gamepad gamepad1){
        if(gamepad1.right_bumper) {
            grabber.toggleGrabber.toggle();
        }
        CheckToggleGrabber(grabber);
    }

    public static void ManualSpoolMotor(Grabber grabber, Gamepad gamepad1){
        //Moves the arm up and down
        if(gamepad1.right_trigger >= 0.1 && gamepad1.left_trigger >= 0.1){
            SpoolMotorControl(grabber,0);
        }else if(gamepad1.right_trigger >= 0.1){
            SpoolMotorControl(grabber, gamepad1.right_trigger);
        }else if(gamepad1.left_trigger >= 0.1){
            SpoolMotorControl(grabber, -gamepad1.left_trigger);
        }
    }
    public static void SpoolMotorControl(Grabber grabber, float Power){
        grabber.spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if(Math.abs(Power) >= 0.1) {
            grabber.spoolMotor.setPower(Power);
        }else{
            grabber.spoolMotor.setPower(0);
        }
    }
}
