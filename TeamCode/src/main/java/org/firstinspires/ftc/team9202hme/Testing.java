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

@TeleOp(name ="Single Driver - BenTest")
public class Testing extends OpMode {
    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor spool;
    DcMotor door;
    DcMotor carousel;

    double spoolSpeed = 1;
    double doorSpeed = 0.2;
    double carouselSpeed = 1;

    boolean lastAInput = false;
    boolean speedToggle = false;


    @Override
    public void init(){
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");
        spool = hardwareMap.dcMotor.get("spoolMotor");
        door = hardwareMap.dcMotor.get("grab");
        carousel = hardwareMap.dcMotor.get("carouselSpinner");
    }

    public double DZone(double rawInput, double deadzone){
        double output = rawInput;
        if(-deadzone < rawInput && rawInput < deadzone){
            output = 0;
        }
        return output;
    }
    public double DZone(double rawInput){
        return DZone(rawInput, 0.01);
    }


    //Loop process:
    @Override
    public void loop(){
        double s = 1;
        if(speedToggle){
            s = 0.5;
        }
        fl.setPower(s*DZone(-gamepad1.left_stick_y) + s*DZone(gamepad1.left_stick_x) + s*DZone(gamepad1.right_stick_x));
        bl.setPower(s*DZone(-gamepad1.left_stick_y) + s*DZone(-gamepad1.left_stick_x) + s*DZone(gamepad1.right_stick_x));
        fr.setPower(s*DZone(gamepad1.left_stick_y) + s*DZone(gamepad1.left_stick_x) + s*DZone(gamepad1.right_stick_x));
        br.setPower(s*DZone(gamepad1.left_stick_y) + s*DZone(-gamepad1.left_stick_x) + s*DZone(gamepad1.right_stick_x));

        spool.setPower(DZone(gamepad2.left_stick_y)*spoolSpeed);

        carousel.setPower(DZone(gamepad2.right_stick_y)*carouselSpeed);

        double doorPower = doorSpeed * DZone(gamepad2.right_trigger) + doorSpeed * DZone(-gamepad2.left_trigger);
        door.setPower(doorPower);

        if(gamepad1.a){
            lastAInput = true;
        }
        else{
            if(lastAInput){
                speedToggle = !speedToggle;
            }
            lastAInput = false;

        }

    }
}