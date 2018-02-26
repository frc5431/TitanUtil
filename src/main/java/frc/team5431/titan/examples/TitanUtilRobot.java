package frc.team5431.titan.examples;

import frc.team5431.titan.core.Titan;
import frc.team5431.titan.core.TitanRobot;

import static frc.team5431.titan.examples.TitanUtilRobot.AxisGroups.DRIVE_GROUP;
import static frc.team5431.titan.examples.TitanUtilRobot.Components.DRIVE;
import static frc.team5431.titan.examples.TitanUtilRobot.Components.LIFT;

public class TitanUtilRobot extends TitanRobot<TitanUtilRobot> {
    private static final int DRIVER = 0, OPERATOR = 1;
    private DriveComponent drive = new DriveComponent();
    private LiftComponent lift = new LiftComponent();

	@Override
    public void init() {
        addController(new Titan.AssignableJoystick<>(0)); //Driver
        addController(new Titan.AssignableJoystick<>(1)); //Operator

        /*
         * DRIVEBASE COMMAND CONTROL
         */
        add(DRIVE, drive);
        addAxisGroup(DRIVER, DRIVE_GROUP, Titan.Xbox.Axis.LEFT_Y, Titan.Xbox.Axis.RIGHT_Y);
        //I just realized all of the drives are probably confusing


        /*
         * LIFT COMMAND CONTROL
         */
        //Add the lift component
        add(LIFT, lift);

        //Go all the way up and down when
        Titan.CommandQueue<TitanRobot> goToTopThenDown = new Titan.CommandQueue<>();
        goToTopThenDown.add(new LiftCommand(true));
        goToTopThenDown.add(new LiftCommand(false));

        //The right bumper is pressed
        addCommand(OPERATOR, Titan.Xbox.Button.BUMPER_R, goToTopThenDown);

        /*
         * CUSTOM JOYSTICK COMMANDS
         */
        //By the way this can overlap with the above command queues to add some crazy cool functionality maybe
        //You want it to initiate another command half way up the lift and kill the previous command with a toggle
        addCustom(OPERATOR, Titan.Xbox.Button.B, new Titan.AssignableJoystick.CustomJoystickControl() {
            @Override
            public void current(boolean state) {
                Titan.l("My current value is %b", state);
            }

            @Override
            public void toggled(boolean state) {
                if (state) {
                    Titan.l("I'm intaking the wheels...");
                } else {
                    Titan.l("I've stopped intaking the wheels");
                }
            }
        });

        /*
         * COMPONENT STATES
         */
        setState(DRIVE, DriveComponent.Drive.STOP);
        setState(LIFT, LiftComponent.Lift.STOP);
    }

    @Override
    public void teleInit() {
        setState(LIFT, LiftComponent.Lift.STOP); //Stop the lift
        setState(DRIVE, DriveComponent.Drive.TELEOP); //Start the teleop state
    }

    public enum Components implements MainComponent {
        DRIVE, LIFT
    }

    public enum AxisGroups implements Titan.Joystick.AxisGroup {
        DRIVE_GROUP
    }


	@Override
    public void autoInit() {

    }

    @Override
    public void autoUpdate() {

	}

    @Override
    public void teleUpdate() {

	}
}
