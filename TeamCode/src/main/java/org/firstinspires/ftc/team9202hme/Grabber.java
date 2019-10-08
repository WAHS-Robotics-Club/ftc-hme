package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    Servo leftServo;
    Servo rightServo;

    DcMotor spoolMotor;

    public static Grabber initGrabber(HardwareMap hardwareMap){
        //Creates and hardware maps the grabber element
        Grabber grabber = new Grabber();

        grabber.leftServo = hardwareMap.servo.get("leftServo");
        grabber.rightServo = hardwareMap.servo.get("rightServo");

        grabber.spoolMotor = hardwareMap.dcMotor.get("spoolMotor");

        return grabber;
    }

    public static void ToggleGrabber(Gamepad gamepad1){

    }
}
