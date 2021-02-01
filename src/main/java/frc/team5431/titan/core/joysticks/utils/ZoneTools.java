package frc.team5431.titan.core.joysticks.utils;

import frc.team5431.titan.core.joysticks.Joystick.POVZone;

/**
 * Utilities for locating Enums with values that do not iterate by one.
 * 
 * @author Ryan Hirasaki
 */
public interface ZoneTools extends POVZone {
    public int getData();

    public static ZoneTools find(Class<? extends ZoneTools> class1, int position) {
        assert (class1.isEnum());
        for (ZoneTools x : class1.getEnumConstants()) {
            if (x.getData() == position)
                return x;
        }
        throw new IndexOutOfBoundsException();
    }
}
