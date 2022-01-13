package frc.team5431.titan.core.solenoid;

import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class DoubleSolenoid extends edu.wpi.first.wpilibj.DoubleSolenoid {
    private Value currentState = Value.kOff;

    public DoubleSolenoid(final PneumaticsModuleType moduleType, final int forwardChannel, final int reverseChannel) {
        super(moduleType, forwardChannel, reverseChannel);
    }

    public DoubleSolenoid(final int moduleNumber, final PneumaticsModuleType moduleType, final int forwardChannel,
            final int reverseChannel) {
        super(moduleNumber, moduleType, forwardChannel, reverseChannel);
    }

    @Override
    public void set(final Value newState) {
        if (currentState != newState) {
            currentState = newState;
            super.set(newState);
        }
    }
}