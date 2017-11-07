package org.firstinspires.ftc.team9202hme.util;

import static java.lang.Math.*;

/**
 * Simple class for scaling gamepad input to be more driver-friendly. Supports
 * a few different scaling functions, including n-degree monomials, logistic functions,
 * and purely user-defined scaling functions, all of which have a minimum absolute input
 * of 0.01; any input below this will give an output of zero.
 */
public class PowerScale {
    private static final double MIN_INPUT = 0.01;

    private Function<Double> scaleFunction;
    private Function<Double> scalarModifierFunction;

    public static PowerScale CreateMonomialScaleFunction(final int degree, final double minAbsolutePower, final double maxAbsolutePower) {
        if(maxAbsolutePower <= minAbsolutePower) {
            throw new IllegalArgumentException("Maximum power should be greater than minimum power");
        }

        if(maxAbsolutePower < 0 || minAbsolutePower < 0) {
            throw new IllegalArgumentException("Absolute power cannot be negative");
        }

        Function<Double> scaleFunction = new Function<Double>() {
            @Override
            public Double compute(Double input) {
                if(abs(input) < MIN_INPUT) return 0.0;

                double scaledPower = pow(abs(input), degree);

                return max(minAbsolutePower, min(maxAbsolutePower, (maxAbsolutePower - minAbsolutePower)
                        * scaledPower + minAbsolutePower)) * (abs(input) / input);
            }
        };

        Function<Double> scalarModifierFunction = new Function<Double>() {
            @Override
            public Double compute(Double input) {
                return pow(input, 1.0 / (double) degree);
            }
        };

        return new PowerScale(scaleFunction, scalarModifierFunction);
    }

    //TODO: Implement logistic scale function
    public static PowerScale CreateLogisticScaleFunction(final double minAbsolutePower, final double maxAbsolutePower) {
        throw new UnsupportedOperationException("Not Yet Implemented");
    }

    public PowerScale(Function<Double> scaleFunction) {
        this.scaleFunction = scaleFunction;
        this.scalarModifierFunction = new Function<Double>() {
            @Override
            public Double compute(Double input) {
                return input;
            }
        };
    }

    public PowerScale(Function<Double> scaleFunction, Function<Double> scalarModifierFunction) {
        this.scaleFunction = scaleFunction;
        this.scalarModifierFunction = scalarModifierFunction;
    }

    public double scale(double power) {
        return scaleFunction.compute(power);
    }

    public double scale(double power, double scaleFactor) {
        return scaleFunction.compute(power * scalarModifierFunction.compute(scaleFactor));
    }
}
