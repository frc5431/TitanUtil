package frc.team5431.titan.core.subsystem;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class DrivebaseSubsystem extends SubsystemBase {
    /**
     * 
     * @return Motor
     */
    protected abstract MotorController getLeft();

    /**
     * 
     * @return Motor where reverse is assumed true via SpeedController.setInverted()
     */
    protected abstract MotorController getRight();

    /**
     * 
     * @return rate on how fast the turn should be
     */
    protected abstract double getMaxTurnValue();

    public final void driveTank(double left, double right) {
        getLeft().set(left);
        getRight().set(right);
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
        double feedFoward = turn * getMaxTurnValue();
        getLeft().set(power - feedFoward);
        getRight().set(power + feedFoward);
    }

    public final void driveVolts(double left, double right) {
        getLeft().setVoltage(left);
        getRight().setVoltage(-right);
    }
}
