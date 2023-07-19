package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.team5431.titan.core.misc.Calc;

/**
 * @author Rudy Soliz
 */

// This is made for the SkyFly I6-S Drone Controller

// I opted for using the GenericHID because i am personally unfamiliar with
// CommandJoystick.

// Potetially remake there, but considering it works, i dont see too much reason
// to change anything to dramatic.
public class CommandSkyFlyController extends CommandGenericHID {
    public CommandSkyFlyController(int port) {
        super(port);
    }

    /**
     * This was made because for some reason, this controller has a range of 0.83
     * for some reason.
     * 
     * @param in Raw Joystick Input
     * @return range from -1 to 1
     */
    public double toFullRange(double in) {
        return Calc.map(in, -0.83, 0.83, -1, 1);
    }

    public double getLeftX() {
        return toFullRange(getRawAxis(3));
    }

    public double getLeftY() {
        return toFullRange(getRawAxis(2));
    }

    public double getRightX() {
        return toFullRange(getRawAxis(0));
    }

    public double getRightY() {
        return toFullRange(getRawAxis(1));
    }

    /**
     * The trigger is triggered when the button on the back left is pressed
     * 
     * @return A trigger that maps to the button on the back right of the controller
     */
    public Trigger backLeft() {
        return button(7);
    }

    /**
     * The trigger is triggered when the button on the back right is pressed
     * 
     * @return A trigger that maps to the button on the back right of the controller
     */
    public Trigger backRight() {
        return button(8);
    }

    /**
     * The trigger is positive when the switch is flipped forwards
     * 
     * @return A trigger that maps to the switch on the left of the controller
     */
    public Trigger leftSwitch() {
        return button(1);
    }

    /**
     * The trigger is positive when the switch is flipped forwards
     * 
     * @return A trigger that maps to the switch on the right of the controller
     */
    public Trigger rightSwitch() {
        return button(6).negate();
    }

    /**
     * The trigger is positive when the right lever is flipped backwards.
     * This lever is to the left of the right switch
     * 
     * @return A trigger that maps to the switch on the left of the controller
     */
    public Trigger rightBackwards() {
        return button(4);
    }

    /**
     * The trigger is positive when the right lever is flipped forwards.
     * This lever is to the left of the right switch
     * 
     * @return A trigger that maps to the switch on the left of the controller
     */
    public Trigger rightForwards() {
        return button(6);
    }

    /**
     * The trigger is positive when the left lever is flipped backwards.
     * This lever is to the right of the left switch
     * 
     * @return A trigger that maps to the switch on the left of the controller
     */
    public Trigger leftBackwards() {
        return button(2);
    }

    /**
     * The trigger is positive when the left lever is flipped forwards.
     * This lever is to the right of the left switch
     * 
     * @return A trigger that maps to the switch on the left of the controller
     */
    public Trigger leftForwards() {
        return button(3);
    }

}
