package frc.team5431.titan.core.solenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

/**
 * Solenoid class for running high voltage Digital Output on a pneumatics
 * module.
 *
 * <p>
 * The Solenoid class is typically used for pneumatic solenoids, but could be
 * used for any device within the current spec of the module.
 *
 * <p>
 * Modified to ensure set() is called only when changing state.
 *
 * @author Ryan Hirasaki
 */
public class SingleSolenoid extends edu.wpi.first.wpilibj.Solenoid {
    private boolean currentState = false;

    public SingleSolenoid(final PneumaticsModuleType moduleType, final int channel) {
        super(moduleType, channel);
    }

    public SingleSolenoid(final int moduleNumber, final PneumaticsModuleType moduleType, final int channel) {
        super(moduleNumber, moduleType, channel);
    }

    @Override
    public void set(final boolean newState) {
        if (currentState != newState) {
            currentState = newState;
            super.set(newState);
        }
    }
}