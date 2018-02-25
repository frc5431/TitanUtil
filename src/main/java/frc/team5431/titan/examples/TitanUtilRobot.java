package frc.team5431.titan.examples;

import frc.team5431.titan.core.Titan;
import frc.team5431.titan.core.TitanRobot;

import static frc.team5431.titan.examples.TitanUtilRobot.AxisGroups.DRIVE_GROUP;
import static frc.team5431.titan.examples.TitanUtilRobot.Components.DRIVE;
import static frc.team5431.titan.examples.TitanUtilRobot.Components.LIFT;

public class TitanUtilRobot extends TitanRobot<TitanUtilRobot> {
    public static final int DRIVER = 0, OPERATOR = 1;

    //WPI_TalonSRX frontLeft, frontRight, backLeft, backRight, shooterLeft, shooterRight;
    //Titan.FSi6S controller;
    //Titan.Toggle toggle = new Titan.Toggle();

    public DriveComponent drive = new DriveComponent();

	@Override
    public void init() {
        addController(new Titan.AssignableJoystick<>(0)); //Driver
        addController(new Titan.AssignableJoystick<>(1)); //Operator

        /*
         * DRIVEBASE COMMAND CONTROL
         */
        addComponent(DRIVE, drive);
        addControllerAxisGroup(DRIVER, DRIVE_GROUP, Titan.Xbox.Axis.LEFT_Y, Titan.Xbox.Axis.RIGHT_Y);
        //I just realized all of the drives are probably confusing


        /*
         * LIFT COMMAND CONTROL
         */
        //Add the lift component
        addComponent(LIFT, lift);

        //Go all the way up and down when
        Titan.CommandQueue<TitanRobot> goToTopThenDown = new Titan.CommandQueue<>();
        goToTopThenDown.add(new LiftCommand(true));
        goToTopThenDown.add(new LiftCommand(false));

        //The right bumper is pressed
        addControllerCommand(OPERATOR, Titan.Xbox.Button.BUMPER_R, goToTopThenDown);

        /*
         * COMPONENT STATES
         */
        setCompState(DRIVE, DriveComponent.Drive.STOP);
        setCompState(LIFT, LiftComponent.Lift.STOP);
    }

    @Override
    public void teleInit() {
        setCompState(DRIVE, DriveComponent.Drive.TELEOP); //Start the teleop state
    }

    public LiftComponent lift = new LiftComponent();


    public enum Components implements MainComponent {
        DRIVE, LIFT
    }

	@Override
    public void autoInit() {

    }

    @Override
    public void autoUpdate() {

	}

    public enum AxisGroups implements Titan.Joystick.AxisGroup {
        DRIVE_GROUP
    }

    @Override
    public void teleUpdate() {

	}
}
