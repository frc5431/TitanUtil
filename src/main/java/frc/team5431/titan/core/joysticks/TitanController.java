package frc.team5431.titan.core.joysticks;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class TitanController {
    private CommandXboxController xboxController;

    /**
     * Trigger-Based Command Xbox Controller
     * 
     * @param port
     * @param deadzone
     */
    public TitanController(int port, double deadzone) {
        xboxController = new CommandXboxController(port);
        xboxController.setDeadzone(deadzone);
    }

    /* Basic Controller Buttons */

    public Trigger a() {
        return xboxController.a();
    }

    public Trigger b() {
        return xboxController.b();
    }

    public Trigger x() {
        return xboxController.x();
    }

    public Trigger y() {
        return xboxController.y();
    }

    public Trigger leftBumper() {
        return xboxController.leftBumper();
    }

    public Trigger rightBumper() {
        return xboxController.rightBumper();
    }

    public Trigger leftTrigger(double threshold) {
        return xboxController.leftTrigger(threshold);
    }

    public Trigger rightTrigger(double threshold) {
        return xboxController.rightTrigger(threshold);
    }

    public Trigger leftStick() {
        return xboxController.leftStick();
    }

    public Trigger rightStick() {
        return xboxController.rightStick();
    }

    public Trigger start() {
        return xboxController.start();
    }

    public Trigger back() {
        return xboxController.back();
    }

    public Trigger upDpad() {
        return xboxController.povUp();
    }

    public Trigger downDpad() {
        return xboxController.povDown();
    }

    public Trigger leftDpad() {
        return xboxController.povLeft();
    }

    public Trigger rightDpad() {
        return xboxController.povRight();
    }

    /* Axis */

    public double getRightTriggerAxis() {
        return xboxController.getRightTriggerAxis();
    }

    public double getLeftTriggerAxis() {
        return xboxController.getLeftTriggerAxis();
    }

    public double getLeftX() {
        return xboxController.getLeftX();
    }

    public double getLeftY() {
        return xboxController.getLeftY();
    }

    public double getRightX() {
        return xboxController.getRightX();
    }

    public double getRightY() {
        return xboxController.getRightY();
    }

    /* Hid and Rumble */

    public GenericHID getHID() {
        return xboxController.getHID();
    }

    public GenericHID getRumbleHID() {
        return xboxController.getHID();
    }

    public void rumbleController(double leftIntensity, double rightIntensity) {
        getRumbleHID().setRumble(RumbleType.kLeftRumble, leftIntensity);
        getRumbleHID().setRumble(RumbleType.kRightRumble, rightIntensity);
    }
}