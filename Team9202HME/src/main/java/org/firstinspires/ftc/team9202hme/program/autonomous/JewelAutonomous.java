package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

public class JewelAutonomous extends AutonomousProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
    private Servo jewelWhacker;
    private ColorSensor color;

    /**
     * Initializes protected members so that
     * they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     */
    public JewelAutonomous(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide);
    }

    private void init() {
        driveTrain.init(opMode.hardwareMap);
        jewelWhacker = opMode.hardwareMap.servo.get(HardwareMapConstants.JEWEL_WHACKER_SERVO);
        color = opMode.hardwareMap.colorSensor.get(HardwareMapConstants.COLOR_SENSOR);
    }

    @Override
    public void run() throws InterruptedException {
        init();

        opMode.waitForStart();

        final double SERVO_DOWN = 0.3;
        final double SERVO_UP = 0.87;

        jewelWhacker.setPosition(SERVO_UP);
        driveTrain.move(driveTrain.getMinimumMovePower(), 0, (FieldConstants.BALANCE_STONE_LENGTH - FieldConstants.ROBOT_WIDTH) / 2);
        jewelWhacker.setPosition(SERVO_DOWN);

        Thread.sleep(1000);

        boolean blueBall = false, redBall = false;

        if(color.blue() - color.red() >= 10) {
            blueBall = true;
            redBall = false;
        } else if(color.red() - color.blue() >= 10) {
            redBall = true;
            blueBall = false;
        }

        opMode.telemetry.addData("Red Value", color.red());
        opMode.telemetry.addData("Blue Value", color.blue());
        opMode.telemetry.addData("Ball Color", blueBall ? "Blue" : (redBall ? "Red" : "Unknown"));
        opMode.telemetry.update();

        Thread.sleep(1000);

        switch(fieldSide) {
            case RED:
                if(blueBall) {
                    driveTrain.turn(0.3);
                    Thread.sleep(500);
                } else if(redBall) {
                    driveTrain.turn(-0.3);
                    Thread.sleep(500);
                } else {
                    jewelWhacker.setPosition(SERVO_UP);
                }
                break;
            case BLUE:
                if(redBall) {
                    driveTrain.turn(0.3);
                    Thread.sleep(500);
                } else if(blueBall) {
                    driveTrain.turn(-0.3);
                    Thread.sleep(500);
                } else {
                    jewelWhacker.setPosition(SERVO_UP);
                }
                break;
        }

        jewelWhacker.setPosition(SERVO_UP);
    }
}
