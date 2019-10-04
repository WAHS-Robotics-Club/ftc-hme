package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="E J's drive and servo test")
public class Test extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    /*
    //Initializing the servo objects:
    Servo leftServo;
    Servo rightServo;

    //Initializing the dc motor objects:
    DcMotor spoolMotor;
    */
        @Override
        public void init(){
            frontLeft = hardwareMap.dcMotor.get("frontLeftMotor");
            frontRight = hardwareMap.dcMotor.get("frontRightMotor");
            backLeft = hardwareMap.dcMotor.get("backLeftMotor");
            backRight = hardwareMap.dcMotor.get("backRightMotor");
            /*
            //Hardware mapping the servos:
            leftServo = hardwareMap.servo.get("leftServo");
            rightServo = hardwareMap.servo.get("rightServo");

            //Hardware mapping the motors:
            spoolMotor = hardwareMap.dcMotor.get("spoolMotor");
            */

        }
        @Override
        public void loop(){
            frontLeft.setPower(gamepad1.left_stick_y);
            frontRight.setPower(gamepad1.right_stick_y);

            backLeft.setPower(gamepad1.left_stick_y);
            backRight.setPower(gamepad1.right_stick_y);
            /*
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

            //Cancels out left and right triggers if pushed at the same time:
            if(gamepad1.left_trigger >= .1 && gamepad1.right_trigger >= .1){
                spoolMotor.setPower(0);
            }

            //Lifts up:
            if(gamepad1.left_trigger >= .1){
                spoolMotor.setPower(gamepad1.right_trigger);
            }

            //Pushes down:
            if(gamepad1.right_trigger >= .1){
                spoolMotor.setPower(-gamepad1.left_trigger);
            }
            */
    }
}