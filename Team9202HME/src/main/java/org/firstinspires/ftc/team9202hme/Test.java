package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Elijah's drive and servo test")
public class Test extends OpMode {
    //Initializing the servo objects:
    Servo leftServo;
    Servo rightServo;

    //Initializing the dc motor objects:
    DcMotor spoolMotor;

    //Initializing some miscellaneous variables:
    float spoolControl;

        @Override
        public void init(){
            //Hardware mapping the servos:
            leftServo = hardwareMap.servo.get("leftServo");
            rightServo = hardwareMap.servo.get("rightServo");

            //Hardware mapping the motors:
            spoolMotor = hardwareMap.dcMotor.get("spoolMotor");

        }
        @Override
        public void loop(){
            //If else statements for the right servo controls (bumpers):
            if(gamepad1.right_bumper) {
                rightServo.setPosition(0.75);
            }
            else{
                rightServo.setPosition(0);
            }

            //If else statements for the left servo controls (bumpers);
            if(gamepad1.left_bumper){
                leftServo.setPosition(0.25);
            }
            else{
                leftServo.setPosition(1);
            }

            //Spool controls:
            spoolControl = gamepad1.left_trigger;

            spoolMotor.setPower(spoolControl);
    }
}