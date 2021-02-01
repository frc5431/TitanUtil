package frc.team5431.titan.core.joysticks.utils;

import frc.team5431.titan.core.joysticks.Joystick.POVZone;

/**
 * @author Ryan Hirasaki
 */
public enum CompassPOV implements POVZone {
    NONE, NORTH, NORTHEAST, EAST, SOUTHEAST, //
    SOUTH, SOUTHWEST, WEST, NORTHWEST;

    public static CompassPOV find(int angle) {
        if (angle < 0) {
            return NONE;
        }
        int index = (int) (((double) angle / 360.0) * 8.0);
        return CompassPOV.values()[index + 1];
    }

    public static int getPOV(CompassPOV val) {
        if (val == NONE) {
            return -1;
        }
        return (int) ((((double) val.ordinal() - 1) / 8.0) * 360.0);
    }

}
