package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.team9202hme.FieldConstants;
import org.firstinspires.ftc.team9202hme.RobotConstants;
import org.firstinspires.ftc.team9202hme.hardware.CubeGrabber;
import org.firstinspires.ftc.team9202hme.hardware.JewelWhacker;
import org.firstinspires.ftc.team9202hme.hardware.MecanumDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.OmniDirectionalDrive;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.CryptoColumn;
import org.firstinspires.ftc.team9202hme.navigation.ImageTracker;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;
import org.firstinspires.ftc.team9202hme.util.Vector3;

import static java.lang.Math.*;

public class MainAutonomousProgram extends AutonomousProgram {
    private OmniDirectionalDrive driveTrain = new MecanumDriveTrain(RobotConstants.WHEEL_DIAMETER, RobotConstants.ENCODER_TICKS_PER_ROTATION);
    private JewelWhacker jewelWhacker = new JewelWhacker();
    private CubeGrabber cubeGrabber = new CubeGrabber();
    private ImageTracker imageTracker = new ImageTracker(CameraSide.BACK, PhoneOrientation.UPSIDE_DOWN, 1, true);
    private boolean shouldPlaceCube;

    /**
     * Initializes protected members so that they may be used by subclasses
     *
     * @param opMode    The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     * @param fieldPosition The position on the field that the robot will be in
     *                      when this program is run
     * @param shouldPlaceCube Whether or not this autonomous should move off of the balancing stone and place a cube or not
     */
    public MainAutonomousProgram(LinearOpMode opMode, FieldSide fieldSide, FieldPosition fieldPosition, boolean shouldPlaceCube) {
        super(opMode, fieldSide, fieldPosition);
        this.shouldPlaceCube = shouldPlaceCube;
    }

    @Override
    public void run() throws InterruptedException {
        driveTrain.init(opMode.hardwareMap);
        jewelWhacker.init(opMode.hardwareMap);
        cubeGrabber.init(opMode.hardwareMap);
        imageTracker.init();
        opMode.waitForStart();

        Thread cubeLiftThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Grab cube and lift up so the robot can move off of the balancing stone
                cubeGrabber.grab();
                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                cubeGrabber.lift();
                try {
                    Thread.sleep(750);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                cubeGrabber.stop();
            }
        });

        //Simultaneously lift cube while getting the jewel
        cubeLiftThread.start();

        //Extend the jewel whacker arm outwards, set the whacker so it will hover over
        //the jewels but not hit the wall, and then bring the whacker and color sensor
        //above the center of the jewel pedestal, and lower it to get color readings
        jewelWhacker.hitRight();
        Thread.sleep(500);
        jewelWhacker.hoverOverJewels();
        Thread.sleep(500);
        jewelWhacker.center();
        Thread.sleep(750);
        jewelWhacker.lower();
        Thread.sleep(500);

        //Read jewel color and whack it (or the other one)
        JewelWhacker.JewelColor jewelColor = jewelWhacker.readJewelColor();
        if(jewelColor != JewelWhacker.JewelColor.UNKNOWN) {
            switch(fieldSide) {
                case RED:
                    if(jewelColor == JewelWhacker.JewelColor.RED) {
                        jewelWhacker.hitRight();
                    } else {
                        jewelWhacker.hitLeft();
                    }
                    break;
                case BLUE:
                    if(jewelColor == JewelWhacker.JewelColor.RED) {
                        jewelWhacker.hitLeft();
                    } else {
                        jewelWhacker.hitRight();
                    }
                    break;
            }

            //Store the whacker mechanism back inside the robot
            Thread.sleep(500);
            jewelWhacker.raise();
            Thread.sleep(500);
            jewelWhacker.retract();
        } else {
            jewelWhacker.hoverOverJewels();
            Thread.sleep(500);
            jewelWhacker.hitRight();
            Thread.sleep(500);
            jewelWhacker.raise();
            Thread.sleep(500);
            jewelWhacker.retract();
            Thread.sleep(500);
        }

        //Turn to see the image
        driveTrain.turn(-0.25);
        Thread.sleep(500);
        driveTrain.stop();
        Thread.sleep(1000);
        Vector3 translation = imageTracker.getRelativeTargetTranslation();
        Vector3 rotation = imageTracker.getRelativeTargetRotation();
        translation.z = abs(translation.z);
        driveTrain.turn(0.25);
        Thread.sleep(500);
        driveTrain.stop();
        Thread.sleep(500);

        double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
        //noinspection SuspiciousNameCombination
        double alpha = toDegrees(atan2(translation.x, translation.z));
        double phi = rotation.y - alpha;

        double lateralDistanceFromImage = abs(-hypotenuse * sin(toRadians(phi))) / DistanceUnit.mmPerInch;

        CryptoColumn column = imageTracker.decodeTarget();

        int columnIndex = 0;
        switch(column) {
            case LEFT:
                columnIndex = fieldSide == FieldSide.RED ? 2 : 0;
                break;
            case CENTER:
                columnIndex = 1;
                break;
            case RIGHT:
                columnIndex = fieldSide == FieldSide.RED ? 0 : 2;
                break;
            case UNKNOWN: shouldPlaceCube = false;
                break;
        }

        double distanceToColumn;

        if(fieldSide == FieldSide.RED) {
            distanceToColumn = abs(imageTracker.getRelativeTargetTranslation().x / DistanceUnit.mmPerInch) + FieldConstants.CAMERA_TO_CENTER_DISTANCE
                    + FieldConstants.IMAGE_TO_CRYPTO_BOX_DISTANCE + (columnIndex + 0.5) * FieldConstants.CRYPTO_BOX_COLUMN_WIDTH;
        } else {
            distanceToColumn = FieldConstants.TILE_EDGE_LENGTH + (columnIndex + 0.5) * FieldConstants.CRYPTO_BOX_COLUMN_WIDTH;
        }

        if(shouldPlaceCube) {
            if(fieldPosition == FieldPosition.LOWER) {
                driveTrain.move(0.5, fieldSide == FieldSide.RED ? 90 : 270, distanceToColumn);

                driveTrain.move(0.4, 180);
                Thread.sleep(700);
                driveTrain.turn(0.4, 82);
            } else {
                driveTrain.move(0.5, fieldSide == FieldSide.RED ? 90 : 270, FieldConstants.TILE_EDGE_LENGTH);
                driveTrain.turn(0.4, -82);
                driveTrain.move(0.5, 90, distanceToColumn - FieldConstants.TILE_EDGE_LENGTH);
                driveTrain.turn(0.4, fieldSide == FieldSide.RED ? 82 : -82);
            }

            //Drop cube and lift claw back up to avoid getting it stuck under claw
            cubeGrabber.lower();
            Thread.sleep(1100);
            cubeGrabber.stop();
            cubeGrabber.openWide();
            cubeGrabber.lift();
            Thread.sleep(1100);
            cubeGrabber.stop();

            //Push it in and back out
            driveTrain.move(0.5, 90, 20);
            driveTrain.move(0.5, 270, 5);
        } else {
            Thread.sleep(250);
            cubeGrabber.lower();
            Thread.sleep(600);
            cubeGrabber.stop();
        }
    }
}
