package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class CommandFSi6S extends CommandJoystick {
    public CommandFSi6S(int port) {
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

    /**
     * Creates a Trigger that evaluates to true when the switch is in the specified
     * position
     * 
     * @param swit desired switch to check
     * @param pos  position to check for
     * @return a new Trigger for the switch
     */
    public Trigger switchTrigger(Switch swit, SwitchPosition pos) {
        return new Trigger(() -> getSwitch(swit) == pos);
    }

    /**
     * Creates a Trigger that evaluates to true when the switch is in the specified
     * position
     * 
     * @param swit desired switch to check
     * @param pos  position to check for
     * @return a new Trigger for the switch
     */
    public Trigger switchTrigger(Switch swit, SwitchPosition pos, EventLoop loop) {
        return new Trigger(loop, () -> getSwitch(swit) == pos);
    }

    public boolean getBackLeft() {
        return getRawButton(7);
    }

    public Trigger backLeft() {
        return button(7);
    }

    public Trigger backLeft(EventLoop loop) {
        return button(7, loop);
    }

    public boolean getBackRight() {
        return getRawButton(8);
    }

    public Trigger backRight() {
        return button(8);
    }

    public Trigger backRight(EventLoop loop) {
        return button(8, loop);
    }
}
