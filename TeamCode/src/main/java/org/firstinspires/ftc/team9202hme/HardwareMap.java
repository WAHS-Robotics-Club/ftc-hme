package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class HardwareMap {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    public HardwareMap(DcMotor fl, DcMotor bl, DcMotor fr, DcMotor br){
        frontLeftMotor = fl;
        backLeftMotor = bl;
        frontRightMotor = fr;
        backRightMotor = br;
    }

    public motorHardware(){
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");

        return frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    }
}
