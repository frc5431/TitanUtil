package frc.team5431.titan.core.misc;

public class Logger {
	public static boolean DEBUG = false;

	/**
	 * Logs information
	 * 
	 * @param base base string, use %s for objects
	 * @param a    objects to replace %s in order
	 */
	public static void l(String base, Object... a) {
		if (DEBUG)
			System.out.println(String.format(base, a));
	}

	/**
	 * Logs errors
	 * 
	 * @param base base string, use %s for objects
	 * @param a    objects to replace %s in order
	 */
	public static void e(String base, Object... a) {
		if (DEBUG)
			System.err.println(String.format(base, a));
	}

	/**
	 * Logs exceptions
	 * 
	 * @param namespace exception namespace (before the colon)
	 * @param e         exception to log
	 */
	public static void ee(String namespace, Exception e) {
		if (DEBUG)
			e("%s: %s", namespace, e.getMessage());
	}

}