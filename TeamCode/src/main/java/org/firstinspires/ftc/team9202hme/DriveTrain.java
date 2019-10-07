package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveTrain extends MainTeleOp {
    //Initializing the dc motor objects:

    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;


    public DriveTrain initDriveTrain() {
        //Hardware mapping the motors:
        DriveTrain driveTrain = new DriveTrain();

        driveTrain.flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        driveTrain.frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        driveTrain.blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        driveTrain.brMotor = hardwareMap.dcMotor.get("backRightMotor");

        return driveTrain;
    }
}
