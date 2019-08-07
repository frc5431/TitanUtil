package frc.team5431.titan.core.solenoid;

public class DoubleSolenoid extends edu.wpi.first.wpilibj.DoubleSolenoid {
    private Value currentState = Value.kOff;

    public DoubleSolenoid(final int forwardChannel, final int reverseChannel){
        super(forwardChannel, reverseChannel);
    }

    public DoubleSolenoid(final int moduleNumber, final int forwardChannel, final int reverseChannel){
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