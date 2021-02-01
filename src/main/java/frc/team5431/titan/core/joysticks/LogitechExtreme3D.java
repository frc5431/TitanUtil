package frc.team5431.titan.core.joysticks;

import frc.team5431.titan.core.joysticks.utils.*;

/**
 * Custom Logitech Extreme class that has enums for controller bindings deadzone
 * management
 */
public class LogitechExtreme3D extends Joystick implements POVTools {
    public LogitechExtreme3D(int port) {
        super(port);
    }

    public static enum Button implements ButtonZone {
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE
    }

    public static enum Axis implements AxisZone {
        X, Y, Z, SLIDER
    }

    @Override
    public POVZone getPOVZone() {
        return getPOVZone(0);
    }

    @Override
    public POVZone getPOVZone(int pov) {
        return CompassPOV.find(pov);
    }
}
