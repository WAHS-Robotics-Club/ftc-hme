package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.hardware.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

public class MainAutonomousProgram extends AutonomousProgram {
    private HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
    private JewelWhacker jewelWhacker = new JewelWhacker();
    private Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.VOLUME_SIDE_DOWN, 1, true);
    private boolean shouldMove;

    /**
     * Initializes protected members so that they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     */
    public MainAutonomousProgram(LinearOpMode opMode, FieldSide fieldSide, boolean shouldMove) {
        super(opMode, fieldSide);
        this.shouldMove = shouldMove;
    }

    private void init() {
        driveTrain.init(opMode.hardwareMap);
        jewelWhacker.init(opMode.hardwareMap);
        navigator.init();
    }

    @Override
    public void run() throws InterruptedException {
        init();

        opMode.waitForStart();

        jewelWhacker.raise();
        driveTrain.move(driveTrain.getMinimumMovePower(), 0, (FieldConstants.BALANCE_STONE_LENGTH - FieldConstants.ROBOT_WIDTH) / 2 + 1);
        jewelWhacker.lower();

        Thread.sleep(300);

        jewelWhacker.logTelemetry(opMode.telemetry);
        opMode.telemetry.update();

        Thread.sleep(1000);

        JewelWhacker.JewelColor jewelColor = jewelWhacker.readJewelColor();

        boolean counterClockwiseTurn = false;

        //Set turn speed for robot based on field side and detected color of jewel (color sensor is on the right side of the whacker)
        switch(fieldSide) {
            case RED:
                switch(jewelColor) {
                    case RED:
                        driveTrain.turn(-0.3);
                        counterClockwiseTurn = true;
                        break;
                    case BLUE:
                        driveTrain.turn(0.3);
                        break;
                    case UNKNOWN:
                        jewelWhacker.raise();
                        break;
                }
                break;
            case BLUE:
                switch(jewelColor) {
                    case RED:
                        driveTrain.turn(0.3);
                        break;
                    case BLUE:
                        driveTrain.turn(-0.3);
                        counterClockwiseTurn = true;
                        break;
                    case UNKNOWN:
                        jewelWhacker.raise();
                        break;
                }
                break;
        }

        Thread.sleep(300);
        jewelWhacker.raise();

        Thread.sleep(500);
        driveTrain.turn(counterClockwiseTurn ? 0.3 : -0.3);
        Thread.sleep(300);
        driveTrain.move(0.7, 180, (FieldConstants.BALANCE_STONE_LENGTH - FieldConstants.ROBOT_WIDTH) / 2 + 6);
        Thread.sleep(300);

        driveTrain.stop();

        if(shouldMove) {
            Thread.sleep(1000);
            driveTrain.move(0.5, 90, 40);

            if(counterClockwiseTurn) {
                driveTrain.move(0.5, -15, 27);
            }
        }
    }
}
