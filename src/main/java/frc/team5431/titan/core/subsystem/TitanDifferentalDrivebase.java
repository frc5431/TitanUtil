package frc.team5431.titan.core.subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class TitanDifferentalDrivebase extends TitanBaseDrivebase {
    private DifferentialDrive drive;
    private SpeedController left;
    private SpeedController right;
    private double maxTurnValue;

    protected TitanDifferentalDrivebase(SpeedController left, SpeedController right) {
        this(left, right, 1.0);
    }

    protected TitanDifferentalDrivebase(SpeedController left, SpeedController right, double maxTurnValue) {
        this.left = left;
        this.right = right;
        this.maxTurnValue = maxTurnValue;
        drive = new DifferentialDrive(left, right);
    }

    public final void driveTank(double left, double right) {
        if (this.left.getInverted() == this.right.getInverted()) {
            throw new RuntimeException("Both sides of drivebase are either not or are inverted");
        }
        drive.tankDrive(left, right);
    }

    public final void driveVolts(double left, double right) {
        this.left.setVoltage(left);
        this.right.setVoltage(-right);
    }

    public final void driveArcade(double power, double turn) {
        drive.arcadeDrive(power, turn * maxTurnValue);
    }
}
