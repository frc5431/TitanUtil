package frc.team5431.titan.core.joysticks;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * Subclass of WPILib's CommandXboxController for deadzone management
 */
public class CommandXboxController extends edu.wpi.first.wpilibj2.command.button.CommandXboxController {
    private final GenericHID m_hid;

    /**
     * Construct an instance of a controller.
     *
     * @param port The port index on the Driver Station that the controller is
     *             plugged into.
     */
    public CommandXboxController(int port) {
        super(port);
        m_hid = new GenericHID(port);
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
     * Set the rumble output for the CommandXboxController
     *
     * @param intensity The normalized value for rumble intensity
     */
    public void rumble(double intensity) {
        m_hid.setRumble(GenericHID.RumbleType.kBothRumble, intensity);
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
