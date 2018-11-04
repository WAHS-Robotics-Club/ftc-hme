package org.firstinspires.ftc.team9202hme.util;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TelemetryManager {
    private static Telemetry telemetry;

    public static Telemetry getTelemetry() {
        if(telemetry != null) {
            return telemetry;
        } else {
            throw new NullPointerException("Telemetry instance not yet specified.");
        }
    }

    public static void setTelemetryInstance(Telemetry instance) {
        telemetry = instance;
    }
}
