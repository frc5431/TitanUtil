package frc.team5431.titan.core.joysticks;

public class Joystick extends edu.wpi.first.wpilibj.Joystick {
    /**
     * @param port Set the joystick USB ID
     */
    public Joystick(int port) {
        super(port);
    }

    private double m_DeadzoneMin = 0.0;
    private double m_DeadzoneMax = 0.0;

    public interface AxisZone {
    };

    public interface ButtonZone {
    };

    public interface POVZone {
    };

    /**
     * @param value Pass ID of axis that the value is wanted
     * @return Returns a double of the axis either [0, 100] or [-100, 100]
     */
    public double getRawAxis(AxisZone value) {
        return getRawAxis(((Enum<?>) value).ordinal());
    }

    /**
     * @param value Pass the ID of button that the value is wanted
     * @return Returns a boolean of the button's state
     */
    public boolean getRawButton(ButtonZone value) {
        return getRawButton(((Enum<?>) value).ordinal() + 1);
    }

    public double getDeadzoneMin() {
        return m_DeadzoneMin;
    }

    public double getDeadzoneMax() {
        return m_DeadzoneMax;
    }

    public void setDeadzoneMax(double value) {
        this.m_DeadzoneMax = value;
    }

    public void setDeadzoneMin(double value) {
        this.m_DeadzoneMin = value;
    }

    /**
     * SetDeadzone is a function that sets the minimum deadzone and the maximum
     * deadzone.
     * 
     * @param min Set deadzome minimum
     * @param max Set deadzone maximum
     */
    public void setDeadzone(double min, double max) {
        setDeadzoneMin(min);
        setDeadzoneMax(max);
    }

    /**
     * SetDeadzone is a function that sets the joystick deadzone for both min and
     * max
     * 
     * @param value Set the absolute value for the deadzone
     */
    public void setDeadzone(double value) {
        setDeadzone(-value, value);
    }

    /**
     * GetRawAxis is a function that allows for you to get joystick data from the
     * device.
     * 
     * @param axis Set the axis value, this is chosen via DriverStation or TitanUtil
     *             Enums
     * @return This returns a double in the range of (-1, 1)
     */
    public double getRawAxis(int axis) {
        double value = super.getRawAxis(axis);
        if (value >= m_DeadzoneMin && value <= m_DeadzoneMax) {
            value = 0.0;
        }

        return value;
    }

}
