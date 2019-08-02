package frc.team5431.titan.core.joysticks;

public class Joystick extends edu.wpi.first.wpilibj.Joystick {
	/**
	 * Custom joystick class that is identical to the WPILib version except it has
	 * deadzone management
	 */
	private double deadzoneMin = 0.0f, deadzoneMax = 0.0f;
	
	public interface AxisZone{
	}
	
	public interface ButtonZone {
	}

	/**
	 * @param value Pass ID of axis that the value is wanted
	 * @return Returns a double of the axis either [0, 100] or [-100, 100]
	 */
	public double getRawAxis(final AxisZone value) {
		return getRawAxis(((Enum<?>) value).ordinal());
	}

	/**
	 * @param value Pass the ID of button that the value is wanted
	 * @return Returns a boolean of the button's state
	 */
	public boolean getRawButton(final ButtonZone value) {
		return getRawButton(((Enum<?>) value).ordinal() + 1);
	}

	/**
	 * @param port Set the joystick USB ID
	 */
	public Joystick(final int port) {
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

	/**
	 * @param min Set deadzome minimum
	 * @param max Set deadzone maximum
	 */
	public void setDeadzone(final double min, final double max) {
		setDeadzoneMin(min);
		setDeadzoneMax(max);
	}

	/**
	 * @param deadzone Set the absolute value for the deadzone
	 */
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
