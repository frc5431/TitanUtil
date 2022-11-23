package frc.team5431.titan.core.misc;

/**
 * Helper class to store button states.
 * 
 * If using for binding buttons, use WPILib Trigger toggleOnTrue/False methods
 * instead.
 * 
 * @author Ryan Hirasaki
 * @author Colin Wong
 */
public class Toggle {
    private boolean toggleState;
    private boolean prevButtonState = false;

    /**
     * Creates a new toggle with a default state of false.
     */
    public Toggle() {
        this(false);
    }

    /**
     * Creates a new toggle with the specified default state.
     * 
     * @param defaultState the default state of the toggle
     */
    public Toggle(boolean defaultState) {
        this.toggleState = defaultState;
    }

    /**
     * Updates internal state for if toggle has changed
     * 
     * @param pressed raw button data
     */
    public void update(boolean pressed) {
        // "^=true" will toggle bool, "^=false" will not.
        // "pressed && !prev_state" checks whether state needs to be changed.
        // Using this over if/else logic allows the JVM to better optimize the logic to
        // CPU instructions.
        toggleState ^= pressed && !prevButtonState;

        this.prevButtonState = pressed;
    }

    /**
     * @return the toggle state
     */
    public boolean getState() {
        return toggleState;
    }

    /**
     * Use with caution, can break internal logic
     * 
     * @param value the new state value
     */
    public void setState(boolean value) {
        toggleState = value;
    }
}
