package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

@TeleOp(name ="Elijah's robot test")
public class Test extends OpMode {
    //Initializing the drive dc motor objects:
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    //Initializing the servo objects:
    Servo leftServo;
    Servo rightServo;

    //Initializing the miscellaneous dc motor objects:
    DcMotor spoolMotor;

        @Override
        public void init(){
            //Hardware mapping the drive motors:
            frontLeft = hardwareMap.dcMotor.get("frontLeft");
            frontRight = hardwareMap.dcMotor.get("frontRight");
            backLeft = hardwareMap.dcMotor.get("backLeft");
            backRight = hardwareMap.dcMotor.get("backRight");

            //Hardware mapping the servos:
            leftServo = hardwareMap.servo.get("leftServo");
            rightServo = hardwareMap.servo.get("rightServo");

            //Hardware mapping the miscellaneous motors:
            spoolMotor = hardwareMap.dcMotor.get("spoolMotor");
        }
        @Override
        public void loop(){
            //Drive code:
            if(Math.abs(gamepad1.left_stick_x)>= 0.1 || Math.abs(gamepad1.left_stick_y)>= 0.1 || Math.abs(gamepad1.right_stick_x)>= 0.1) {
                frontLeft.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
                frontRight.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
                backLeft.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
                backRight.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            }
            else{
                driveStop();
            }

            //Moving forward and backward:
            /*if(Math.abs(gamepad1.left_stick_y) >= 0.1){
                frontLeft.setPower(-gamepad1.left_stick_y);
                frontRight.setPower(gamepad1.left_stick_y);
                backLeft.setPower(-gamepad1.left_stick_y);
                backRight.setPower(gamepad1.left_stick_y);
            }
            else{
                stop();
            }

            //Turning on a fixed point:
            if(gamepad1.right_stick_x >= 0.1){
                frontLeft.setPower(gamepad1.right_stick_x);
                frontRight.setPower(gamepad1.right_stick_x);
                backLeft.setPower(gamepad1.right_stick_x);
                backRight.setPower(gamepad1.right_stick_x);
            }
            else{
                driveStop();
            }

            //Turning on a fixed point opposite direction:
            if(gamepad1.right_stick_x <= -0.1){
                frontLeft.setPower(-gamepad1.right_stick_x);
                frontRight.setPower(-gamepad1.right_stick_x);
                backLeft.setPower(-gamepad1.right_stick_x);
                backRight.setPower(-gamepad1.right_stick_x);
            }
            else{
                driveStop();
            }
             */


            //Arm servo controls (right and left bumpers):
            //Right servo:
            if(gamepad1.right_bumper) {
                rightServo.setPosition(0.75);
            }
            else{
                rightServo.setPosition(0);
            }

            //Left servo
            if(gamepad1.left_bumper){
                leftServo.setPosition(0.25);
            }
            else{
                leftServo.setPosition(1);
            }


            //Spool motor code (left and right triggers):
            //Cancels out left and right triggers if pushed at the same time:
            if(gamepad1.left_trigger >= .1 && gamepad1.right_trigger >= .1){
                spoolMotor.setPower(0);
            } //Lifts up
            else if(gamepad1.left_trigger >= .1){
                spoolMotor.setPower(gamepad1.left_trigger);
            } //Pushes down:
            else if(gamepad1.right_trigger >= .1){
                spoolMotor.setPower(-gamepad1.right_trigger);
            }
            else{
                spoolStop();
            }
    }
    public void driveStop(){
        //Immediate drive stop code:
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
    public void spoolStop(){
        spoolMotor.setPower(0);
    }
}