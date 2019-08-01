package frc.team5431.titan.core.components;

public class TitanToggle {
    private boolean isToggled = false;
    private int prevButton = 0;

    public boolean isToggled(final boolean buttonState) {
        if ((buttonState ? 1 : 0) > prevButton) {
            isToggled = !isToggled;
        }
        prevButton = buttonState ? 1 : 0;
        return isToggled;
    }

    public void setState(final boolean state) {
        isToggled = state;
    }

    public boolean getState(){
        return isToggled;
    }
}