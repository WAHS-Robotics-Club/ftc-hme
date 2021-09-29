package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "HME - SingleDriverTeleOp")
public class SingleDriverTeleOp extends OpMode{
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor spool;

    Servo foundationGrabber;
    Servo capstoneDropper;
    Servo leftPaddle;
    Servo rightPaddle;

    @Override
    public void init(){
        HardwareMap initiate = new HardwareMap(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, spool, foundationGrabber, capstoneDropper, leftPaddle, rightPaddle);
        initiate.motorHardware();
        initiate.servoHardware();
    }

    @Override
    public void loop(){
        frontLeftMotor.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        backRightMotor.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
    }
}
