package frc.team5431.titan.core.misc;

import java.sql.Driver;

import edu.wpi.first.wpilibj.DriverStation;

public class Logger {
	@Deprecated
    public static boolean DEBUG = false;

	/* Log information */
	public static void l(String base, Object... a) {
		String msg = String.format(base, a);
		System.out.println(String.format(base, a));
		DriverStation.reportWarning(msg, false);
	}

	/* Log error */
	public static void e(String base, Object... a) {
		String msg = String.format(base, a);
		System.err.println(msg);
		DriverStation.reportError(msg, false);
	}

	/* Exception error */
	public static void ee(String namespace, Exception e) {
		e("%s: %s", namespace, e.getMessage());
	}

}