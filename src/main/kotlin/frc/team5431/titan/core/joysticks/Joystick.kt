package frc.team5431.titan.core.joysticks;

/**
 * Custom joystick class that is identical to the WPILib version except it has
 * deadzone management
 */
open class Joystick : edu.wpi.first.wpilibj.Joystick {
	private var m_DeadzoneMin: Double = 0.0;
	private var m_DeadzoneMax: Double = 0.0;
	
	public interface AxisZone;
	
	public interface ButtonZone;

	/**
	 * @param	value	Pass ID of axis that the value is wanted
	 * @return Returns a double of the axis either [0, 100] or [-100, 100]
	 */
	public fun getRawAxis(value: AxisZone): Double {
		return getRawAxis((value as Enum<*>).ordinal);
	}

	/**
	 * @param	value	Pass the ID of button that the value is wanted
	 * @return Returns a boolean of the button's state
	 */
	public fun getRawButton(value: ButtonZone) : Boolean {
		return getRawButton((value as Enum<*>).ordinal + 1);
	}

	/**
	 * @param port Set the joystick USB ID
	 */
	constructor(port: Int) : super(port);

	public fun getDeadzoneMin() : Double { return m_DeadzoneMin; }
	public fun getDeadzoneMax() : Double { return m_DeadzoneMax; }

	public fun setDeadzoneMax(value: Double) { this.m_DeadzoneMax = value; }
	public fun setDeadzoneMin(value: Double) { this.m_DeadzoneMin = value; }

	/**
	 * SetDeadzone is a function that sets the minimum deadzone and the maximum deadzone.
	 * 
	 * @param	min		Set deadzome minimum
	 * @param	max		Set deadzone maximum
	 */
	public fun setDeadzone(min: Double, max: Double) {
		setDeadzoneMin(min);
		setDeadzoneMax(max);
	}

	/**
	 * SetDeadzone is a function that sets the joystick deadzone for both min and max
	 * 
	 * @param	value	Set the absolute value for the deadzone
	 */
	public fun setDeadzone(value: Double) {
		setDeadzone(-value, value);
	}

	/**
	 * GetRawAxis is a function that allows for you to get joystick data from the device.
	 * 
	 * @param	axis	Set the axis value, this is chosen via DriverStation or TitanUtil Enums
	 * @return			This returns a double in the range of (-1, 1)
	 */
	public override fun getRawAxis(axis: Int): Double {
		var value: Double = super.getRawAxis(axis);
		if (value >= m_DeadzoneMin && value <= m_DeadzoneMax) {
			value = 0.0;
		}

		return value;
	}
}
