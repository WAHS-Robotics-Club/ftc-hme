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

        telemetry.addData("IsBusy", driveTrain.isBusy());
        driveTrain.logTelemetry(telemetry, driveTrain);
        telemetry.update();
        driveTrain.resetEncoders();
        BananaFruit gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        //Sets the height to a safe height
        grabber.setHeightTo(telemetry, 0);

        //ONLY MODIFY STUFF AFTER THIS
        //The encoders on everything except the spool motor reset each time you run it
        //Don't set target headings to anything within .5 of 180* either way
        //Good luck have fun
        //Hi



        //THIS IS ELIJAH: If I did this all correctly, it should work...at least decently...

        //Going to pick up skystone
        driveTrain.moveForwardsBy(telemetry, 38);
        //Moving back one tile
        driveTrain.moveForwardsBy(telemetry, -23);
        //Turning towards the building side
        driveTrain.turnToHeading(gyro, telemetry, 90);
        //Moving to the build side
        driveTrain.moveForwardsBy(telemetry, 70);
        //Turning towards the foundation
        driveTrain.turnToHeading(gyro, telemetry, 0);
        //Moving to the foundation
        driveTrain.moveForwardsBy(telemetry, 23);
        //Turning around to face the build site
        driveTrain.turnToHeading(gyro, telemetry, 179);
        //Driving to the build site
        driveTrain.moveForwardsBy(telemetry, 38);
        //Turning to park under the bridge
        driveTrain.turnToHeading(gyro, telemetry, 270);
        sleep(1);
        //Driving to park under the bridge
        driveTrain.moveForwardsBy(telemetry, 47);
        //DONE with the drive train code!
    }
}