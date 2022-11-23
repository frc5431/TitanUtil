package frc.team5431.titan.core.misc;

import java.lang.Math;

/**
 * Utility math functions.
 * 
 * @see edu.wpi.first.math.MathUtil
 */
public final class Calc {

	/**
	 * Detects whether two values are within a certain range of each other.
	 * 
	 * @param a       the first value
	 * @param b       the second value
	 * @param epsilon the range
	 * @return whether they are approximately equal according to the range
	 * @throws IllegalArgumentException if epsilon is negative
	 */
	public final static boolean approxEquals(final double a, final double b, final double epsilon)
			throws IllegalArgumentException {
		if (epsilon < 0)
			throw new IllegalArgumentException("Epsilon must be positive");

		if (a == b)
			return true;

		return Math.abs(a - b) < epsilon;
	}

	/**
	 * Lerps two values by the given factor. Replacement for
	 * {@link edu.wpi.first.math.MathUtil#interpolate(double, double, double)} that
	 * does not restrict the multiplier to [0, 1]. Example: having values 4 and 7
	 * and a factor of 1.33 gives 8, and having values 10 and 20 with a factor of
	 * 0.65 gives 16.5.
	 * 
	 * @param a the first value
	 * @param b the second value
	 * @param f the multiplier of the difference
	 * @return the result of the lerp
	 */
	public final static double lerp(final double a, final double b, final double f) {
		return a + f * (b - a);
	}

	/**
	 * Clamps a number between two values.
	 * 
	 * @deprecated use
	 *             {@link edu.wpi.first.math.MathUtil#clamp(double, double, double)}
	 * 
	 * @param val the value to clamp
	 * @param min the minimum allowed value
	 * @param max the maximum allowed value
	 * @return the clamped value
	 * @throws IllegalArgumentException if max is less than min
	 */
	@Deprecated
	public final static double clamp(final double val, final double min, final double max)
			throws IllegalArgumentException {
		if (min > max)
			throw new IllegalArgumentException("Clamp max must be greater than min");
		return Math.max(min, Math.min(max, val));
	}

	/**
	 * Maps a value from one range to another. Note: min and maxes can be swapped
	 * for easy flipping over a range.
	 * 
	 * @param val     the value to map
	 * @param in_min  the initial range's minimum
	 * @param in_max  the initial range's maximum
	 * @param out_min the output range's minimum
	 * @param out_max the output range's maximum
	 * @return the mapped value
	 */
	public final static double map(double val, double in_min, double in_max, double out_min, double out_max) {
		return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	/**
	 * Helper function for quadratic equations:
	 * 
	 * <code>ax^2 + bx + c</code>
	 */
	public static double quadratic(double x, double a, double b, double c) {
		return a * x * x
				+ b * x
				+ c;
	}

	/**
	 * Helper function for logarithmic equations:
	 * 
	 * <code>a * log(x - c) + b</code>
	 */
	public static double logarithm(double x, double a, double b, double c) {
		return a * Math.log10(x - c) + b;
	}

	private Calc() {
		throw new AssertionError("Utility class should not be instantiated!");
	}
}