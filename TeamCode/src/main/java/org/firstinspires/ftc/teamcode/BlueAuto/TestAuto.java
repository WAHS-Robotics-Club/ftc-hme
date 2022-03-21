package org.firstinspires.ftc.teamcode.BlueAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.BananaFruit;
import org.firstinspires.ftc.teamcode.DriveTrain;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@Autonomous(name ="Carousel BLU")
public class TestAuto extends LinearOpMode {
    //DriveTrain DcMotors:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;

    //Appendage DcMotors:
    DcMotor spool;
    DcMotor grab;
    DcMotor carousel;

    double inches = 12;
    double rotations;

    int targetPosition;
    int i = 0;

    boolean isBusy;

    @Override
    public void runOpMode() throws InterruptedException {
        //INIT PHASE BUTTON PRESSED
        //HardwareMap DcMotors:
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");

        //hello
        grab = hardwareMap.dcMotor.get("grab");
        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.dcMotor.get("carouselSpinner");

        telemetry.addData("FL Power: ", fl.getPower());
        telemetry.addData("BL Power: ", bl.getPower());
        telemetry.addData("FR Power", fr.getPower());
        telemetry.addData("BR Power", br.getPower());
        telemetry.update();

        //PLAY PHASE BUTTON PRESSED ||| ONLY MODIFY STUFF AFTER THIS
        //Wait for the button and subsequently wait 1/4 secs to start the program:
        waitForStart();
        sleep(250);

        rotations = inches / (4*Math.PI);
        targetPosition = (int)(rotations*1120);
        fl.setTargetPosition(-targetPosition);
        bl.setTargetPosition(-targetPosition);
        fr.setTargetPosition(targetPosition);
        br.setTargetPosition(targetPosition);

        fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fl.setPower(.8);
        bl.setPower(.8);
        fr.setPower(.8);
        br.setPower(.8);
        Thread.sleep(1);

        if(fl.isBusy() && fr.isBusy() && bl.isBusy() && br.isBusy()){
            isBusy = true;
        }else{
            isBusy = false;
        }

        while(isBusy == true && i < 500){
            telemetry.update();
            i++;
            Thread.sleep(1);
        }
    }
}