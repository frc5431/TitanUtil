package frc.team5431.titan.core.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team5431.titan.core.joysticks.Joystick;
import frc.team5431.titan.core.joysticks.utils.CompassPOV;

public class POVButton extends edu.wpi.first.wpilibj2.command.button.POVButton {

    /**
     * @param joystick The GenericHID object that has the POV
     * @param angle    The desired angle (e.g. 90, 270)
     */
    public POVButton(GenericHID joystick, int angle) {
        super(joystick, angle);
    }

    /**
     *
     * @param joystick The GenericHID object that has the POV
     * @param pov      The desired angle in enum format
     */
    public POVButton(Joystick joystick, CompassPOV pov) {
        super(joystick, CompassPOV.getPOV(pov));
    }
}
