package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class TestOp extends LinearOpMode {
    @Override



    public void runOpMode() throws InterruptedException {
        //here ya go Charlie

        DcMotor FL, FR, BL, BR;

                FL  = hardwareMap.get(DcMotor.class, "fl");
                FR = hardwareMap.get(DcMotor.class, "fr");
                BL  = hardwareMap.get(DcMotor.class, "bl");
                BR = hardwareMap.get(DcMotor.class, "br");


        waitForStart();
            //Loop stuff
        while (opModeIsActive()) {

            boolean auto, detect;
            double RSB, LSB, BSB, DSB;
            double AimX, AimY;
            auto =(gamepad1.right_bumper);
            detect = true; //If object is seen this is true

            RSB = 1; //This is the total width of the X-axis /2 minus 0.5*sweet spot
            LSB = 1; //This is RSB + sweet spot
            BSB = 1; //This is the total length of the y-axis minus the neccessary amount for the ball not to roll under the bot
            DSB = 1; //This is the BSB - sweet spot

            AimX = 1; //This is the object's x pos
            AimY = 1; //This is the object's y pos


            while(auto){
                //Inside be automatic

            while(detect){

                //Align Aim
                while(AimX>LSB){
                    FL.setPower(.25);
                    BL.setPower(.25);
                    FR.setPower(-.25);
                    BR.setPower(-.25);
                }

                while(AimX<RSB){
                    FL.setPower(-.25);
                    BL.setPower(-.25);
                    FR.setPower(.25);
                    BR.setPower(.25);
                }


                //Align Distance
                while(AimY<DSB){
                    FL.setPower(.25);
                    BL.setPower(.25);
                    FR.setPower(.25);
                    BR.setPower(.25);
                }

                while(AimY>BSB){
                    FL.setPower(-.15);
                    BL.setPower(-.15);
                    FR.setPower(-.15);
                    BR.setPower(-.15);
                }

            } //This ends detection positive

                 while(!detect){
                     FL.setPower(-.25);
                     BL.setPower(-.25);
                     FR.setPower(.25);
                     BR.setPower(.25);
                 }
            } //This ends auto


            //Manually stuff
            FL.setPower(-gamepad1.left_stick_x  + -gamepad1.left_stick_y);
            BL.setPower(gamepad1.left_stick_x  + -gamepad1.left_stick_y);
            FR.setPower(-gamepad1.left_stick_x  + gamepad1.left_stick_y);
            BR.setPower(gamepad1.left_stick_x  + gamepad1.left_stick_y);





        }




    }
}
