package org.usfirst.frc.team5431;

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
}
