package frc.team5431.titan.core.components.joysticks;

public class Logitech extends Joystick {
    public static enum Button implements ButtonZone{
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE;
    }
    
    public static enum Axis implements AxisZone {
        X, Y, Z, SLIDER;
    }
    
    public Logitech(final int port) {
        super(port);
    }
}