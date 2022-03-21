package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name ="Ben Auto")
public class AutonomousTesting extends LinearOpMode {

    //DriveTrain DcMotors:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;

    //Appendage DcMotors:
    DcMotor spool;
    DcMotor grab;
    DcMotor carousel;

    @Override
    public void runOpMode() throws InterruptedException {
        //INIT PHASE BUTTON PRESSED
        //HardwareMap DcMotors:
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        grab = hardwareMap.dcMotor.get("grab");
        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.dcMotor.get("carouselSpinner");

        //PLAY PHASE BUTTON PRESSED
        //Wait for the button and subsequently wait 1/4 secs to start the program:
        waitForStart();
        sleep(250);

        //ONLY MODIFY STUFF AFTER THIS

        carousel.setPower(0.5);
        sleep(5000);
        carousel.setPower(0);

    }
}
