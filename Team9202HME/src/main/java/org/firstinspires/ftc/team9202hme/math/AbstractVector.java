package org.firstinspires.ftc.team9202hme.math;


/**
 * An interface for manipulating a mathematical
 * vector
 *
 * @param <T> The type of vector that will be used by
 *            the class's functions
 *
 * @author Nathaniel Glover
 */
abstract class AbstractVector<T extends AbstractVector<T>> {
    /**
     * Returns the dot product of this vector and another
     *
     * @param multiplier The vector with which this one will be multiplied
     * @return The dot product of the two vectors
     */
    public abstract double dot(T multiplier);

    /**
     * Returns the component-wise sum of this vector and another
     *
     * @param addition The vector with which this one will be added
     * @return The sum of the two vectors
     */
    public abstract T plus(T addition);

    /**
     * Returns the component-wise difference of this vector and another
     *
     * @param subtraction The vector which will subtract this one
     * @return The difference of the two vectors
     */
    public abstract T minus(T subtraction);

    /**
     * Returns the product of this vector and a scalar
     *
     * @param scalar The scalar with which this vector will be multiplied
     * @return The product of the vector and the scalar
     */
    public abstract T times(double scalar);

    /**
     * Returns the magnitude, or length, of this vector
     *
     * @return Magnitude of this vector
     */
    public abstract double magnitude();

    /**
     * Returns a normalized version of this vector.
     * A normalized vector is commonly referred to as
     * a unit vector
     *
     * @return This vector, normalized
     */
    public abstract T normalize();

    /**
     * Checks if this vector is equal to another. This is simply
     * here so it may be used in {@link Object#equals(Object)}
     *
     * @param vector The vector to be compared with this one
     * @return Whether or not they are equal
     */
    protected abstract boolean isEqual(Object vector);

    /**
     * Returns a string representation of this vector. This is simply
     * here so it may be used in {@link Object#toString()}
     *
     * @return A string representation of this vector's components
     */
    protected abstract String toText();

    /**
     * Compares this vector with another
     *
     * @param object The vector to be compared with this one
     * @return Whether or not they are equal
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof AbstractVector && this.isEqual(object);
    }

    /**
     * Returns a string representation of this vector's components
     *
     * @return A string representation of this vector's components
     */
    @Override
    public String toString() {
        return toText();
    }
}
