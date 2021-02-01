package frc.team5431.titan.core.joysticks;

public class FSi6S extends Joystick {
    public FSi6S(int port) {
        super(port);
    }

    public static enum SwitchPosition implements ButtonZone {
        DOWN, NEUTRAL, UP
    }

    public static enum Switch implements ButtonZone {
        A, B, C, D
    }

    public static enum Axis implements AxisZone {
        RIGHT_X, RIGHT_Y, LEFT_Y, LEFT_X
    }

    public SwitchPosition getSwitch(Switch swit) {
        switch (swit) {
            case B: {
                boolean top = getRawButton(3);
                boolean bottom = getRawButton(2);
                if (top)
                    return SwitchPosition.UP;
                else if (bottom)
                    return SwitchPosition.DOWN;
                else
                    return SwitchPosition.NEUTRAL;
            }
            case C: {
                boolean top = getRawButton(5);
                boolean bottom = getRawButton(4);
                if (top)
                    return SwitchPosition.UP;
                else if (bottom)
                    return SwitchPosition.DOWN;
                else
                    return SwitchPosition.NEUTRAL;
            }
            case D:
                return getRawButton(6) ? SwitchPosition.UP : SwitchPosition.DOWN;
            case A: // fallthrough
            default:
                return getRawButton(1) ? SwitchPosition.UP : SwitchPosition.DOWN;
        }
    }

    public boolean getBackLeft() {
        return getRawButton(7);
    }

    public boolean getBackRight() {
        return getRawButton(8);
    }
}
