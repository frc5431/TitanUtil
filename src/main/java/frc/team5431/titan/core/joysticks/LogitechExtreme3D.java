package frc.team5431.titan.core.joysticks;

/**
 * Custom LogitechExtreme3D class that has enums for controller bindings
 * deadzone management
 */
public class LogitechExtreme3D extends Joystick {
    /**
     * Enums for Button Bindings
     */
    public static enum Button implements ButtonZone{
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE;
    }
    
    /**
     * Enums for Axis Bindings
     */
    public static enum Axis implements AxisZone {
        X, Y, Z, SLIDER;
    }
    
    public LogitechExtreme3D(final int port) {
        super(port);
    }
}