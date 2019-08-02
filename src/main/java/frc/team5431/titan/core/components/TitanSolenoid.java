package frc.team5431.titan.core.components;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class TitanSolenoid{
    public static class Single extends Solenoid{
        private boolean currentState = false;

    public Single(final int channel){
        super(channel);
    }

    public Single(final int moduleNumber, final int channel) {
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

    public static class Double extends DoubleSolenoid {
        private Value currentState = Value.kOff;

        public Double(final int forwardChannel, final int reverseChannel){
            super(forwardChannel, reverseChannel);
        }
    
        public Double(final int moduleNumber, final int forwardChannel, final int reverseChannel){
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
}
