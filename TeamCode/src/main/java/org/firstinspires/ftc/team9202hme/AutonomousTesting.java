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

        driveTrain.setPower(0.6);
        driveTrain.moveForwards(35);
        driveTrain.turnToHeading(60);
        Thread.sleep(500);
        driveTrain.turnToHeading(-90);
        Thread.sleep(500);
        driveTrain.turnToHeading(200);

        //grab.setPower(-0.4);
        //Thread.sleep(300);
        //grab.setPower(0);

        //driveTrain.turnToHeading(-90);
        //driveTrain.moveForwards(30);




    }
}
