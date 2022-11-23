package frc.team5431.titan.swerve.sim;

/**
 * This is a 2D vector struct that supports basic vector operations.
 */
@SuppressWarnings("MemberName")
public class Vector2d {

    public double x;
    public double y;

    public Vector2d() {
        this(0.0, 0.0);
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double cross(Vector2d other) {
        return x * other.y - y * other.x;
    }

    /**
     * Rotate a vector in Cartesian space.
     *
     * @param angle angle in degrees by which to rotate vector counter-clockwise.
     */
    public void rotate(double angle) {
        double cosA = Math.cos(angle * (Math.PI / 180.0));
        double sinA = Math.sin(angle * (Math.PI / 180.0));
        double[] out = new double[2];
        out[0] = x * cosA - y * sinA;
        out[1] = x * sinA + y * cosA;
        x = out[0];
        y = out[1];
    }

    /**
     * Returns dot product of this vector with argument.
     *
     * @param vec Vector with which to perform dot product.
     * @return Dot product of this vector with argument.
     */
    public double dot(Vector2d vec) {
        return x * vec.x + y * vec.y;
    }

    /**
     * Returns magnitude of vector.
     *
     * @return Magnitude of vector.
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Returns scalar projection of this vector onto argument.
     *
     * @param vec Vector onto which to project this vector.
     * @return scalar projection of this vector onto argument.
     */
    public double scalarProject(Vector2d vec) {
        return dot(vec) / vec.magnitude();
    }
}
