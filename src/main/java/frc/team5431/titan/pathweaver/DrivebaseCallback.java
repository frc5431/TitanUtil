package frc.team5431.titan.pathweaver;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

public interface DrivebaseCallback {
    public Pose2d getPose();
    public DifferentialDriveWheelSpeeds getWheelSpeeds();
    public void tankDriveVolts(double leftVolts, double rightVolts);
}
