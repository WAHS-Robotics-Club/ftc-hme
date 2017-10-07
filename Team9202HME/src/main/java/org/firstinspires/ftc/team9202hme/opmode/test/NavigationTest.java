package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.math.Vector3;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.ImageTarget;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

@TeleOp(name = "Navigation Test", group = "Tests")
//@Disabled
public class NavigationTest extends OpMode {
    private Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.UPSIDE_DOWN, 1, true);

    @Override
    public void init() {
        navigator.init();
    }

    @Override
    public void loop() {
        ImageTarget target = ImageTarget.CRYPTO_KEY;

        Vector3 translation = navigator.getRelativeTargetTranslation(target);
        Vector3 rotation = navigator.getRelativeTargetRotation(target);
        translation.z = abs(translation.z);

        telemetry.addData("Navigator Data for Image Target", target.name());
        telemetry.addData("Visible", navigator.canSeeTarget(target));
        telemetry.addData("Translation", translation);
        telemetry.addData("Rotation", rotation);

        double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
        @SuppressWarnings("SuspiciousNameCombination") //Math.atan2() doesn't like receiving a variable named "x" for parameter "y"
                double alpha = toDegrees(atan2(translation.x, translation.z));
        double phi = rotation.y - alpha;

        /**
         * If a line segment parallel to the image was drawn from the robot (assuming the
         * robot is just a point) until it intersected a line perpendicular to the image,
         * lateralDistanceFromImage would be the length of that line segment.
         */
        double lateralDistanceFromImage = -hypotenuse * sin(toRadians(phi));

        telemetry.addData("Lateral Distance", lateralDistanceFromImage);

        telemetry.update();
    }
}
