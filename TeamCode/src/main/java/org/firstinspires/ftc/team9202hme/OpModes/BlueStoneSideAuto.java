package org.firstinspires.ftc.team9202hme.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.Objects.BananaFruit;
import org.firstinspires.ftc.team9202hme.Objects.DriveTrain;
import org.firstinspires.ftc.team9202hme.Objects.Grabber;
import org.firstinspires.ftc.team9202hme.Objects.Misc;

@Autonomous(name ="BlueStoneSide - HME Auto")
public class BlueStoneSideAuto extends LinearOpMode {

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
        grabber.setHeightTo(telemetry, 500);

        //The encoders on everything except the spool motor reset each time you run it


        //THIS IS THE AUTONOMOUS CODE FOR BLUE STONE SIDE

        //Setting servos and motors to the correct position
        grabber.leftServo.setPosition(0);
        grabber.rightServo.setPosition(1);
        misc.foundationGrabber.setPosition(180);
        grabber.setHeightTo(telemetry, 0);

        //Going to pick up skystone
        driveTrain.moveForwardsBy(telemetry, 26);

        //Picking up skystone
        grabber.leftServo.setPosition(0.95);
        grabber.rightServo.setPosition(0.05);
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
        driveTrain.moveForwardsBy(telemetry, -1);

        //Lowering the drawer slides
        grabber.setHeightTo(telemetry, 0);

        //Moving back a tile or so
        driveTrain.moveForwardsBy(telemetry, -20);

        //Turning towards the build site
        driveTrain.turnToHeading(gyro, telemetry, 90);

        //Raising the drawer slides and detaching
        grabber.setHeightTo(telemetry, 2000);
        driveTrain.moveForwardsBy(telemetry, -20);

        //Lowering the drawer slides again
        grabber.setHeightTo(telemetry, 0);

        //Avoiding alliance partner
        driveTrain.turnToHeading(gyro, telemetry, 0);
        driveTrain.moveForwardsBy(telemetry, 10);
        driveTrain.turnToHeading(gyro, telemetry, -90);

        //Driving to park under the bridge
        driveTrain.moveForwardsBy(telemetry, 28);

        //DONE
    }
}