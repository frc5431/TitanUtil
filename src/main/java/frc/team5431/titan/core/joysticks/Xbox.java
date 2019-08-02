package frc.team5431.titan.core.joysticks;

/**
 * Custom Xbox class that has enums for controller bindings
 * deadzone management
 */
public class Xbox extends Joystick {
    public Xbox(int port) {
        super(port);
    }

    /**
     * Enums for Button Bindings
     */
    public enum Button implements ButtonZone {
        // ordered correctly, so ordinal reflects real mapping
        A, B, X, Y, BUMPER_L, BUMPER_R, BACK, START
    }

    /**
     * Enums for Axis Bindings
     */
    public enum Axis implements AxisZone {
        LEFT_X, LEFT_Y, TRIGGER_LEFT, TRIGGER_RIGHT, RIGHT_X, RIGHT_Y
    }
}