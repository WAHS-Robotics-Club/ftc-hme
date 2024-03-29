package org.firstinspires.ftc.teamcode.RedAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PwmControl;

import org.firstinspires.ftc.teamcode.BananaFruit;
import org.firstinspires.ftc.teamcode.DriveTrain;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@Autonomous(name ="Carousel RED")
public class CarouselRedAuto extends LinearOpMode {

    //Local DcMotor variables:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;
    DcMotor spool;
    DcMotor grab;

    //Local CRServo and Servo variables:
    DcMotor carousel;

    DriveTrain driveTrain;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
        grab = hardwareMap.dcMotor.get("grab");
        spool = hardwareMap.dcMotor.get("spoolMotor");
        carousel = hardwareMap.dcMotor.get("carouselSpinner");

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        //ONLY MODIFY STUFF AFTER THIS

        //waits .25 seconds, goes backward 24 in, turns carousel for 5 seconds:
        sleep(250);
        driveTrain.moveForwardsBy(telemetry, 17);
        carousel.setPower(.5);
        sleep(5000);
        carousel.setPower(0);
    }
}