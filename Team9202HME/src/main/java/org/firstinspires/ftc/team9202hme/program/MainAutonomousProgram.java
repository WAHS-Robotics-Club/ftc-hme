package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.hardware.CubeGrabber;
import org.firstinspires.ftc.team9202hme.hardware.OmniDirectionalDrive;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.CryptoColumn;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;

import static java.lang.Math.abs;

public class MainAutonomousProgram extends AutonomousProgram {
    private OmniDirectionalDrive driveTrain = new MecanumDriveTrain(FieldConstants.WHEEL_DIAMETER, FieldConstants.ENCODER_TICKS_PER_ROTATION);
    private JewelWhacker jewelWhacker = new JewelWhacker();
    private CubeGrabber cubeGrabber = new CubeGrabber();
    private Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.UPRIGHT, 1, true);
    private boolean shouldPlaceCube;

    /**
     * Initializes protected members so that they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     * @param shouldPlaceCube Whether or not this autonomous should also place
     *                        a cube in the correct (image-decoded) cryptobox
     */
    public MainAutonomousProgram(LinearOpMode opMode, FieldSide fieldSide, boolean shouldPlaceCube) {
        super(opMode, fieldSide);
        this.shouldPlaceCube = shouldPlaceCube;
    }

    @Override
    public void run() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);
        jewelWhacker.init(opMode.hardwareMap);
        cubeGrabber.init(opMode.hardwareMap);
        navigator.init();
        opMode.waitForStart();

        cubeGrabber.grab();
        Thread.sleep(500);

        cubeGrabber.lift();
        Thread.sleep(1000);
        cubeGrabber.stop();

        jewelWhacker.extend(10.5);

        jewelWhacker.stop();
        jewelWhacker.lower();

        Thread.sleep(800);

        JewelWhacker.JewelColor jewelColor = jewelWhacker.readJewelColor();
        boolean counterClockwiseTurn = false;
        boolean skipToCubePlacement = false;

        //Set turn speed for robot based on field side and detected color of jewel (color sensor is on the left side of the whacker)
        switch(fieldSide) {
            case RED:
                switch(jewelColor) {
                    case RED:
                        driveTrain.turn(0.3);
                        counterClockwiseTurn = true;
                        break;
                    case BLUE:
                        driveTrain.turn(-0.3);
                        break;
                    case UNKNOWN: skipToCubePlacement = true;
                        break;
                }
                break;
            case BLUE:
                switch(jewelColor) {
                    case RED:
                        driveTrain.turn(-0.3);
                        break;
                    case BLUE:
                        driveTrain.turn(0.3);
                        counterClockwiseTurn = true;
                        break;
                    case UNKNOWN: skipToCubePlacement = true;
                        break;
                }
                break;
        }

        Thread.sleep(400);
        driveTrain.stop();
        Thread.sleep(200);
        jewelWhacker.raise();

        jewelWhacker.retract(10.5);

        if(!skipToCubePlacement) {
            driveTrain.turn(counterClockwiseTurn ? -0.3 : 0.3);
            Thread.sleep(400);
        }

        driveTrain.stop();
        Thread.sleep(500);

        double distanceFromImage = -navigator.getRelativeTargetTranslation().x * 0.0393701;
        CryptoColumn column = navigator.decodeTarget();

        int columnIndex = -1;
//        switch(column) {
//            case LEFT: columnIndex = fieldSide == FieldSide.RED ? 3 : 1;
//                break;
//            case CENTER: columnIndex = 2;
//                break;
//            case RIGHT: columnIndex = fieldSide == FieldSide.RED ? 1 : 3;
//                break;
//            case UNKNOWN: shouldPlaceCube = false;
//                break;
//        }
        switch(column) {
            case LEFT: columnIndex = fieldSide == FieldSide.RED ? 3 : 3;
                break;
            case CENTER: columnIndex = 2;
                break;
            case RIGHT: columnIndex = fieldSide == FieldSide.RED ? 1 : 1;
                break;
            case UNKNOWN: shouldPlaceCube = false;
                break;
        }

        double distanceToColumn = distanceFromImage + FieldConstants.CAMERA_TO_CENTER_DISTANCE + FieldConstants.IMAGE_TO_CRYPTO_BOX_DISTANCE
                + columnIndex * FieldConstants.CRYPTO_BOX_COLUMN_WIDTH;

        if(shouldPlaceCube) {
            driveTrain.move(0.5, 90, distanceToColumn);

            driveTrain.move(0.4, 180);
            Thread.sleep(1000);

            driveTrain.turn(0.5, 90);
            cubeGrabber.lower();
            Thread.sleep(1100);
            cubeGrabber.stop();
            cubeGrabber.openWide();
            cubeGrabber.lift();
            Thread.sleep(1100);
            cubeGrabber.stop();

            driveTrain.move(0.5, 90, 22);
            driveTrain.move(0.5, 270, 5);
        } else {
//            driveTrain.move(0.5, 90, 32);
        }
    }
}
