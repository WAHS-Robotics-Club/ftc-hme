package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name ="Single Driver TeleOp")
public class Testing extends OpMode {
    //Local misc variables:
    boolean driveSwitch = false;
    boolean switchProtection = false;

    //Local DcMotor variables:
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    //Instantiation of objects:
    HardwareMap hardware = new HardwareMap(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    DriveTrain drive = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

    //Initiation process:
    @Override
    public void init(){
        //Hardwaremap ALL motors:
        hardware.motorHardware();
    }

    //Loop process:
    @Override
    public void loop(){
        //driveSwitch control:
        if(gamepad1.a == true && driveSwitch == false && switchProtection == false){
            if(driveSwitch == false){
                switchProtection = true;
            }

            driveSwitch = true;
        }
        else if(gampad1.a == true && driveSwitch == true && switchProtection == false){
            if(driveSwitch == true){
                switchProtection = true;
            }

            driveSwitch = false;
        }
        else{
            switchProtection = true;
        }

        //Driving speed control:
        if(driveSwitch == false){
            drive.regDrive();
        }
        else if(driveSwitch == true){
            drive.slowDrive();
        }
    }
}