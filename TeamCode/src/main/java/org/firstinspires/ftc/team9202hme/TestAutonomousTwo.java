package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@Autonomous(name ="Test Auto 2")
public class TestAutonomousTwo extends LinearOpMode {

    //Local DcMotor variables:
    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;
    DcMotor spool;

    //Local CRServo and Servo variables:
    CRServoImplEx carousel;

    DriveTrain driveTrain;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        //ONLY MODIFY STUFF AFTER THIS

        //waits 1 second and does a quarter square:
        sleep(1000);
        driveTrain.moveForwardsBy(telemetry, -20);
        driveTrain.turnToHeading(gyro, telemetry,90);

        //waits 1 second and does a quarter square:
        sleep(1000);
        driveTrain.moveForwardsBy(telemetry, -20);
        driveTrain.turnToHeading(gyro, telemetry,180);

        //waits 1 second and does a quarter square:
        sleep(1000);
        driveTrain.moveForwardsBy(telemetry, -20);
        driveTrain.turnToHeading(gyro, telemetry,0);

        //waits 1 second and turns 90 degrees:
        sleep(1000);
        driveTrain.moveForwardsBy(telemetry, -20);
    }
}