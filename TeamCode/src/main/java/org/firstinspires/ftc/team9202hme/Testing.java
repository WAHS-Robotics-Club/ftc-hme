package org.firstinspires.ftc.team9202hme;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name ="Single Driver - Testing")
public class Testing extends OpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor spool;
    DcMotor door;
    DcMotor carousel;

    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");
        spool = hardwareMap.dcMotor.get("spool");
        door = hardwareMap.dcMotor.get("door");
        carousel = hardwareMap.dcMotor.get("carousel");
    }

    //Loop process:
    @Override
    public void loop(){
        
    }
}