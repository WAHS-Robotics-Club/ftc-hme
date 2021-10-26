package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

/*
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
YOU ARE ON THE MASTER BRANCH (!) (!) (!) (!) (!) DO NOT CODE HERE IF NOT INSTRUCTED (!) (!) (!)
*/

@TeleOp(name ="Single Driver TeleOp")
public class SingleDriver extends OpMode {
    //Classes:
    DriveTrain driveTrain;
    Misc misc;

    //Initiation process:
    @Override
    public void init(){
        //Instantiation of objects:
        driveTrain = new DriveTrain();
        misc = new Misc();
    }

    //Loop process:
    @Override
    public void loop(){
        //Drive train with 0.01 deadspace:
        driveTrain.Drive();

        //Carousel spinner:
        misc.carouselSpin();

        //Spool wind and unwind with 0.01 deadspace:
        misc.spoolSpin();
    }
}