package org.firstinspires.ftc.team9202hme.Objects;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.Tool.Toggle;

public class Misc {
    Servo foundationGrabber;
    Toggle toggleFoundationGrabber;

    public static Misc initMiscellaneous(HardwareMap hardwareMap){
        Misc misc = new Misc();
        misc.foundationGrabber = hardwareMap.servo.get("foundationServo");
        misc.toggleFoundationGrabber = new Toggle();

        return misc;
    }

    public void checkTogglePosition(Gamepad gamepad){
        if(gamepad.a){
            toggleFoundationGrabber.toggle();
        }
    }

    public void checkTogglePosition(Gamepad gamepad, boolean isDualDriver){
        if(gamepad.a){
            toggleFoundationGrabber.toggle();
        }
    }

    public void useMiscLoop(){
        useFoundationGrabber();
    }

    public void useFoundationGrabber(){
        if(toggleFoundationGrabber.isToggled()){
            foundationGrabber.setPosition(0);
        }else{
            foundationGrabber.setPosition(180);
        }
    }


}
