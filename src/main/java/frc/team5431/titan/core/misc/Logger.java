package frc.team5431.titan.core.misc;

import edu.wpi.first.wpilibj.DriverStation;

public class Logger {
	@Deprecated
	public static boolean DEBUG = false;

	/**
	 * Logs information
	 * 
	 * @param base base string, use %s for objects
	 * @param a    objects to replace %s in order
	 */
	public static void l(String base, Object... a) {
		String msg = String.format(base, a);
		System.out.println(String.format(base, a));
		DriverStation.reportWarning(msg, false);
	}

	/**
	 * Logs errors
	 * 
	 * @param base base string, use %s for objects
	 * @param a    objects to replace %s in order
	 */
	public static void e(String base, Object... a) {
		String msg = String.format(base, a);
		System.err.println(msg);
		DriverStation.reportError(msg, false);
	}

	/**
	 * Logs exceptions
	 * 
	 * @param namespace exception namespace (before the colon)
	 * @param e         exception to log
	 */
	public static void ee(String namespace, Exception e) {
		e("%s: %s", namespace, e.getMessage());
	}

}