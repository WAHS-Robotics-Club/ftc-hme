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

        telemetry.addData("FL TARGET POSITION", driveTrain.flMotor.getTargetPosition());
        telemetry.addData("FL MODE", driveTrain.flMotor.getMode());
        //telemetry.addData("Heading", )
        telemetry.update();
        driveTrain.resetEncoders();

        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        driveTrain.resetCooldown();
        waitForStart();

        //Going Forwards
        driveTrain.goForwardsTo(24);
        driveTrain.setBasePower(.8);
        sleep(1);
        while(driveTrain.isBusy()){
            telemetry.update();
            sleep(1);
        }

        //Turning
        driveTrain.targetHeading = 90;
        driveTrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(!driveTrain.isCorrectHeading(gyro.getHeading())){
            telemetry.update();
            driveTrain.turnToHeading(gyro.getHeading());
            sleep(1);
        }

        //Going Forwards
        driveTrain.goForwardsTo(24);
        driveTrain.setBasePower(.8);
        sleep(1);
        while(driveTrain.isBusy()){
            telemetry.update();
            sleep(1);
        }

        //Turning
        driveTrain.targetHeading = 0;
        driveTrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(!driveTrain.isCorrectHeading(gyro.getHeading())){
            telemetry.update();
            driveTrain.turnToHeading(gyro.getHeading());
            sleep(1);
        }

        //Going Forwards
        driveTrain.goForwardsTo(24);
        driveTrain.setBasePower(.8);
        sleep(1);
        while(driveTrain.isBusy()){
            telemetry.update();
            sleep(1);
        }

        //Turning
        driveTrain.targetHeading = -90;
        driveTrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(!driveTrain.isCorrectHeading(gyro.getHeading())){
            telemetry.update();
            driveTrain.turnToHeading(gyro.getHeading());
            sleep(1);
        }

    }
}