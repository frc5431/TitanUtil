package frc.team5431.titan.core.solenoid;

public class SingleSolenoid extends edu.wpi.first.wpilibj.Solenoid {
    private boolean currentState = false;

    public SingleSolenoid(final int channel) {
        super(channel);
    }

    public SingleSolenoid(final int moduleNumber, final int channel) {
        super(moduleNumber, channel);
    }

    @Override
    public void set(final boolean newState) {
        if (currentState != newState) {
            currentState = newState;
            super.set(newState);
        }
    }
}