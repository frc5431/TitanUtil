package frc.team5431.titan.core.subsystem;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import frc.team5431.titan.core.misc.Logger;

public abstract class TitanDifferentalDrivebase extends TitanBaseDrivebase<DifferentialDriveWheelSpeeds> {
    private static final String PATH = "frc.team5431.titan.core.subsystem.TitanDifferentalDrivebase";
    private SpeedController left;
    private SpeedController right;
    private final double maxTurnValue;

    protected TitanDifferentalDrivebase(Object left, Object right) {
        this(left, right, 1.0);
    }

    protected TitanDifferentalDrivebase(Object left, Object right, double maxTurnValue) {
        this.left = (SpeedController) left;
        this.right = (SpeedController) right;
        this.maxTurnValue = maxTurnValue;

        if (this.left.getInverted() == this.right.getInverted()) {
            Logger.l("WARN: %s: %s", PATH, "Left and Right inversion setting is the same");
        }
    }

    @Override
    public final void driveTank(double left, double right) {
        this.left.set(left);
        this.right.set(right);
    }

    @Override
    public final void driveVolts(double left, double right) {
        this.left.setVoltage(left);
        this.right.setVoltage(-right);
    }

    @Override
    public final void driveArcade(double power, double turn) {
        left.set(power + turn * maxTurnValue);
        right.set(power - turn * maxTurnValue);
    }
}
