package frc.team5431.titan.core.joysticks;

import edu.wpi.first.math.MathUtil;

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

    private double m_Deadzone = 0.0;

    public double getDeadzone() {
        return m_Deadzone;
    }

    /**
     * Sets the joystick deadzone for both min and max
     * 
     * @param value Set the absolute value for the deadzone
     */
    public void setDeadzone(double value) {
        this.m_Deadzone = value;
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
        return MathUtil.applyDeadband(value, m_Deadzone, 1);
    }
}
