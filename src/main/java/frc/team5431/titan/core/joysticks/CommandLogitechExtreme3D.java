package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Custom Logitech Extreme class that has enums for controller bindings deadzone
 * management
 */
public class CommandLogitechExtreme3D extends CommandJoystick {
    public CommandLogitechExtreme3D(int port) {
        super(port);
    }

    public static enum Button implements ButtonZone {
        TRIGGER, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE
    }

    public static enum Axis implements AxisZone {
        X, Y, Z, SLIDER
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger trigger() {
        return button(Button.TRIGGER);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger two() {
        return button(Button.TWO);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger three() {
        return button(Button.THREE);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger four() {
        return button(Button.FOUR);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger five() {
        return button(Button.FIVE);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger six() {
        return button(Button.SIX);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger seven() {
        return button(Button.SEVEN);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger eight() {
        return button(Button.EIGHT);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger nine() {
        return button(Button.NINE);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger ten() {
        return button(Button.TEN);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger eleven() {
        return button(Button.ELEVEN);
    }

    /**
     * Get a Trigger to bind commands to. To use this with an EventLoop other than
     * the default command loop, use button(ButtonZone, loop)
     * 
     * @return the Trigger bound to this button
     */
    public Trigger twelve() {
        return button(Button.TWELVE);
    }
}
