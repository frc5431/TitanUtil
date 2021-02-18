package frc.team5431.titan.core.subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

public abstract class TitanDifferentalDrivebase extends TitanBaseDrivebase<DifferentialDriveWheelSpeeds> {
    private DifferentialDrive drive;
    private SpeedController left;
    private SpeedController right;
    private final double maxTurnValue;

    protected TitanDifferentalDrivebase(SpeedController left, SpeedController right) {
        this(left, right, 1.0);
    }

    protected TitanDifferentalDrivebase(SpeedController left, SpeedController right, double maxTurnValue) {
        this.left = left;
        this.right = right;
        this.maxTurnValue = maxTurnValue;
        drive = new DifferentialDrive(left, right);
    }

    @Override
    public final void driveTank(double left, double right) {
        drive.tankDrive(left, right);
    }

    @Override
    public final void driveVolts(double left, double right) {
        this.left.setVoltage(left);
        this.right.setVoltage(-right);
    }

    @Override
    public final void driveArcade(double power, double turn) {
        drive.arcadeDrive(power, turn * maxTurnValue);
    }
}
