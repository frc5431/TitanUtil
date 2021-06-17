package frc.team5431.titan.core.misc;

/**
 * Helper class to store button states
 */
public class Toggle {
    private boolean state;
    private byte prevButton = 0;

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
        this.state = defaultState;
    }

    /**
     * Whether the toggle is active
     * 
     * @param buttonState raw data from the button
     * @return the toggle state after raw data is received
     */
    public boolean isToggled(boolean buttonState) {
        if ((byte) (buttonState ? 1 : 0) > prevButton) {
            state = !state;
        }

        this.prevButton = (byte) (buttonState ? 1 : 0);

        return state;
    }

    /**
     * @return the toggle state
     */
    public boolean getState() {
        return state;
    }

    /**
     * @param value the state to set the toggle to
     */
    public void setState(boolean value) {
        state = value;
    }
}
