package frc.team5431.titan.core.joysticks;

import frc.team5431.titan.core.joysticks.utils.*;

/**
 * Custom Xbox class that has enums for controller bindings deadzone management
 */
public class Xbox extends Joystick implements POVTools {
    public Xbox(int port) {
        super(port);
    }

    public static enum Button implements ButtonZone {
        A, B, X, Y, BUMPER_L, BUMPER_R, BACK, START
    }

    public static enum Axis implements AxisZone {
        LEFT_X, LEFT_Y, TRIGGER_LEFT, TRIGGER_RIGHT, RIGHT_X, RIGHT_Y
    }

    public ZoneTools getPOVZone() {
        return getPOVZone(0);
    }

    public ZoneTools getPOVZone(int pov) {
        return ZoneTools.find(CompassPOV.class, getPOV(pov));
    }
}
