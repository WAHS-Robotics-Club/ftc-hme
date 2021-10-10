package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name ="Single Driver TeleOp")
public class Testing extends OpMode {
    //Local DcMotor variables:
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    //Instantiation of objects:
    HardwareMap hardware = new HardwareMap(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    DriveTrain drive = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

    //Initiation process:
    @Override
    public void init(){
        hardware.motorHardware();
    }

    //Loop process:
    @Override
    public void loop() {
        drive.regDrive();
    }
}