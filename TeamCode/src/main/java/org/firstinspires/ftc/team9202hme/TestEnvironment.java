package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Test Environment (Auto or TeleOp)")
public class Test extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    HardwareMap hardware = new HardwareMap(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    DriveTrain drive = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

    @Override
    public void init(){
        hardware.motorHardware();
        drive.regDrive();
    }

    //count goku
    @Override
    public void loop() {
        drive.regDrive();
    }
}