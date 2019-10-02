package org.firstinspires.ftc.team9202hme.sensory;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

public class Mineral {
    private Recognition recognition;

    public Mineral(Recognition recognition) {
        this.recognition = recognition;
    }

    public boolean exists() {
        return recognition != null;
    }

    public boolean isGold() {
        return recognition.getLabel().equals("Gold Mineral");
    }

    public double getArea() {
        return recognition.getWidth() * recognition.getHeight();
    }

    public double getOffset() {
        return (recognition.getLeft() + recognition.getRight()) / 2;
    }

    public double getAngle() {
        return recognition.estimateAngleToObject(AngleUnit.DEGREES);
    }
}
