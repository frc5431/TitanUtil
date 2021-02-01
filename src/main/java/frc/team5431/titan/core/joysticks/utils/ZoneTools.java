package frc.team5431.titan.core.joysticks.utils;

/**
 * Utilities for locating Enums with values that do not iterate by one.
 * 
 * @apiNote Designed for internal use.
 * 
 * @author Ryan Hirasaki
 */
public interface ZoneTools {
    public int getPosition();

    public static <T extends ZoneTools> T find(Class<T> class1, int position) {
        assert (class1.isEnum());
        for (T x : class1.getEnumConstants()) {
            if (x.getPosition() == position)
                return x;
        }
        return null;
    }
}
