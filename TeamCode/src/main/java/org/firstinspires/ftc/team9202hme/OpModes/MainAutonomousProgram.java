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


        //ONLY MODIFY STUFF AFTER THIS


        //Sets the height to a safe height
        grabber.setHeightTo(telemetry, 0);

        //The encoders on everything except the spool motor reset each time you run it
        //Don't set target headings to anything within .5 of 180* either way

        /*READ THIS IF YOU LOOK AT THIS CODE:
            So, this will technically get the 21 points we want, but there are a few issues.
            We have changes the strategy a LITTLE bit, but that means I have to make changes.
            This works for what we need for now, however. When I make the changes, I will
            delete this passage. ~Elijah Berlin Witt, aka MutantPrince633, aka Mutant Prince,
            aka MutntPrnce, aka EliBerW, aka Soul, aka Chief-Man, aka Hokage Boomer
         */

        //Setting servos and motors to the correct position
        grabber.leftServo.setPosition(0);
        grabber.rightServo.setPosition(1);
        misc.foundationGrabber.setPosition(180);
        grabber.setHeightTo(telemetry, 0);

        //Going to pick up skystone
        driveTrain.moveForwardsBy(telemetry, 24);

        //Picking up skystone
        grabber.leftServo.setPosition(0.75);
        grabber.rightServo.setPosition(0.25);
        sleep(1000);

        //Moving back one tile
        driveTrain.moveForwardsBy(telemetry, -4);

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