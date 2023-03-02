package frc.team5431.titan.core.misc;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * Logger helper for logging to the Driver Station
 * 
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class Logger {

	/**
	 * Logs information
	 * 
	 * @param base base string, use %s for objects
	 * @param a    objects to replace %s in order
	 */
	public static void l(String base, Object... a) {
		String msg = String.format(base, a);
		// System.out.println(msg);
		DataLogManager.log(msg);
	}

	/**
	 * Logs errors
	 * 
	 * @param base base string, use %s for objects
	 * @param a    objects to replace %s in order
	 */
	public static void e(String base, Object... a) {
		String msg = String.format(base, a);
		// System.err.println(msg);
		DriverStation.reportError(msg, false);
	}

	/**
	 * Logs exceptions
	 * 
	 * @param namespace exception namespace (before the colon)
	 * @param e         exception to log
	 */
	public static void ee(String namespace, Exception e) {
		String msg = String.format("%s: %s", namespace, e.getMessage());
		DriverStation.reportError(msg, e.getStackTrace());
	}
}