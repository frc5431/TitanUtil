package frc.team5431.titan.core.misc;

public class Calc {
	public static boolean approxEquals(final double a, final double b, final double epsilon) {
		if (a == b) {
			return true;
		}

		return java.lang.Math.abs(a - b) < epsilon;
	}

	public static double lerp(final double a, final double b, final double f){
		return a + f * (b - a);
	}

	public static double clamp(final double val, final double min, final double max){
		return java.lang.Math.max(min, java.lang.Math.min(max, val));
	}
}