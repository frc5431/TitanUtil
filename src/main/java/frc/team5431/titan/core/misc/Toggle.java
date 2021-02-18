package frc.team5431.titan.core.misc;

public class Toggle {
    private boolean state = false;
    private boolean prevState = false;

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
        if (pressed && !prevState) {
            state = !state;
        }

        this.prevState = pressed;
    }

    public boolean getState() {
        return state;
    }

    /**
     * Use with caution, can break internal logic
     * 
     * @param value new state value
     */
    public void setState(boolean value) {
        state = value;
    }
}
