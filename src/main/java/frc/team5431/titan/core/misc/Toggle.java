package frc.team5431.titan.core.misc;

public class Toggle {
    private boolean curr_state = false;
    private boolean prev_state = false;

    /**
     * @deprecated use update() as it is more descriptive
     * @param buttonState button status
     * @return current state
     */
    @Deprecated(forRemoval = true)
    public boolean isToggled(boolean buttonState) {
        update(buttonState);
        return getState();
    }

    /**
     * Updates internal state for if toggle has changed
     * 
     * @param pressed button status
     */
    public void update(boolean pressed) {
        // "^=true" will toggle bool, "^=false" will not.
        // "pressed && !prev_state" checks whether state needs to be changed.
        // Using this over if/else logic allows the JVM to better optimize the logic to
        // CPU instructions.
        curr_state ^= pressed && !prev_state;

        this.prev_state = pressed;
    }

    public boolean getState() {
        return curr_state;
    }

    /**
     * Use with caution, can break internal logic
     * 
     * @param value new state value
     */
    public void setState(boolean value) {
        curr_state = value;
    }
}
