package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Test Environment (Auto or TeleOp)")
public class TestEnvironment extends OpMode {
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;

    Servo carousel;

    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        carousel = hardwareMap.servo.get("carouselSpinner");
    }

    @Override
    public void loop() {
        
    }
}