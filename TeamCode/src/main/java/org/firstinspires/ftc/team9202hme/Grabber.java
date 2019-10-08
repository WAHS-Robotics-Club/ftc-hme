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

    public static void ToggleGrabber(Grabber grabber, Gamepad gamepad1){

        if(gamepad1.right_bumper){
            grabber.toggleGrabber.toggle();
        }

        if(grabber.toggleGrabber.isToggled()){
            grabber.rightServo.setPosition(0.25);
            grabber.leftServo.setPosition(0.75);
        }else{
            grabber.rightServo.setPosition(1);
            grabber.leftServo.setPosition(0);
        }

    }
}
