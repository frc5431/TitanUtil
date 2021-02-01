package frc.team5431.titan.core.misc;

import java.lang.Math;

public class Calc {
	public final static boolean approxEquals(final double a, final double b, final double epsilon) {
		if (a == b) {
			return true;
		}

		return Math.abs(a - b) < epsilon;
	}

	public final static double lerp(final double a, final double b, final double f) {
		return a + f * (b - a);
	}

	public final static double clamp(final double val, final double min, final double max) {
		return Math.max(min, Math.min(max, val));
	}
}