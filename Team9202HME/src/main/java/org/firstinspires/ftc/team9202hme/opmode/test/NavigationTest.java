package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.navigation.ImageTracker;
import org.firstinspires.ftc.team9202hme.util.Vector3;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;

import static java.lang.Math.abs;

@TeleOp(name = "Navigation Test", group = "Tests")
//@Disabled
public class NavigationTest extends OpMode {
    private ImageTracker imageTracker = new ImageTracker(CameraSide.BACK, PhoneOrientation.UPRIGHT, 1, true);

    @Override
    public void init() {
        imageTracker.init();
    }

    @Override
    public void loop() {
        Vector3 translation = imageTracker.getRelativeTargetTranslation();
        Vector3 rotation = imageTracker.getRelativeTargetRotation();
        translation.z = abs(translation.z);

        telemetry.addData("Visible", imageTracker.canSeeTarget());
        telemetry.addData("Translation", translation);
        telemetry.addData("Rotation", rotation);
        telemetry.addData("Crypto Column", imageTracker.decodeTarget().name());

        telemetry.update();
    }
}
