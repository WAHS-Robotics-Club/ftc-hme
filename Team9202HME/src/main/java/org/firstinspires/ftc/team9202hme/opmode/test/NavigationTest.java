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
        telemetry.addData("Crypto Column", navigator.decodeTarget().name());

        telemetry.update();
    }
}
