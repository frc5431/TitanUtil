package frc.team5431.titan.core.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team5431.titan.core.joysticks.CommandJoystick;

public class JoystickButton extends edu.wpi.first.wpilibj2.command.button.JoystickButton {
    /**
     * Standard WPI constructor
     * 
     * @param joystick     the joystick
     * @param buttonNumber the button number
     */
    public JoystickButton(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);
    }

    /**
     * Alternative constructor using TitanUtil Joystick zones
     * 
     * @param joystick the joystick
     * @param zone     the joystick's button zone
     */
    public JoystickButton(CommandJoystick joystick, CommandJoystick.ButtonZone zone) {
        this(joystick.getHID(), (int) ((Enum<?>) zone).ordinal() + 1);
    }
}