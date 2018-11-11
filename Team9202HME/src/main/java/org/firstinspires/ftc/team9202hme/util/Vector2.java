package org.firstinspires.ftc.team9202hme.util;


import static java.lang.Math.*;

/**
 * A vector containing two components
 */
public class Vector2 {
    /**
     * A constant for an empty vector
     */
    public static final Vector2 ZERO = new Vector2();

    /**
     * A component of this vector
     */
    public double x, y;

    /**
     * Constructs an empty vector
     */
    public Vector2() {
        this(0, 0);
    }

    /**
     * Constructs a vector with the desired components
     *
     * @param x The first component
     * @param y The second component
     */
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double dot(Vector2 multiplier) {
        return (x * multiplier.x) + (y * multiplier.y);
    }

    public Vector2 plus(Vector2 addition) {
        return new Vector2(x + addition.x, y + addition.y);
    }

    public Vector2 minus(Vector2 subtraction) {
        return new Vector2(x - subtraction.x, y - subtraction.y);
    }

    public Vector2 times(double scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    public double magnitude() {
        return sqrt((x * x) + (y * y));
    }

    public double direction() {
        return toDegrees(atan2(y, x));
    }

    public Vector2 normalize() {
        double length = magnitude();
        return new Vector2(x / length, y / length);
    }

    private boolean isEqual(Object vector) {
        Vector2 comparator = (Vector2) vector;
        return (x == comparator.x) && (y == comparator.y);
    }

    @Override
    public String toString() {
        return "(\n" +
                "\tX: " + x + ",\n" +
                "\tY: " + y +
                "\n)";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Vector2 && this.isEqual(object);
    }
}
