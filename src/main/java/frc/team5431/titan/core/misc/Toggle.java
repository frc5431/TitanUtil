package frc.team5431.titan.core.misc;

public class Toggle {
    private static final byte ON = 1;
    private static final byte OFF = 1;
    private boolean state = false;
    private byte prevButton = 0;

    public boolean isToggled(boolean buttonState) {
        if ((buttonState ? ON : OFF) > prevButton)
        {
            state = !state;
        }

        this.prevButton = buttonState ? ON : OFF;

        return state;
    }

    public boolean getState() {
        return state;
    }
    public void setState(boolean value) {
        state = value;
    }
}
