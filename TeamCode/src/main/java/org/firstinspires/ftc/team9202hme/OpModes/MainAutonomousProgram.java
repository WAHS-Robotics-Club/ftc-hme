package org.firstinspires.ftc.team9202hme.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.Objects.BananaFruit;
import org.firstinspires.ftc.team9202hme.Objects.DriveTrain;
import org.firstinspires.ftc.team9202hme.Objects.Grabber;
import org.firstinspires.ftc.team9202hme.Objects.Misc;

@Autonomous(name ="Main Autonomous - Charlie")
public class MainAutonomousProgram extends LinearOpMode {

    DriveTrain driveTrain;
    Grabber grabber;
    Misc misc;

    @Override
    public void runOpMode() throws InterruptedException {
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
        grabber = Grabber.initGrabber(hardwareMap);
        misc = Misc.initMiscellaneous(hardwareMap);
        int i;

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        //Set the height
        grabber.setHeightTo(telemetry, 6000);

        //Going Forwards
        driveTrain.moveForwardsBy(telemetry, 24);

        //Turning
        driveTrain.turnToHeading(gyro, telemetry, 90);

       //Going forwards
        driveTrain.moveForwardsBy(telemetry, 24);

        //Turning Back
        driveTrain.turnToHeading(gyro, telemetry,-90);

        //Going Back
        driveTrain.moveForwardsBy(telemetry, 24);

        //Turing to face forwards again
        driveTrain.turnToHeading(gyro,telemetry,0);

        //Backing up
        driveTrain.moveForwardsBy(telemetry, -24);
    }
}