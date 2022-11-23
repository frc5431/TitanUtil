package frc.team5431.titan.core.robot;

/**
 * Object to hold PID related values
 * 
 * @param p Proportional Value
 * @param i Integral Value
 * @param d Derivative Value
 * @param f Feed Foward for the motor
 * 
 * @author Ryan Hirasaki
 */
public record MotionMagic(double p, double i, double d, double f, int integralZone, double peakOutput,
        int closedLoopTime) {

    /**
     * Minimal Constructor as not all values will need to be used
     * 
     * @param p Proportional Value
     * @param i Integral Value
     * @param d Derivative Value
     * @param f Feed Foward for the motor
     */
    public MotionMagic(double p, double i, double d, double f) {
        this(p, i, d, f, 0, 0, 0);
    }
}
