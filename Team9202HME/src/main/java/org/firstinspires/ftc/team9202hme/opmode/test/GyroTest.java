package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.motion.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.motion.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.util.TelemetryManager;
import org.firstinspires.ftc.team9202hme.util.Vector2;

@TeleOp(name = "Gyro Test", group = "Tests")
//@Disabled
public class GyroTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new MecanumDriveTrain();
        driveTrain.init(hardwareMap);
        TelemetryManager.setTelemetryInstance(telemetry);

        waitForStart();

        while(opModeIsActive()) {
            if(gamepad1.a) {
                driveTrain.turnToHeading(0);
            } else {
                Vector2 velocity = new Vector2(gamepad1.left_stick_x, -gamepad1.left_stick_y);
                driveTrain.move(velocity, gamepad1.right_stick_x);
            }

            driveTrain.logTelemetry(telemetry);
            telemetry.update();
        }
    }
}
