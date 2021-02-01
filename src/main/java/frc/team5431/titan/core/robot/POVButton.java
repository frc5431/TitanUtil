package frc.team5431.titan.core.robot;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team5431.titan.core.joysticks.Joystick;
import frc.team5431.titan.core.joysticks.Joystick.POVZone;
import frc.team5431.titan.core.joysticks.utils.ZoneTools;

public class POVButton extends edu.wpi.first.wpilibj2.command.button.POVButton {

    /**
     *
     * @param joystick The GenericHID object that has the POV
     * @param angle    The desired angle (e.g. 90, 270)
     */
    public POVButton(GenericHID joystick, int angle) {
        super(joystick, angle);
    }

    public POVButton(Joystick joystick, POVZone pov) {
        super(joystick, ZoneTools.isZoneTools(//
                pov.getClass()) ? //
                        ((ZoneTools) pov).getPosition() : //
                        ((Enum<?>) pov).ordinal() //
        );
        // Arrays.asList(pov.getClass().getInterfaces()).contains(ZoneTools.class);
    }

}
