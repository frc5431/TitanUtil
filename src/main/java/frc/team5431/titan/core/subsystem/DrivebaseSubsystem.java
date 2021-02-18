package frc.team5431.titan.core.subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class DrivebaseSubsystem extends SubsystemBase {
    private static final String IMPROPER_CAST_DETECTED = "Improper Cast Detected";
    private SpeedController left;
    private SpeedController right;
    private double maxTurnValue;

    protected DrivebaseSubsystem(SpeedController left, SpeedController right, double maxTurnValue) {
        assert left instanceof SpeedController : IMPROPER_CAST_DETECTED;
        assert right instanceof SpeedController : IMPROPER_CAST_DETECTED;
        this.left = left;
        this.right = right;
        this.maxTurnValue = maxTurnValue;
    }

    public final void driveTank(double left, double right) {
        if (this.left.getInverted() == this.right.getInverted()) {
            throw new RuntimeException("Both sides of drivebase are either not or are inverted");
        }
        this.left.set(left);
        this.right.set(right);
    }

    /**
     * A basic implementation of arcade drive using a Arbitrary Feed Forward like
     * formula go to
     * https://docs.ctre-phoenix.com/en/latest/ch16_ClosedLoop.html#arbitrary-feed-forward
     * for more information
     * 
     * @param power speed [-1, 1]
     * @param turn  gravitation [-1, 1]
     */
    public final void driveArcade(double power, double turn) {
        // In some rare cases, a negative zero can be given by a controller
        // and can cause undefined behavior on speed controllers
        if (Math.abs(power) == -0.0)
            power = 0.0;
        double feedFoward = turn * maxTurnValue;
        this.left.set(power - feedFoward);
        this.right.set(power + feedFoward);
    }

    public final void driveVolts(double left, double right) {
        this.left.setVoltage(left);
        this.right.setVoltage(-right);
    }

    public abstract Pose2d getPose();

    public abstract DifferentialDriveWheelSpeeds getWheelSpeeds();
}
