package org.firstinspires.ftc.team9202hme.Objects;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.Tool.Toggle;

public class Grabber {
    Servo leftServo;
    Servo rightServo;

    public DcMotor spoolMotor;

    private Toggle toggleGrabber;

    public static Grabber initGrabber(HardwareMap hardwareMap){
        //Creates and hardware maps the grabber element
        Grabber grabber = new Grabber();

        grabber.leftServo = hardwareMap.servo.get("leftServo");
        grabber.rightServo = hardwareMap.servo.get("rightServo");

        grabber.spoolMotor = hardwareMap.dcMotor.get("spoolMotor");
        grabber.toggleGrabber = new Toggle();
        grabber.spoolMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        return grabber;
    }

    public void CheckToggleGrabber(){
        if(toggleGrabber.isToggled()){
            rightServo.setPosition(0.25);
            leftServo.setPosition(0.75);
        }else{
            rightServo.setPosition(1);
            leftServo.setPosition(0);
        }
    }

    public void ManualToggleGrabber(Gamepad gamepad1){
        if(gamepad1.right_bumper) {
            toggleGrabber.toggle();
        }
        CheckToggleGrabber();
    }

    public void ManualSpoolMotor(Gamepad gamepad1){
        //Moves the arm up and down
        if(gamepad1.right_trigger >= 0.1 && gamepad1.left_trigger >= 0.1){
            SpoolMotorControl(0);
        }else if(gamepad1.right_trigger >= 0.1){
            SpoolMotorControl(gamepad1.right_trigger);
        }else if(gamepad1.left_trigger >= 0.1){
            SpoolMotorControl(-gamepad1.left_trigger);
        }else{
            SpoolMotorControl(0);
        }

    }

    public void SpoolMotorControl(float Power) {
        if (spoolMotor.getCurrentPosition() < 12345 && spoolMotor.getCurrentPosition() > -10) {
            if (Math.abs(Power) >= 0.1) {
                spoolMotor.setPower(Power);
            } else {
                spoolMotor.setPower(0);
            }
        }else if(spoolMotor.getCurrentPosition() > -10){
            spoolMotor.setPower(-.2);
        }else{
            spoolMotor.setPower(.2);
        }
    }


}
