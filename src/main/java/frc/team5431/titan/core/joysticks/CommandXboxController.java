package frc.team5431.titan.core.joysticks;

/**
 * Subclass of WPILib's CommandXboxController for deadzone management
 */
public class CommandXboxController extends edu.wpi.first.wpilibj2.command.button.CommandXboxController {

    /**
     * Construct an instance of a controller.
     *
     * @param port The port index on the Driver Station that the controller is
     *             plugged into.
     */
    public CommandXboxController(int port) {
        super(port);
    }

    private double m_DeadzoneMin = 0.0;
    private double m_DeadzoneMax = 0.0;

    public double getDeadzoneMin() {
        return m_DeadzoneMin;
    }

    public void setDeadzoneMin(double val) {
        m_DeadzoneMin = val;
    }

    public double getDeadzoneMax() {
        return m_DeadzoneMax;
    }

    public void setDeadzoneMax(double val) {
        m_DeadzoneMax = val;
    }

    /**
     * Sets the minimum deadzone and the maximum deadzone.
     * 
     * @param min deadzone minimum
     * @param max deadzone maximum
     */
    public void setDeadzone(double min, double max) {
        setDeadzoneMin(min);
        setDeadzoneMax(max);
    }

    /**
     * Sets the joystick deadzone for both min and max
     * 
     * @param value Set the absolute value for the deadzone
     */
    public void setDeadzone(double value) {
        setDeadzone(-value, value);
    }

    /**
     * Get joystick axis data with deadzone applied
     * 
     * @param axis axis value to get, chosen via DriverStation
     * @return the value of the axis in the range of (-1, 1)
     */
    @Override
    public double getRawAxis(int axis) {
        double value = super.getRawAxis(axis);
        if (value > m_DeadzoneMin && value < m_DeadzoneMax) {
            value = 0.0;
        }

        return value;
    }
}
