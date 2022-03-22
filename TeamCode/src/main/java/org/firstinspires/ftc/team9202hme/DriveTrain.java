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

    public DriveTrain(HardwareMap _hardwareMap, Telemetry _telemetry){
        hardwareMap = _hardwareMap;
        telemetry = _telemetry;
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
        waitUntilDone();
    }



}
