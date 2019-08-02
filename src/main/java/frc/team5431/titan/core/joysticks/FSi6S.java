package frc.team5431.titan.core.joysticks;

public class FSi6S extends Joystick {
    public enum SwitchPosition implements ButtonZone {
        DOWN, NEUTRAL, UP
    }

    public enum Switch implements ButtonZone {
        A, B, C, D
    }

    public enum Axis implements AxisZone {
        RIGHT_X, RIGHT_Y, LEFT_Y, LEFT_X
    }

    public FSi6S(int port) {
        super(port);
    }

    public SwitchPosition getSwitch(final Switch swit) {
        switch (swit) {
        default:
        case A:
            return getRawButton(1) ? SwitchPosition.UP : SwitchPosition.DOWN;
        case B: {
            final boolean top = getRawButton(3), bottom = getRawButton(2);
            if (top) {
                return SwitchPosition.UP;
            } else if (bottom) {
                return SwitchPosition.DOWN;
            } else {
                return SwitchPosition.NEUTRAL;
            }
        }
        case C: {
            final boolean top = getRawButton(5), bottom = getRawButton(4);
            if (top) {
                return SwitchPosition.UP;
            } else if (bottom) {
                return SwitchPosition.DOWN;
            } else {
                return SwitchPosition.NEUTRAL;
            }
        }
        case D:
            return getRawButton(6) ? SwitchPosition.UP : SwitchPosition.DOWN;
        }
    }

    public boolean getBackLeft() {
        return getRawButton(7);
    }

    public boolean getBackRight() {
        return getRawButton(8);
    }
}
