package frc.team5431.titan.pathfinder;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * @author Ryan Hirasaki
 */
public interface PathFinderControls extends Subsystem {
    public Pose2d getPose();
    public DifferentialDriveWheelSpeeds getWheelSpeeds();
    public void tankDriveVolts(double leftVolts, double rightVolts);
}
