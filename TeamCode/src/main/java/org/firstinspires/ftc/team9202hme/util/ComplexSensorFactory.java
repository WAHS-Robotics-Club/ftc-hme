package org.firstinspires.ftc.team9202hme.util;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.team9202hme.HardwareMapConstants;

public class ComplexSensorFactory {
    public static VuforiaLocalizer getVuforiaInstance(HardwareMap hardwareMap, boolean provideCameraFeedback) {
        VuforiaLocalizer.Parameters parameters;

        if(provideCameraFeedback) {
            int viewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            parameters = new VuforiaLocalizer.Parameters(viewId);
        } else {
            parameters = new VuforiaLocalizer.Parameters();
        }

        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        parameters.vuforiaLicenseKey = "ASn81+//////AAABmUAgeaPsWE0ToMRZbjRKARoWgrYHVUAPoI3N/BM/E+ObZCJ9" +
                "OdTH+Fb7zpN0R7CyaAJ1rsUu7VZ5J81Y7KYNbHrDnpJjt6FUtdZBScxB2+r0Lddn7ikF/wj3VHfr9ySdEZwdH+Y" +
                "gIUr+NHQKuZ+Cf5t9twhOTYyQAsz/SIz+swunBP3EKO5fUrNSYYPjCgG0w7R9HbPWpAbND2HGO4HBCwFOgsLGUL" +
                "qqnWMQdTw7w7IKwolRaqzanPe9E1JXm0w+2d9EzXRe6c8xpJVfZsMUKt95ZJWC6q3hECf+cJxceJpsXVwfLiqH0" +
                "x5m4NMs0ufU+SyPAzl/nb9WE/yhHzPo0Ko/kD6rfTk0iqA/UzMsZB1x";
        parameters.cameraName = hardwareMap.get(WebcamName.class, HardwareMapConstants.CAMERA);
        parameters.useExtendedTracking = true;

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_OBJECT_TARGETS, 4);

        return ClassFactory.getInstance().createVuforia(parameters);
    }

    public static TFObjectDetector getTensorFlowObjectDetector(HardwareMap hardwareMap, VuforiaLocalizer vuforiaInstance, boolean provideCameraFeedback) {
        TFObjectDetector.Parameters tfodParameters;

        if(provideCameraFeedback) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        } else {
            tfodParameters = new TFObjectDetector.Parameters();
        }

        tfodParameters.useObjectTracker = true;
        TFObjectDetector tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforiaInstance);
        tfod.loadModelFromAsset("RoverRuckus.tflite", "Gold Mineral", "Silver Mineral");
        return tfod;
    }

    public static TFObjectDetector getTensorFlowObjectDetector(HardwareMap hardwareMap, boolean provideCameraFeedback) {
        return getTensorFlowObjectDetector(hardwareMap, getVuforiaInstance(hardwareMap, false), provideCameraFeedback);
    }
}
