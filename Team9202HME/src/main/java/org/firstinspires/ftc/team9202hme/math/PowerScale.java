package org.firstinspires.ftc.team9202hme.math;

import static java.lang.Math.*;

public class PowerScale {
    private final double MIN_INPUT = 0.01;

    private Function<Double> scaleFunction;
    private double degree;

    public PowerScale(Function<Double> scaleFunction) {
        this.degree = 1;
        this.scaleFunction = scaleFunction;
    }

    public PowerScale(final double minAbsolutePower, final double maxAbsolutePower) {
        this(1, minAbsolutePower, maxAbsolutePower);
    }

    public PowerScale(final int polynomialDegree, final double minAbsolutePower, final double maxAbsolutePower) {
        degree = polynomialDegree;
        if(maxAbsolutePower <= minAbsolutePower) {
            throw new IllegalArgumentException("Maximum power should be greater than minimum power");
        }

        if(maxAbsolutePower < 0 || minAbsolutePower < 0) {
            throw new IllegalArgumentException("Absolute power cannot be negative");
        }

        this.scaleFunction = new Function<Double>() {
            @Override
            public Double compute(Double input) {
                if(abs(input) < MIN_INPUT) return 0.0;

                double scaledPower = pow(abs(input), polynomialDegree);

                return max(minAbsolutePower, min(maxAbsolutePower, (maxAbsolutePower - minAbsolutePower) * scaledPower + minAbsolutePower));
            }
        };
    }

    public double scale(double power) {
        return scaleFunction.compute(power);
    }

    public double scale(double power, double scaleFactor) {
        return scaleFunction.compute(power * pow(scaleFactor, 1.0 / degree));
    }
}
