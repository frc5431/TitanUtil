package frc.team5431.titan.examples;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.team5431.titan.core.TitanRobot;

import static frc.team5431.titan.examples.TitanUtilRobot.AxisGroups.DRIVE_GROUP;


public class DriveComponent extends TitanRobot.Component<TitanUtilRobot, DriveComponent.Drive> {
    private WPI_TalonSRX frontLeft, frontRight, backLeft, backRight;

    public void drive(final double left, final double right) {
        frontLeft.set(left);
        frontRight.set(right);
    }

    @Override
    public void init(final TitanUtilRobot robot) {
        frontLeft = new WPI_TalonSRX(0);
        frontRight = new WPI_TalonSRX(1);
        backLeft = new WPI_TalonSRX(2);
        backRight = new WPI_TalonSRX(3);

        //Inverted
        frontLeft.setInverted(false);
        frontRight.setInverted(false);
        backLeft.setInverted(false);
        backRight.setInverted(false);

        //Master slave
        backLeft.set(ControlMode.Follower, frontLeft.getDeviceID());
        backRight.set(ControlMode.Follower, frontRight.getDeviceID());
    }

    @Override
    public void update(final TitanUtilRobot robot, final Drive state) {
        switch (state) {
            case FORWARD:
                drive(0.25, 0.25);
                break;
            case BACKWARDS:
                drive(-0.25, -0.25);
                break;
            case STOP:
                drive(0.0, 0.0);
                break;
            case TELEOP:
                double axis[] = robot.getAxisGroup(DRIVE_GROUP); //Get the left_y and right_y xbox axis group
                drive(axis[0], axis[1]);
                break;
        }
    }

    public enum Drive implements TitanRobot.ComponentState {
        FORWARD, BACKWARDS, STOP, TELEOP
    }
}
