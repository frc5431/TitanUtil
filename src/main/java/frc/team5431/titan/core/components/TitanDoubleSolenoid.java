package frc.team5431.titan.core.components;

public class TitanDoubleSolenoid extends edu.wpi.first.wpilibj.DoubleSolenoid{
    private Value currentState = Value.kOff;

    public TitanDoubleSolenoid(final int forwardChannel, final int reverseChannel){
        super(forwardChannel, reverseChannel);
    }

    public TitanDoubleSolenoid(final int moduleNumber, final int forwardChannel, final int reverseChannel){
        super(moduleNumber, forwardChannel, reverseChannel);
    }

    @Override
    public void set(final Value newState){
        if(currentState != newState){
            currentState = newState;
            super.set(newState);
        }
    }
}
