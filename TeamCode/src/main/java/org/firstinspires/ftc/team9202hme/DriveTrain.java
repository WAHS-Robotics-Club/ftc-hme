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
    private int cooldown;

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

        telemetry.addData("FL Power", driveTrain.flMotor.getPower());
        telemetry.addData("FR Power", driveTrain.frMotor.getPower());
        telemetry.addData("BL Power", driveTrain.blMotor.getPower());
        telemetry.addData("BR Power", driveTrain.brMotor .getPower());

        telemetry.addData("FL Power", driveTrain.flMotor.getTargetPosition());
        telemetry.addData("FR Power", driveTrain.frMotor.getTargetPosition());
        telemetry.addData("BL Power", driveTrain.blMotor.getTargetPosition());
        telemetry.addData("BR Power", driveTrain.brMotor.getTargetPosition());
    }

    public void goForwardsTo(double inches) throws InterruptedException{
        resetEncoders();
        Thread.sleep(1);
        int targetPosition;
        double rotations;

        rotations = inches / (4*Math.PI);
        targetPosition = (int)(rotations * 1120);

        flMotor.setTargetPosition(-targetPosition);
        frMotor.setTargetPosition(targetPosition);
        blMotor.setTargetPosition(-targetPosition);
        brMotor.setTargetPosition(targetPosition);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
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

    public void turnRobotToHeading(int currentHeading){
        double modifier, basePower;
        modifier = ((Math.sqrt(Math.abs(targetHeading - currentHeading)))/2);
        basePower = 0.1;

        if(targetHeading < currentHeading - .5){
            flMotor.setPower(-basePower * modifier);
            frMotor.setPower(-basePower * modifier);
            blMotor.setPower(-basePower * modifier);
            brMotor.setPower(-basePower * modifier);
        }else if(targetHeading > currentHeading + .5){
            flMotor.setPower(basePower * modifier);
            frMotor.setPower(basePower * modifier);
            blMotor.setPower(basePower * modifier);
            brMotor.setPower(basePower * modifier);
        }else{
            flMotor.setPower(0);
            frMotor.setPower(0);
            blMotor.setPower(0);
            brMotor.setPower(0);
        }

    }
    public void resetCooldown(){
        cooldown = 0;
    }
    public boolean cooldownTimer(boolean doIncrease){
        if(!doIncrease){
            resetCooldown();
            return true;
        }else{
            cooldown++;

            if (cooldown > 500) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void moveForwardsBy(Telemetry telemetry, int inches) throws InterruptedException{
        //Going Forwards
        int i = 0;
        goForwardsTo(inches);
        setBasePower(.8);
        Thread.sleep(1);
        while(isBusy() && i < 500){
            telemetry.update();
            i++;
            Thread.sleep(1);
        }
    }

    public void turnToHeading(BananaFruit gyro, Telemetry telemetry, int inputTargetHeading) throws InterruptedException{
        //Turning
        targetHeading = inputTargetHeading;
        setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(!isCorrectHeading(gyro.getHeading())){
            telemetry.update();
            turnRobotToHeading(gyro.getHeading());
            Thread.sleep(1);
        }
    }

}


