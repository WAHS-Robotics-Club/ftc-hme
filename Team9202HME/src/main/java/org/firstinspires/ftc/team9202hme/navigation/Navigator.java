package org.firstinspires.ftc.team9202hme.navigation;


import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.team9202hme.R;
import org.firstinspires.ftc.team9202hme.math.Vector3;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

/**
 * A collection of functions for locating images in
 * 3D space, using the robot controller phone's camera
 *
 * @author Nathaniel Glover
 */
public class Navigator {
    private VuforiaLocalizer.Parameters vuforiaSettings;
    private VuforiaTrackables targets;
    private CameraSide cameraSide;
    private PhoneOrientation orientation;

    /**
     * Creates the Navigator with necessary parameters
     *
     * @param cameraSide The camera that will be used to locate images
     * @param orientation The orientation of the phone on the robot
     * @param maxSimultaneousImageTargets The maximum number of image targets that can be tracked at one time
     * @param showCameraFeedbackOnPhone Whether or not what the camera is seeing should be displayed on the
     *                                  robot controller phone. This is good for debugging, but eats through
     *                                  the phone battery
     */
    public Navigator(CameraSide cameraSide, PhoneOrientation orientation, int maxSimultaneousImageTargets, boolean showCameraFeedbackOnPhone) {
        this.cameraSide = cameraSide;
        this.orientation = orientation;

        if(showCameraFeedbackOnPhone) {
            vuforiaSettings = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        } else {
            vuforiaSettings = new VuforiaLocalizer.Parameters();
        }

        switch(cameraSide) {
            case SCREEN: vuforiaSettings.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
                break;
            case BACK: vuforiaSettings.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
                break;
        }

        vuforiaSettings.vuforiaLicenseKey = "AYrzM+7/////AAAAGflN33oLXURIiZiOHPt5MZA2iv50tePz4bz21" +
                "btpbPci5G9i+R0v4r0iNxOOPL5mkqRO/EjcBv4TYHnKqEahIIt35JZdsc" +
                "PxAp0uHcpSONmWqRcFNglob05nEiqNkTAQKG7Ux9AhjJqZp6R+lAiCKB1" +
                "/Ht9pNZ+qK+xNE1iEtL9g708JbjmdsqT+KYCA7Rup0dqdeMGieexgSQUK" +
                "fWKIk3w/Sap1W83He60GW0UGnSUzM81fBu05Oqkl1QiAWbb9TpWff9/Yf" +
                "OJZPSCfdfErIMBuYtYgsJl5xZEtv57u6EwrqsrlwvudD1GciBrIIMmnqM" +
                "eIQu9EM5PD0dI9Oi+3jn8RPEfKauoAGDRIpUlfoI+2";
        vuforiaSettings.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, maxSimultaneousImageTargets);
    }

    private VuforiaTrackableDefaultListener getTargetListener(ImageTarget imageTarget) {
        VuforiaTrackable target = targets.get(imageTarget.ordinal());

        return (VuforiaTrackableDefaultListener) target.getListener();
    }

    /**
     * Initializes the Navigator. This should be called during either
     * {@link TeleOpProgram#init()} or {@link AutonomousProgram#run()} and
     * is necessary for the Navigator class to function properly
     */
    public void init() {
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(vuforiaSettings);

        targets = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        VuforiaTrackables relicRecoveryTarget = vuforia.loadTrackablesFromAsset("RelicVuMark");

        targets.get(0).setName("Wheels");
        targets.get(1).setName("Tools");
        targets.get(2).setName("Legos");
        targets.get(3).setName("Gears");
        targets.add(4, relicRecoveryTarget.get(0));
        targets.get(4).setName("CryptoKey");

        targets.activate();
    }

    /**
     * Checks if an image is visible
     *
     * @param target The image to look for
     * @return Whether or not it is visible
     */
    public boolean canSeeTarget(ImageTarget target) {
        return getTargetListener(target).getPose() != null;
    }

    /**
     * Returns the position, using right-hand rule, of the image in 3D space
     *
     * @param target The image to locate
     * @return The translation of the image in 3D space, relative to the phone, where x
     *         is the distance from the origin on the x axis, y is the distance from the
     *         origin on the y axis, and z is the distance from the origin on the z axis.
     *         These values are all given in millimeters, and use the right-hand rule to
     *         determine the meaning of positive and negative values
     */
    public Vector3 getRelativeTargetTranslation(ImageTarget target) {
        VuforiaTrackableDefaultListener listener = getTargetListener(target);
        OpenGLMatrix pose = listener.getPose();

        Vector3 result = new Vector3();

        if(pose != null) {
            VectorF translation = pose.getTranslation();

            switch(orientation) {
                case UPRIGHT:
                    result.x = translation.get(0);
                    result.y = translation.get(1);
                    break;
                case UPSIDE_DOWN:
                    result.x = -translation.get(0);
                    result.y = -translation.get(1);
                    break;
                case CHARGER_SIDE_UP:
                    result.x = translation.get(1);
                    result.y = -translation.get(0);
                    break;
                case VOLUME_SIDE_UP:
                    result.x = -translation.get(1);
                    result.y = translation.get(0);
                    break;
            }

            result.z = translation.get(2);

            return result;
        } else return Vector3.ZERO;
    }

    /**
     * Returns the rotation, of the image in 3D space
     *
     * @param target The image to locate
     * @return The rotation of the image in 3D space, relative to the phone, using
     *         Euler angles, where x is pitch, y is yaw, and z is roll, and all are
     *         in degrees, where a clockwise rotation is positive
     */
    public Vector3 getRelativeTargetRotation(ImageTarget target) { //TODO: Implement this function for ALL phone orientations
        VuforiaTrackableDefaultListener listener = getTargetListener(target);

        OpenGLMatrix rawPose = listener.getRawPose();

        if(rawPose != null) {
            float[] data = rawPose.getData();
            float[][] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};
            double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
            double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
            double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
            thetaX = Math.toDegrees(thetaX);
            thetaY = Math.toDegrees(thetaY);
            thetaZ = Math.toDegrees(thetaZ);

            Vector3 result = new Vector3();

            switch(cameraSide) {
                case SCREEN:
                    switch(orientation) {
                        case UPRIGHT:
                            //noinspection SuspiciousNameCombination
                            result.x = -thetaY;
                            if(thetaX >= 0) {
                                result.y = thetaX - 180;
                            } else {
                                result.y = thetaX + 180;
                            }
                            break;
                        case UPSIDE_DOWN:
                            break;
                        case CHARGER_SIDE_UP:
                            if(thetaX >= 0) {
                                result.x = -(thetaX - 180);
                            } else {
                                result.x = -(thetaX + 180);
                            }

                            result.y = -thetaY;
                            break;
                        case VOLUME_SIDE_UP:
                            break;
                    }
                    break;
                case BACK:
                    switch(orientation) {
                        case UPRIGHT:
                            //noinspection SuspiciousNameCombination
                            result.x = thetaY;

                            if(thetaX >= 0) {
                                result.y = -(thetaX - 180);
                            } else {
                                result.y = -(thetaX + 180);
                            }
                            break;
                        case UPSIDE_DOWN:
                            break;
                        case CHARGER_SIDE_UP:
                            if(thetaX >= 0) {
                                result.x = -(thetaX - 180);
                            } else {
                                result.x = -(thetaX + 180);
                            }

                            result.y = -thetaY;
                            break;
                        case VOLUME_SIDE_UP:
                            break;
                    }
                    break;
            }

            result.z = thetaZ;

            return result;
        } else return Vector3.ZERO;
    }

    /**
     * Getter for cameraSide
     *
     * @return The camera being used to locate images
     */
    public CameraSide getCameraSide() {
        return cameraSide;
    }

    /**
     * Getter for orientation
     *
     * @return The orientation of the phone on the robot
     */
    public PhoneOrientation getOrientation() {
        return orientation;
    }
}
