package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain{
    //Initializing the dc motor objects:

    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;
    Toggle toggleSpeed;


    public static DriveTrain initDriveTrain(HardwareMap hardwareMap) {
        //Hardware mapping the motors:
        DriveTrain driveTrain = new DriveTrain();

        driveTrain.flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        driveTrain.frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        driveTrain.blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        driveTrain.brMotor = hardwareMap.dcMotor.get("backRightMotor");
        driveTrain.toggleSpeed = new Toggle();

        return driveTrain;
    }

    public static void manualDrive(DriveTrain driveTrain, Gamepad gamepad1){
        if(!driveTrain.toggleSpeed.isToggled()) {
            driveTrain.flMotor.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            driveTrain.frMotor.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
            driveTrain.blMotor.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            driveTrain.brMotor.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        }else{
            driveTrain.flMotor.setPower((-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x)/10);
            driveTrain.frMotor.setPower((-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x)/10);
            driveTrain.blMotor.setPower((gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x)/10);
            driveTrain.brMotor.setPower((gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x)/10);
        }
    }

    public static void checkToggleSpeed(DriveTrain driveTrain, Gamepad gamepad1){
        if(gamepad1.left_bumper){
            driveTrain.toggleSpeed.toggle();
        }
    }



}


