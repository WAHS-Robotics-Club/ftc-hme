package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

public class JewelAutonomous extends AutonomousProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
    private JewelWhacker jewelWhacker = new JewelWhacker();

    /**
     * Initializes protected members so that they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     */
    public JewelAutonomous(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide);
    }

    private void init() {
        driveTrain.init(opMode.hardwareMap);
        jewelWhacker.init(opMode.hardwareMap);
    }

    @Override
    public void run() throws InterruptedException {
        init();

        opMode.waitForStart();

        jewelWhacker.raise();
        driveTrain.move(driveTrain.getMinimumMovePower(), 0, (FieldConstants.BALANCE_STONE_LENGTH - FieldConstants.ROBOT_WIDTH) / 2);
        jewelWhacker.lower();

        Thread.sleep(1000);

        jewelWhacker.logTelemetry(opMode.telemetry);
        opMode.telemetry.update();

        Thread.sleep(1000);

        JewelWhacker.JewelColor jewelColor = jewelWhacker.readJewelColor();

        //Set turn speed for robot based on field side and detected color of jewel (color sensor is on the right side of the whacker)
        switch(fieldSide) {
            case RED:
                switch(jewelColor) {
                    case RED: driveTrain.turn(-0.3);
                        break;
                    case BLUE: driveTrain.turn(0.3);
                        break;
                    case UNKNOWN: jewelWhacker.raise();
                        break;
                }
                break;
            case BLUE:
                switch(jewelColor) {
                    case RED: driveTrain.turn(0.3);
                        break;
                    case BLUE: driveTrain.turn(-0.3);
                        break;
                    case UNKNOWN: jewelWhacker.raise();
                        break;
                }
                break;
        }

        Thread.sleep(300);

        jewelWhacker.raise();
        driveTrain.stop();

        Thread.sleep(1000);
    }
}
