package org.firstinspires.ftc.team9202hme;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.lang.reflect.Method;

public class DriveTrain {

    DcMotor fl;
    DcMotor bl;
    DcMotor fr;
    DcMotor br;

    HardwareMap hardwareMap;
    Telemetry telemetry;
    BananaFruit gyro;


    public DriveTrain(HardwareMap _hardwareMap, Telemetry _telemetry){
        hardwareMap = _hardwareMap;
        telemetry = _telemetry;
        gyro = new BananaFruit();
        gyro.runBananaFruit(hardwareMap, telemetry);
        fl = hardwareMap.dcMotor.get("frontLeftMotor");
        bl = hardwareMap.dcMotor.get("backLeftMotor");
        fr = hardwareMap.dcMotor.get("frontRightMotor");
        br = hardwareMap.dcMotor.get("backRightMotor");
    }

    public void initTelemetry(){
        telemetry.addData("FL Power: ", fl.getPower());
        telemetry.addData("BL Power: ", bl.getPower());
        telemetry.addData("FR Power", fr.getPower());
        telemetry.addData("BR Power", br.getPower());
        telemetry.update();
    }

    public void setMode(DcMotor.RunMode mode){
        fl.setMode(mode);
        bl.setMode(mode);
        fr.setMode(mode);
        br.setMode(mode);
    }

    public void setTargetPosition(int targetPosition){
        fl.setTargetPosition(-targetPosition);
        bl.setTargetPosition(-targetPosition);
        fr.setTargetPosition(targetPosition);
        br.setTargetPosition(targetPosition);
    }

    public void setPower(double power){
        fl.setPower(power);
        bl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
    }

    public void waitUntilDone() {
        int i = 0;
        while(fl.isBusy() && bl.isBusy() && fr.isBusy() && br.isBusy() && i < 500){
            telemetry.update();
            i++;
            try{
                Thread.sleep(1);
            } catch(InterruptedException exception){
                //Thread.sleep can throw this if interrupted with something like Thread.interrupt
                //As this is looping with very small sleep times it's no big deal if one is interrupted
            }
        }
    }

    public void moveForwards(int inches){

        inches = -inches;
        double rotations = inches / (4*Math.PI);
        int targetPosition = (int)(rotations*1120);
        setTargetPosition(targetPosition);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double maxDist = targetPosition + bl.getCurrentPosition();
        float currentDist;
        int i = 0;
        while(fl.isBusy() && bl.isBusy() && fr.isBusy() && br.isBusy() && i < 500){
            telemetry.update();
            currentDist = targetPosition + bl.getCurrentPosition();
            double modifier = Math.sqrt(Math.abs(currentDist/maxDist));
            if(modifier < 0.2){
                modifier = 0.2;
            }
            else if(modifier > 1){
                modifier = 1;
            }
            setPower(modifier);
            i++;
            try{
                Thread.sleep(1);
            } catch(InterruptedException exception){ }
        }
    }

    //Turning

    public boolean isCorrectHeading(int targetHeading){
        int dist = signedAngleDistance(gyro.getHeading(), targetHeading);
        if(dist > -1.25 && dist < 1.25){
            return true;
        } else {
            return false;
        }
    }

    //Turns any angle into an angle in the range of 0 to 360
    public int positiveAngle(int angle){
        while(angle < 0){
            angle += 360;
        }
        angle = angle%360;
        return angle;
    }

    //Returns the shortest change needed to get from angle 1 to angle 2
    public int signedAngleDistance(int angle1, int angle2){
        angle1 = positiveAngle(angle1);
        angle2 = positiveAngle(angle2);
        int dist = (angle2 - angle1 + 180) % 360 - 180;
        return  dist;
    }

    public void turnToHeading(int targetHeading){
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int currentHeading;
        while(!isCorrectHeading(targetHeading)){
            telemetry.update();
            currentHeading = gyro.getHeading();

            /*
            if(currentHeading > 145 || currentHeading < -145){
                if(currentHeading < 0){
                    currentHeading += 360;
                }
            }*/

            double modifier = ((Math.sqrt(Math.abs(signedAngleDistance(currentHeading, targetHeading))))/2);
            double basePower = 0.1;

            if(signedAngleDistance(currentHeading, targetHeading) > 0){
                setPower(basePower * modifier);
            }
            else if (signedAngleDistance(currentHeading, targetHeading) < 0){
                setPower(-basePower * modifier);
            } else{
                setPower(0);
            }

            try{
                Thread.sleep(1);
            } catch (InterruptedException e){}
        }

    }



}
