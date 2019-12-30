package frc.team5431.titan.core.joysticks;

/**
 * Custom joystick class that is identical to the WPILib version except it has
 * deadzone management
 */
public class Joystick extends edu.wpi.first.wpilibj.Joystick {
	private double m_DeadzoneMin = 0.0f;
	private double m_DeadzoneMax = 0.0f;
	
	public interface AxisZone{
	}
	
	public interface ButtonZone {
	}

	/**
	 * @param	value	Pass ID of axis that the value is wanted
	 * @return Returns a double of the axis either [0, 100] or [-100, 100]
	 */
	public double getRawAxis(final AxisZone value) {
		return getRawAxis(((Enum<?>) value).ordinal());
	}

	/**
	 * @param	value	Pass the ID of button that the value is wanted
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

	public double getDeadzoneMin() { return m_DeadzoneMin; }
	public double getDeadzoneMax() { return m_DeadzoneMax; }

	public void setDeadzoneMax(final double val) { this.m_DeadzoneMax = val; }
	public void setDeadzoneMin(final double val) { this.m_DeadzoneMin = val; }

	/**
	 * SetDeadzone is a function that sets the minimum deadzone and the maximum deadzone.
	 * 
	 * @param	min		Set deadzome minimum
	 * @param	max		Set deadzone maximum
	 */
	public void setDeadzone(final double min, final double max) {
		setDeadzoneMin(min);
		setDeadzoneMax(max);
	}

	/**
	 * SetDeadzone is a function that sets the joystick deadzone for both min and max
	 * 
	 * @param	value	Set the absolute value for the deadzone
	 */
	public void setDeadzone(final double value) {
		setDeadzone(-value, value);
	}

	/**
	 * GetRawAxis is a function that allows for you to get joystick data from the device.
	 * 
	 * @param	axis	Set the axis value, this is chosen via DriverStation or TitanUtil Enums
	 * @return			This returns a double in the range of (-1, 1)
	 */
	@Override
	public double getRawAxis(final int axis) {
		double val = super.getRawAxis(axis);
		if (val >= m_DeadzoneMin && val <= m_DeadzoneMax)
			val = 0.0;

		return val;
	}
}
