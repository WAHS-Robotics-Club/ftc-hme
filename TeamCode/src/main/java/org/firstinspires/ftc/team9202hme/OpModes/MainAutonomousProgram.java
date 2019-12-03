package org.firstinspires.ftc.team9202hme.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.Objects.BananaFruit;
import org.firstinspires.ftc.team9202hme.Objects.DriveTrain;
import org.firstinspires.ftc.team9202hme.Objects.Grabber;
import org.firstinspires.ftc.team9202hme.Objects.Misc;

@Autonomous(name ="Main Autonomous - Elijah")
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

        //Sets the height to a safe height
        grabber.setHeightTo(telemetry, 3000);

        //ONLY MODIFY STUFF AFTER THIS
        //The encoders on everything except the spool motor reset each time you run it
        //Don't set target headings to anything within .5 of 180* either way
        //Good luck

        //Going Forwards
        driveTrain.moveForwardsBy(telemetry, 24);

        //Turning
        driveTrain.turnToHeading(gyro, telemetry, 179);


    }
}