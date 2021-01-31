package frc.team5431.titan.core.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team5431.titan.core.joysticks.Joystick;

public class JoystickButton extends edu.wpi.first.wpilibj2.command.button.JoystickButton {
    /**
     * Standard WPI Constructor
     * 
     * @param joystick
     * @param buttonNumber
     */
    public JoystickButton(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);
    }

    /**
     * Alternative Constructor using TitanUtil Joystick zones
     * 
     * @param joystick
     * @param zone
     */
    public JoystickButton(Joystick joystick, Joystick.ButtonZone zone) {
        this(joystick, (int) ((Enum<?>) zone).ordinal() + 1);
    }
}