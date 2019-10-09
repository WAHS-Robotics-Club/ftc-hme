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


    public static DriveTrain initDriveTrain(HardwareMap hardwareMap) {
        //Hardware mapping the motors:
        DriveTrain driveTrain = new DriveTrain();

        driveTrain.flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        driveTrain.frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        driveTrain.blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        driveTrain.brMotor = hardwareMap.dcMotor.get("backRightMotor");

        return driveTrain;
    }

    public static void manualDrive(DriveTrain driveTrain, Gamepad gamepad1){
        driveTrain.flMotor.setPower(-gamepad1.left_stick_x  + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        driveTrain.frMotor.setPower(-gamepad1.left_stick_x  + gamepad1.left_stick_y + -gamepad1.right_stick_x);
        driveTrain.blMotor.setPower(gamepad1.left_stick_x  + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        driveTrain.brMotor.setPower(gamepad1.left_stick_x  + gamepad1.left_stick_y + -gamepad1.right_stick_x);
    }

}
