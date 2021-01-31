package frc.team5431.titan.core.misc;

public class Toggle {
    private boolean state = false;
    private byte prevButton = 0;

    public boolean isToggled(boolean buttonState) {
        if ((byte) (buttonState ? 1 : 0) > prevButton) {
            state = !state;
        }

        this.prevButton = (byte) (buttonState ? 1 : 0);

        return state;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean value) {
        state = value;
    }
}
