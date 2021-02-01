package frc.team5431.titan.core.joysticks.utils;

import java.util.Arrays;

import frc.team5431.titan.core.joysticks.Joystick.POVZone;

/**
 * Utilities for locating Enums with values that do not iterate by one.
 * 
 * @apiNote Designed for internal use.
 * 
 * @author Ryan Hirasaki
 */
public interface ZoneTools extends POVZone {
    public int getPosition();

    public static <T> boolean isZoneTools(Class<T> class1) {
        return Arrays.asList(class1.getInterfaces()).contains(ZoneTools.class);
    }

    public static <T> T find(Class<T> class1, int position) {
        assert (class1.isEnum());
        if (isZoneTools(class1)) {
            for (T x : class1.getEnumConstants()) {
                if (((ZoneTools) x).getPosition() == position)
                    return x;
            }
        } else {
            return class1.getEnumConstants()[position];
        }
        throw new IndexOutOfBoundsException();
    }
}
