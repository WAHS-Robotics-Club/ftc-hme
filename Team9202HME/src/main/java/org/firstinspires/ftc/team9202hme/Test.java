package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name ="Test TeleOp")
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
                leftServo.setPosition(1);
            }
            else if(gamepad1.left_bumper){
                leftServo.setPosition(1);
            }
            else{
                leftServo.setPosition(0.5);
                rightServo.setPosition(0.5);
            }
    }
}