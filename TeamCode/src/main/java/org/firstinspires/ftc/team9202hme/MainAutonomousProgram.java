package org.firstinspires.ftc.team9202hme;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name ="Main Autonomous - Charlie")
public class MainAutonomousProgram extends LinearOpMode {
    DriveTrain driveTrain;
    Grabber grabber;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
        grabber = Grabber.initGrabber(hardwareMap);
        int i;

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();


        sleep(1);
        waitForStart();
        sleep(1);


        //Going Forwards
        i = 0;
        driveTrain.goForwardsTo(24);
        driveTrain.setBasePower(.8);
        sleep(1);
        while(driveTrain.isBusy() && i < 500){
            telemetry.update();
            i++;
            sleep(1);
        }


        sleep(1);
        driveTrain.resetEncoders();
        sleep(1);
        //Turning
        driveTrain.targetHeading = 90;
        driveTrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(!driveTrain.isCorrectHeading(gyro.getHeading())){
            telemetry.update();
            driveTrain.turnToHeading(gyro.getHeading());
            sleep(1);
        }


        sleep(1);
        driveTrain.resetEncoders();
        sleep(1);
        //Going Forwards
        i = 0;
        driveTrain.goForwardsTo(24);
        driveTrain.setBasePower(.8);
        sleep(1);
        while(driveTrain.isBusy() && i < 500){
            telemetry.update();
            i++;
            sleep(1);
        }


    }
}