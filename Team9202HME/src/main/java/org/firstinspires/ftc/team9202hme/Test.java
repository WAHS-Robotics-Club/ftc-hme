//Importing FTC packages, classes, and methods:
package org.firstinspires.ftc.team9202hme;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import java.lang.Math;

@TeleOp(name ="Elijah's robot test")
public class Test extends OpMode {
    DcMotor ha;

    @Override
    public void init() {
        ha =  hardwareMap.dcMotor.get("ha");
    }

    @Override
    public void loop() {
        ha.setPower(gamepad1.left_stick_y);

    }
}