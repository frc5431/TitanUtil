package frc.team5431.titan.examples;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team5431.titan.core.Titan;
import frc.team5431.titan.core.TitanRobot;

import static frc.team5431.titan.examples.TitanUtilRobot.Components.LIFT;

public class TitanUtilRobot extends TitanRobot<TitanUtilRobot> {
    public static final int DRIVER = 0, OPERATOR = 1;

    //WPI_TalonSRX frontLeft, frontRight, backLeft, backRight, shooterLeft, shooterRight;
    //Titan.FSi6S controller;
    //Titan.Toggle toggle = new Titan.Toggle();

    public enum Components implements MainComponent {
        LIFT
    }

    public LiftComponent lift = new LiftComponent();


	@Override
    public void init() {
        addController(new Titan.AssignableJoystick<>(0)); //Driver
        addController(new Titan.AssignableJoystick<>(1)); //Operator

        //Add the lift component
        addComponent(LIFT, lift);

        Titan.CommandQueue<TitanRobot> goToTopThenDown = new Titan.CommandQueue<>();
        goToTopThenDown.add(new LiftCommand(true));
        goToTopThenDown.add(new LiftCommand(false));

        addControllerCommand(DRIVER, Titan.Xbox.Button.BUMPER_R, goToTopThenDown);

        //addControllerCommand(DRIVER);
		/*frontLeft = new WPI_TalonSRX(4);
		frontRight = new WPI_TalonSRX(6);
		backLeft = new WPI_TalonSRX(3);
		backRight = new WPI_TalonSRX(7);

		shooterRight = new WPI_TalonSRX(8);
		shooterLeft = new WPI_TalonSRX(2);

		shooterLeft.set(ControlMode.Follower, shooterRight.getDeviceID());

		backLeft.set(ControlMode.Follower, frontLeft.getDeviceID());
		backRight.set(ControlMode.Follower, frontRight.getDeviceID());

		frontLeft.setInverted(true);
		backLeft.setInverted(true);

		controller = new Titan.FSi6S(0);
		controller.setDeadzone(0.1);*/
	}

	@Override
    public void autoInit() {

    }

    @Override
    public void autoUpdate() {

	}

	@Override
    public void teleInit() {

    }

    @Override
    public void teleUpdate() {
		/*if(toggle.isToggled(controller.getBackLeft())) {
			shooterRight.set(0.8);
		}else {
			shooterRight.set(0.0);
		}

		frontLeft.set(controller.getRawAxis(Titan.FSi6S.Axis.LEFT_Y));
		frontRight.set(controller.getRawAxis(Titan.FSi6S.Axis.RIGHT_Y));*/
	}
}
