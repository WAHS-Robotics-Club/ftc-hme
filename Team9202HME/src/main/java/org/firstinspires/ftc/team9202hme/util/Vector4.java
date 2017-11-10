package org.firstinspires.ftc.team9202hme.util;


import static java.lang.Math.*;

/**
 * A vector containing four components
 *
 * @author Nathaniel Glover
 */
public class Vector4 {
    /**
     * A constant for an empty vector
     */
    public static final Vector4 ZERO = new Vector4();

    /**
     * A component of this vector
     */
    public double x, y, z, w;

    /**
     * Constructs an empty vector
     */
    public Vector4() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a vector with the desired components
     *
     * @param x The first component
     * @param y The second component
     * @param z The third component
     * @param w The fourth component
     */
    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double dot(Vector4 multiplier) {
        return (x * multiplier.x) + (y * multiplier.y) + (z * multiplier.z) + (w * multiplier.w);
    }

    public Vector4 plus(Vector4 addition) {
        return new Vector4(x + addition.x, y + addition.y, z + addition.z, w + addition.w);
    }

    public Vector4 minus(Vector4 subtraction) {
        return new Vector4(x - subtraction.x, y - subtraction.y, z - subtraction.z, w - subtraction.w);
    }

    public Vector4 times(double scalar) {
        return new Vector4(x * scalar, y * scalar, z * scalar, w * scalar);
    }

    public double magnitude() {
        return sqrt((x * x) + (y * y) + (z * z) + (w * w));
    }

    public Vector4 normalize() {
        double length = magnitude();
        return new Vector4(x / length, y / length, z / length, w / length);
    }

    private boolean isEqual(Object vector) {
        Vector4 comparator = (Vector4) vector;
        return (x == comparator.x) && (y == comparator.y) && (z == comparator.z) && (w == comparator.w);
    }

    @Override
    public String toString() {
        return "(\n" +
                "\tX: " + x + ",\n" +
                "\tY: " + y + ",\n" +
                "\tZ: " + z + ",\n" +
                "\tW: " + w +
                "\n)";
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Vector4 && this.isEqual(object);
    }
}
