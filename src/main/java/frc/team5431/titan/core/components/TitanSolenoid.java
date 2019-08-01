package frc.team5431.titan.core.components;

public class TitanSolenoid extends edu.wpi.first.wpilibj.Solenoid{
    private boolean currentState = false;

    public TitanSolenoid(final int channel){
        super(channel);
    }

    public TitanSolenoid(final int moduleNumber, final int channel) {
        super(moduleNumber, channel);
    }

    @Override
    public void set(final boolean newState){
        if(currentState != newState){
            currentState = newState;
            super.set(newState);
        }
    }
}
