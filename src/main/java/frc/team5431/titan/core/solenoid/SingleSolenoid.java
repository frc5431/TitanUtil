package frc.team5431.titan.core.solenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

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