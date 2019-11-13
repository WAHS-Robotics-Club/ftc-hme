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
        driveTrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);

        sleep(1);
        telemetry.update();

        waitForStart();

        sleep(1);
        telemetry.update();
        sleep(1);
        driveTrain.targetHeading = 90;
        //THIS FREAKING DATA BETtER EXIST
        sleep(1);
        telemetry.update();
        sleep(1);

        while(!driveTrain.isCorrectHeading(gyro.getHeading())){
            telemetry.update();
            driveTrain.turnToHeading(gyro.getHeading());
            telemetry.update();
            sleep(1);
        }
    }
}