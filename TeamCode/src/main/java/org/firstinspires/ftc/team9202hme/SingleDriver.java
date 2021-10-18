package org.firstinspires.ftc.team9202hme;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name ="Single Driver TeleOp")
public class SingleDriver extends OpMode {
    //Local misc variables:
    boolean driveSwitch = false;
    boolean switchProtection = false;

    //Local DcMotor variables:
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    //Instantiation of objects:
    //HardwareMap hardware = new HardwareMap(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    //DriveTrain drive = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

    //Initiation process:
    @Override
    public void init(){
        //Hardwaremap ALL motors:
        //hardware.motorHardware();
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
    }

    //Loop process:
    @Override
    public void loop(){
        frontLeftMotor.setPower(gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x);
        backLeftMotor.setPower(gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x);
        frontRightMotor.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + -gamepad1.right_stick_x);
        backRightMotor.setPower(-gamepad1.left_stick_y + -gamepad1.left_stick_x + -gamepad1.right_stick_x);
        /*
        //driveSwitch control:
        if(gamepad1.a == true && driveSwitch == false){
            if(switchProtection == false){
                driveSwitch = true;
            }
            switchProtection = true;
        }
        else if(gamepad1.a == true && driveSwitch == true){
            if(switchProtection == false){
                driveSwitch = false;
            }
            switchProtection = true;
        }
        else{
            switchProtection = false;
        }

        //Driving speed control:
        if(driveSwitch == false){
            drive.regDrive();
        }
        else if(driveSwitch == true){
            drive.slowDrive();
        }
         */

        //Hello Shep
    }
}