package org.firstinspires.ftc.team9202hme;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain{
    //Initializing the dc motor objects:

    DcMotor flMotor;
    DcMotor frMotor;
    DcMotor blMotor;
    DcMotor brMotor;
    int targetHeading;
    Toggle toggleSpeed;

    public static DriveTrain initDriveTrain(HardwareMap hardwareMap) {
        //Hardware mapping the motors:
        DriveTrain driveTrain = new DriveTrain();

        driveTrain.flMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        driveTrain.frMotor = hardwareMap.dcMotor.get("frontRightMotor");
        driveTrain.blMotor = hardwareMap.dcMotor.get("backLeftMotor");
        driveTrain.brMotor = hardwareMap.dcMotor.get("backRightMotor");
        driveTrain.toggleSpeed = new Toggle();

        return driveTrain;
    }

    public static void manualDrive(DriveTrain driveTrain, Gamepad gamepad1){
        if(!driveTrain.toggleSpeed.isToggled()) {
            driveTrain.flMotor.setPower(-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            driveTrain.frMotor.setPower(-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
            driveTrain.blMotor.setPower(gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x);
            driveTrain.brMotor.setPower(gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x);
        }else{
            driveTrain.flMotor.setPower((-gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x)/5);
            driveTrain.frMotor.setPower((-gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x)/5);
            driveTrain.blMotor.setPower((gamepad1.left_stick_x + gamepad1.left_stick_y + -gamepad1.right_stick_x)/5);
            driveTrain.brMotor.setPower((gamepad1.left_stick_x + -gamepad1.left_stick_y + -gamepad1.right_stick_x)/5);
        }
    }

    public static void checkToggleSpeed(DriveTrain driveTrain, Gamepad gamepad1){
        if(gamepad1.left_bumper){
            driveTrain.toggleSpeed.toggle();
        }
    }

    public static void logTelemetry(Telemetry telemetry, DriveTrain driveTrain) {
       // telemetry.addData("Heading", driveTrain.getHeading() + " degrees");
//1120 ticks in a rotation
        telemetry.addData("FL Pos", driveTrain.flMotor.getCurrentPosition());
        telemetry.addData("FR Pos", driveTrain.frMotor.getCurrentPosition());
        telemetry.addData("BL Pos", driveTrain.blMotor.getCurrentPosition());
        telemetry.addData("BR Pos", driveTrain.brMotor .getCurrentPosition());
    }

    public void goForwardsTo(double inches){
        int targetPosition;
        double rotations;

        rotations = inches / (4*Math.PI);
        targetPosition = (int)(rotations * 1120);

        flMotor.setTargetPosition(-targetPosition);
        frMotor.setTargetPosition(targetPosition);
        blMotor.setTargetPosition(-targetPosition);
        brMotor.setTargetPosition(targetPosition);
    }

    public void setRunMode(DcMotor.RunMode runMode){
        flMotor.setMode(runMode);
        frMotor.setMode(runMode);
        blMotor.setMode(runMode);
        brMotor.setMode(runMode);
    }

    public void setBasePower(double power){
        flMotor.setPower(power);
        frMotor.setPower(power);
        blMotor.setPower(power);
        brMotor.setPower(power);
    }

    public void resetEncoders(){
        flMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public boolean isBusy(){
        if(flMotor.isBusy() && frMotor.isBusy() && blMotor.isBusy() && brMotor.isBusy()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isCorrectHeading(int currentHeading){
        if(targetHeading == currentHeading){
            return true;
        }else{
            return false;
        }
    }

    public void turnToHeading(int currentHeading){
        if(targetHeading < currentHeading - 5){
            flMotor.setPower(-.5);
            frMotor.setPower(-.5);
            blMotor.setPower(-.5);
            brMotor.setPower(-.5);
        }else if(targetHeading > currentHeading + 5){
            flMotor.setPower(.5);
            frMotor.setPower(.5);
            blMotor.setPower(.5);
            brMotor.setPower(.5);
        }else{
            flMotor.setPower(0);
            frMotor.setPower(0);
            blMotor.setPower(0);
            brMotor.setPower(0);
        }

    }

}


