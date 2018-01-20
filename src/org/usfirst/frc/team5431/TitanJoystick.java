package org.usfirst.frc.team5431;

import edu.wpi.first.wpilibj.Joystick;

public class TitanJoystick extends Joystick {

	private double deadzoneMin = 0.0f, deadzoneMax = 0.0f;

	public TitanJoystick(final int port) {
		super(port);
	}

	public double getDeadzoneMin() {
		return deadzoneMin;
	}

	public void setDeadzoneMin(final double deadzoneMin) {
		this.deadzoneMin = deadzoneMin;
	}

	public double getDeadzoneMax() {
		return deadzoneMax;
	}

	public void setDeadzoneMax(final double deadzoneMax) {
		this.deadzoneMax = deadzoneMax;
	}

	public void setDeadzone(final double min, final double max) {
		setDeadzoneMin(min);
		setDeadzoneMax(max);
	}

	public void setDeadzone(final double deadzone) {
		setDeadzone(-deadzone, deadzone);
	}

	@Override
	public double getRawAxis(final int axis) {
		final double val = super.getRawAxis(axis);
		if (val >= deadzoneMin && val <= deadzoneMax) {
			return 0.0;
		} else {
			return val;
		}
	}

}
