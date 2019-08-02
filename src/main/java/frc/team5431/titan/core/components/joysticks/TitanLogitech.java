package frc.team5431.titan.core.components.joysticks;

public class TitanLogitech extends TitanJoystick {
    public static enum Button implements ButtonZone{
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE;
    }
    
    public static enum Axis implements AxisZone {
        X, Y, Z, SLIDER;
    }
    
    public TitanLogitech(final int port) {
        super(port);
    }
}