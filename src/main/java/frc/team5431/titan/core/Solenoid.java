package frc.team5431.titan.core;

public class Solenoid{
    public static class Single extends edu.wpi.first.wpilibj.Solenoid{
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

    public static class Double extends edu.wpi.first.wpilibj.DoubleSolenoid {
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
