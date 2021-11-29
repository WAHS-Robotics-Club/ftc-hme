package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Test Environment (Auto or TeleOp)")
public class TestEnvironment extends OpMode {
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;

    CRServo carousel;

    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        carousel = hardwareMap.crservo.get("carouselSpinner");
    }

    @Override
    public void loop() {
        if(gamepad1.left_stick_y >= .03 || gamepad1.left_stick_y <= -.03 || gamepad1.left_stick_x >= .03 || gamepad1.left_stick_x <= -.03 || gamepad1.right_stick_x >= .03 || gamepad1.right_stick_x <= -.03){
            fl.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            bl.setPower(-gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x);
            fr.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
            br.setPower(gamepad1.left_stick_y + -gamepad1.left_stick_x + gamepad1.right_stick_x);
        }
        else{
            fl.setPower(0);
            bl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
        }

        if(gamepad1.left_bumper == true){
            carousel.setPower(1);
        }
        else{
            carousel.setPower(0);
        }
    }
}