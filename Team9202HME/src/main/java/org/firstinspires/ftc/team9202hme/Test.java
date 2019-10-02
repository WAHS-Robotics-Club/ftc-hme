package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Elijah's drive and servo test")
public class Test extends OpMode {
    //Making the servos:
    Servo leftServo;
    Servo rightServo;

    //Making the dc motors:
    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;

        @Override
        public void init(){
            //Hardware mapping the servos:
            leftServo = hardwareMap.servo.get("leftServo");
            rightServo = hardwareMap.servo.get("rightServo");

            //Hardware mapping the motors:
            flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
            frMotor = hardwareMap.dcMotor.get("frontRightMotor");
            blMotor = hardwareMap.dcMotor.get("backLeftMotor");
            brMotor = hardwareMap.dcMotor.get("backRightMotor");
        }
        @Override
        public void loop(){
            //If else statements for the right servo controls (bumpers):
            if(gamepad1.right_bumper) {
                rightServo.setPosition(1);
            }
            else{
                rightServo.setPosition(0.5);
            }

            //If else statements for the left servo controls (bumpers);
            if(gamepad1.left_bumper){
                leftServo.setPosition(-0);
            }
            else{
                leftServo.setPosition(0.5);
            }
    }
}