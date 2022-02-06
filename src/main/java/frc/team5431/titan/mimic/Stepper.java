package frc.team5431.titan.mimic;

import frc.team5431.titan.core.misc.Logger;

/**
 * @author David Smerkous
 * @author Ryan Hirasaki
 * @deprecated since 2022.3.1.0
 */
@Deprecated
public class Stepper {
    public static final String mimicFile = "/media/sda1/%s.mimic";
    public static final String formatString = "%.2f,%.2f,%.2f,%.4f,%.4f,%d\n"; // LEFT ENCODER, RIGHT ENCODER, GYRO
                                                                               // ANGLE, LEFT POWER, RIGHT POWER, HOME
    public double leftDistance, rightDistance, angle, leftPower, rightPower;
    public boolean isHome;

    public Stepper(final double lD, final double rD, final double a, final double lP, final double rP,
            final boolean h) {
        leftDistance = lD;
        rightDistance = rD;
        angle = a;
        leftPower = lP;
        rightPower = rP;
        isHome = h;
    }

    public Stepper(final String toParse) {
        try {
            final String parts[] = toParse.split(",");
            leftDistance = getDouble(parts[0]);
            rightDistance = getDouble(parts[1]);
            angle = getDouble(parts[2]);
            leftPower = getDouble(parts[3]);
            rightPower = getDouble(parts[4]);
            isHome = getBoolean(parts[5]);
        } catch (Exception e) {
            Logger.ee("MimicParse", e);
        }
    }

    private static final double getDouble(final String data) {
        return getDouble(data, 0.0);
    }

    private static final double getDouble(final String data, final double defaultValue) {
        try {
            return Double.parseDouble(data);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    private static final boolean getBoolean(final String data) {
        return getBoolean(data, false);
    }

    private static final boolean getBoolean(final String data, final boolean defaultValue) {
        try {
            return Integer.parseInt(data) == 1;
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public String toString() {
        return String.format(formatString, leftDistance, rightDistance, angle, leftPower, rightPower, (isHome) ? 1 : 0);
    }
}