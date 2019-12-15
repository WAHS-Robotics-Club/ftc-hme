package org.firstinspires.ftc.team9202hme.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.Objects.BananaFruit;
import org.firstinspires.ftc.team9202hme.Objects.DriveTrain;
import org.firstinspires.ftc.team9202hme.Objects.Grabber;
import org.firstinspires.ftc.team9202hme.Objects.Misc;

@Autonomous(name ="BlueFoundationSide - HME Auto")
public class BlueFoundationSideAuto extends LinearOpMode {

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


        //ONLY MODIFY STUFF AFTER THIS


        //Sets the height to a safe height
        grabber.setHeightTo(telemetry, 0);

        //The encoders on everything except the spool motor reset each time you run it
        //Don't set target headings to anything within .5 of 180* either way

        //THIS IS THE AUTONOMOUS CODE FOR BLUE FOUNDATION SIDE

        //Setting servos and motors to the correct position
        grabber.leftServo.setPosition(0);
        grabber.rightServo.setPosition(1);
        misc.foundationGrabber.setPosition(180);
        grabber.setHeightTo(telemetry, 0);

        //Going to pick up skystone
        driveTrain.moveForwardsBy(telemetry, 26);

        //Picking up skystone
        grabber.leftServo.setPosition(0.9);
        grabber.rightServo.setPosition(0.1);
        sleep(1000);

        //Moving back one tile
        driveTrain.moveForwardsBy(telemetry, -5);

        //Turning towards the building side
        driveTrain.turnToHeading(gyro, telemetry, 90);

        //Moving to the build side
        driveTrain.moveForwardsBy(telemetry, 64);

        //Turning towards the foundation
        driveTrain.turnToHeading(gyro, telemetry, 0);

        //Rising the drawer slides
        grabber.setHeightTo(telemetry, 3500);

        //Moving closer to the foundation
        driveTrain.moveForwardsBy(telemetry, 9);

        //Dropping the skystone
        grabber.leftServo.setPosition(0);
        grabber.rightServo.setPosition(1);
        driveTrain.moveForwardsBy(telemetry, -3);

        //Turning around to face the build site and grabbing the foundation
        driveTrain.moveForwardsBy(telemetry, -1);
        driveTrain.turnToHeading(gyro, telemetry, 179);
        misc.foundationGrabber.setPosition(0);
        sleep(3000);

        //Driving to the build site
        driveTrain.moveForwardsBy(telemetry, 24);
        misc.foundationGrabber.setPosition(180);

        //Turning to park under the bridge and lowering the drawer slides
        driveTrain.turnToHeading(gyro, telemetry, -90);
        grabber.setHeightTo(telemetry, 0);

        //Driving to park under the bridge
        driveTrain.moveForwardsBy(telemetry, 40);

        //DONE
    }
}