package org.firstinspires.ftc.team9202hme.util;

public class UnitConversions {
    public static final double MM_PER_INCH = 25.4;

    public static double inchesToMm(double inches) {
        return inches * MM_PER_INCH;
    }

    public static double mmToInches(double mm) {
        return mm / MM_PER_INCH;
    }
}
