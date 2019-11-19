//Importing FTC packages, classes, and methods:
package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

@TeleOp(name ="Elijah's robot test")
public class Test extends OpMode {
    //Initializing the drive train dc motor objects:
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    //Initializing the grabbing servo objects:
    Servo leftServo;
    Servo rightServo;

    //Initializing the foundation servo;
    Servo foundationServo;

    //Initializing the spool dc motor:
    DcMotor spoolMotor;

    @Override
    public void init() {
        //Hardware mapping the drive motors:
        frontLeft = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRight = hardwareMap.dcMotor.get("frontRightMotor");
        backLeft = hardwareMap.dcMotor.get("backLeftMotor");
        backRight = hardwareMap.dcMotor.get("backRightMotor");

        //Hardware mapping the servos:
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        //Hardware mapping the spool motor:
        spoolMotor = hardwareMap.dcMotor.get("spoolMotor");

        //Hardware mapping the foundation servo
        foundationServo = hardwareMap.servo.get("foundationServo");

    }

    @Override
    public void loop() {

        //Mecanum drive train code:
        //Won't move unless code detects movement on either stick (reduces drift):
        if (Math.abs(gamepad1.left_stick_x) >= 0.1 || Math.abs(gamepad1.left_stick_y) >= 0.1 || Math.abs(gamepad1.right_stick_x) >= 0.1) {
            frontLeft.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            frontRight.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
            backLeft.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            backRight.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        }
        else{ //Stops all drive train motors through the diveStop method:
            driveStop();
        }

            //Arm servo controls (right and left bumpers):
            //Closes right servo:
            if(gamepad1.right_bumper) {
                rightServo.setPosition(0.75);
            }
            else{ //Opens right servo:
                rightServo.setPosition(0.0);
            }

            //Closes left servo:
            if(gamepad1.left_bumper){
                leftServo.setPosition(0.25);
            } //Opens left servo:
            else{
                leftServo.setPosition(1.0);
            }


            //Spool motor code (left and right triggers):
            //Cancels out left and right triggers if pushed at the same time:
            if(gamepad1.left_trigger >= .1 && gamepad1.right_trigger >= .1){
                spoolStop();
            } //Lifts up (or down dependant on the spooling):
            else if(gamepad1.left_trigger >= .1){
                spoolMotor.setPower(gamepad1.left_trigger);
            } //Pushes down (or up dependant on the spooling):
            else if(gamepad1.right_trigger >= .1){
                spoolMotor.setPower(-gamepad1.right_trigger);
            }
            else{ //Stops the spool motor through the spoolStop method:
                spoolStop();
            }

            //Foundation servo code (a button):
            //Pushes down:
            if(gamepad1.a){
                foundationServo.setPosition(-1.0);
            } //Lifts up:
            else{
                foundationServo.setPosition(1.0);
            }
    }
    public void driveStop(){
        //Immediate drive motor stop code:
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }
    public void spoolStop(){
        //Immediate spool motor stop code:
        spoolMotor.setPower(0.0);
    }
}