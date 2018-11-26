package org.firstinspires.ftc.team9202hme.motion;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.util.Vector2;

public class OmniDriveTrain extends HolonomicDriveTrain {
    /**
     * Gives drive train the values it needs to calculate how to properly apply motor powers
     * and handle encoder positions when moving and turning autonomously
     *
     * @param wheelDiameter           The diameter of the robot's wheels; unit is unnecessary as long as it is consistent with other distances
     * @param encoderTicksPerRotation The number of ticks given off by each motor's
     *                                encoder each rotation. If you are using Andymark
     */
    public OmniDriveTrain(double wheelDiameter, int encoderTicksPerRotation) {
        super(wheelDiameter, encoderTicksPerRotation);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        super.init(hardwareMap);
    }

    @Override
    public double getHeading() {
        return 0;
    }

    @Override
    public void move(Vector2 velocity, double turnPower) {

    }

    @Override
    public void moveToDisplacement(Vector2 displacement, double movePower) throws InterruptedException {

    }

    @Override
    public void turnToHeading(double heading) throws InterruptedException {

    }

    @Override
    public void logTelemetry(Telemetry telemetry) {

    }
}
