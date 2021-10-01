package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ElijahCode - Simple Drive")
public class SingleDriverTeleOp extends OpMode{
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");
    }

    @Override
    public void loop(){
        fl.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        fr.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        bl.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        br.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
    }
}
