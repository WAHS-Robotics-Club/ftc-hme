package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name ="Ben Auto")
public class AutonomousTesting extends LinearOpMode {

    //Appendage DcMotors:
    DcMotor spool;
    DcMotor grab;
    DcMotor carousel;

    DriveTrain driveTrain;

    int inches = 12;

    @Override
    public void runOpMode() throws InterruptedException {
        //INIT PHASE BUTTON PRESSED
        driveTrain = new DriveTrain(hardwareMap, telemetry);

        grab = hardwareMap.dcMotor.get("grab");
        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.dcMotor.get("carouselSpinner");

        driveTrain.initTelemetry();

        waitForStart();
        sleep(250);

        driveTrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        driveTrain.setPower(0.8);
        driveTrain.moveForwards(inches);

    }
}
