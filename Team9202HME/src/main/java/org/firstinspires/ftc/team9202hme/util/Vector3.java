package org.firstinspires.ftc.team9202hme.util;


import static java.lang.Math.*;

/**
 * A vector containing three components
 */
public class Vector3 {
    /**
     * A constant for an empty vector
     */
    public static final Vector3 ZERO = new Vector3();

    /**
     * A component of this vector
     */
    public double x, y, z;

    /**
     * Constructs an empty vector
     */
    public Vector3() {
        this(0, 0, 0);
    }

    /**
     * Constructs a vector with the desired components
     *
     * @param x The first component
     * @param y The second component
     * @param z The third component
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double dot(Vector3 multiplier) {
        return (x * multiplier.x) + (y * multiplier.y) + (z * multiplier.z);
    }

    /**
     * Returns the cross product of this vector and another.
     * Note that cross product is only defined for 3 dimensional
     * vectors
     *
     * @param multiplier The vector with which this one will be multiplied
     * @return The cross product of the two vectors
     */
    public Vector3 cross(Vector3 multiplier) {
        return new Vector3(
                (y * multiplier.z) - (z * multiplier.y),
                (z * multiplier.x) - (x * multiplier.z),
                (x * multiplier.y) - (y * multiplier.x)
        );
    }

    public Vector3 plus(Vector3 addition) {
        return new Vector3(x + addition.x, y + addition.y, z + addition.z);
    }

    public Vector3 minus(Vector3 subtraction) {
        return new Vector3(x - subtraction.x, y - subtraction.y, z - subtraction.z);
    }

    public Vector3 times(double scalar) {
        return new Vector3(x * scalar, y * scalar, z * scalar);
    }

    public double magnitude() {
        return sqrt((x * x) + (y * y) + (z * z));
    }

    public Vector3 normalize() {
        double length = magnitude();
        return new Vector3(x / length, y / length, z / length);
    }

    private boolean isEqual(Object vector) {
        Vector3 comparator = (Vector3) vector;
        return (x == comparator.x) && (y == comparator.y) && (z == comparator.z);
    }

    @Override
    public String toString() {
        return "(\n" +
                "\tX: " + x + ",\n" +
                "\tY: " + y + ",\n" +
                "\tZ: " + z +
                "\n)";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Vector3 && this.isEqual(object);
    }
}
