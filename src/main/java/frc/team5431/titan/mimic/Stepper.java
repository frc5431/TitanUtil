package frc.team5431.titan.mimic;

import frc.team5431.titan.core.Logger;

public class Stepper {
    public static final String mimicFile = "/media/sda1/%s.mimic";
    public static final String formatString = "%.2f,%.2f,%.2f,%.4f,%.4f,%d,%d\n"; //LEFT ENCODER, RIGHT ENCODER, GYRO ANGLE, LEFT POWER, RIGHT POWER, HOME, SWITCH SHOOT
    public double leftDistance, rightDistance, angle, leftPower, rightPower;
    public boolean isHome, isSwitch;

    public Stepper(final double lD, final double rD, final double a, final double lP, final double rP, final boolean h, final boolean sw) {
        leftDistance = lD;
        rightDistance = rD;
        angle = a;
        leftPower = lP;
        rightPower = rP;
        isHome = h;
        isSwitch = sw;
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
            isSwitch = getBoolean(parts[6]);
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
        return String.format(formatString, leftDistance, rightDistance, angle, leftPower, rightPower, (isHome) ? 1 : 0, (isSwitch) ? 1 : 0);
    }
}