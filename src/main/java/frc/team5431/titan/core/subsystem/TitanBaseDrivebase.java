package frc.team5431.titan.core.subsystem;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class TitanBaseDrivebase<WheelSpeed> extends SubsystemBase {
    /**
     * @param left  percentage [-1.0, 1.0]
     * @param right percentage [-1.0, 1.0]
     */
    public abstract void driveTank(double left, double right);

    /**
     * @param left  voltage [-12.0, 12.0]
     * @param right voltage [-12.0, 12.0]
     */
    public abstract void driveVolts(double left, double right);

    /**
     * @param power speed [-1, 1]
     * @param turn  gravitation [-1, 1]
     */
    public abstract void driveArcade(double power, double turn);

    public abstract Pose2d getPose();

    public abstract WheelSpeed getWheelSpeeds();
}
