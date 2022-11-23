package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class CommandJoystick extends edu.wpi.first.wpilibj2.command.button.CommandJoystick {
    /**
     * @param port Set the joystick USB ID
     */
    public CommandJoystick(int port) {
        super(port);
    }

    private double m_DeadzoneMin = 0.0;
    private double m_DeadzoneMax = 0.0;

    /**
     * Enumeration of axes in order of DriverStation listing
     */
    public interface AxisZone {
        default int getValue() {
            return ((Enum<?>) this).ordinal();
        }
    };

    /**
     * Enumeration of buttons in order of DriverStation listing
     */
    public interface ButtonZone {
        default int getValue() {
            return ((Enum<?>) this).ordinal() + 1;
        }
    };

    /**
     * Get the joystick's button value
     * 
     * @param value button value to get, chosen from TitanUtil enum
     * @return whether the button is currently being pressed
     */
    public boolean getRawButton(ButtonZone value) {
        return getRawButton(value.getValue());
    }

    /**
     * Get the joystick's button value
     * 
     * @param value button value to get, either from DriverStation or TitanUtil enum
     * @return whether the button is currently being pressed
     */
    public boolean getRawButton(int value) {
        return getHID().getRawButton(value);
    }

    public Trigger button(ButtonZone value) {
        return super.button(value.getValue());
    }

    public Trigger button(ButtonZone value, EventLoop loop) {
        return super.button(value.getValue(), loop);
    }

    public double getDeadzoneMin() {
        return m_DeadzoneMin;
    }

    public double getDeadzoneMax() {
        return m_DeadzoneMax;
    }

    public void setDeadzoneMin(double value) {
        this.m_DeadzoneMin = value;
    }

    public void setDeadzoneMax(double value) {
        this.m_DeadzoneMax = value;
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
     * @param value axis value to get, chosen via TitanUtil enum
     * @return the value of the axis in the range of (-1, 1)
     */
    public double getRawAxis(AxisZone value) {
        return getRawAxis(value.getValue());
    }

    /**
     * Get joystick axis data with deadzone applied
     * 
     * @param axis axis value to get, chosen via DriverStation or TitanUtil enums
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
