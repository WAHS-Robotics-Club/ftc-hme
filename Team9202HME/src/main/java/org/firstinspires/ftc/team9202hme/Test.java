package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Servo test for Elijah")
public class Test extends OpMode {
    Servo leftServo;
    Servo rightServo;

        @Override
        public void init(){
            leftServo = hardwareMap.servo.get("leftServo");
            rightServo = hardwareMap.servo.get("rightServo");
        }
        @Override public void loop(){

            if(gamepad1.right_bumper) {
                rightServo.setPosition(1);
            }
            else{
                rightServo.setPosition(0.5);
            }

            if(gamepad1.left_bumper){
                leftServo.setPosition(0);
            }
            else{
                leftServo.setPosition(0.5);
            }


    }
}