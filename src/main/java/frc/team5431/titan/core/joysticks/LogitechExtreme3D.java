package frc.team5431.titan.core.joysticks;

public class LogitechExtreme3D extends Joystick {
    public LogitechExtreme3D(int port) {
        super(port);
    }
    public static enum Button implements ButtonZone {
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE
    }
    public static enum Axis implements AxisZone {
        X, Y, Z, SLIDER
    }
}
