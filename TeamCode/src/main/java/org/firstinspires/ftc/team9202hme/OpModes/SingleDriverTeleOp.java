package org.firstinspires.ftc.team9202hme.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.Objects.DriveTrain;
import org.firstinspires.ftc.team9202hme.Objects.Grabber;
import org.firstinspires.ftc.team9202hme.Objects.Misc;


@TeleOp(name ="Single Driver TeleOp - Charlie")
public class SingleDriverTeleOp extends OpMode {
    //Initializing the servo objects:

    Grabber grabber;
    DriveTrain driveTrain;
    Misc misc;

    @Override
    public void init(){
        //Hardware mapping the servos:
        grabber = Grabber.initGrabber(hardwareMap);
        driveTrain = DriveTrain.initDriveTrain(hardwareMap);
        misc = Misc.initMiscellaneous(hardwareMap);
    }

    /*
    leftFront = 2
    leftBack = 1
    rightFront = 3
    rightBack = 0
     */

    @Override public void loop(){
        //Drive Train manual control system
        driveTrain.manualDrive(gamepad1);
        driveTrain.checkToggleSpeed(gamepad1);
        DriveTrain.logTelemetry(telemetry, driveTrain);

        //Grabber System (Servos)
        Grabber.ManualToggleGrabber(grabber, gamepad1);

        //Spool controls
        Grabber.ManualSpoolMotor(grabber, gamepad1);

        //Misc controls
        misc.checkTogglePosition(gamepad1);
        misc.useMiscLoop();

    }

}