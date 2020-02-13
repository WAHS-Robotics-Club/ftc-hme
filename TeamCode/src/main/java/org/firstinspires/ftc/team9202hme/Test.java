package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name ="Elijah's robot test")
public class Test extends OpMode {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

        @Override
        public void init(){
            frontLeft = hardwareMap.dcMotor.get("frontLeftMotor");
            frontRight = hardwareMap.dcMotor.get("frontRightMotor");
            backLeft = hardwareMap.dcMotor.get("backLeftMotor");
            backRight = hardwareMap.dcMotor.get("backRightMotor");
        }
        @Override
        public void loop(){
            if(gamepad1.left_stick_y >= 0.01) {
                frontLeft.setPower(gamepad1.left_stick_y);
                backLeft.setPower(gamepad1.left_stick_y);
            }
            if(gamepad1.right_stick_y >= 0.01) {
                frontRight.setPower(gamepad1.right_stick_y);
                backRight.setPower(gamepad1.right_stick_y);
            }
        }
}