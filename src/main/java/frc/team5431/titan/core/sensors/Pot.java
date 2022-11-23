package frc.team5431.titan.core.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.team5431.titan.core.misc.Calc;

public class Pot extends AnalogInput {
    private double minAngle = 0, maxAngle = 180;
    private double minPotValue = 0, maxPotValue = 4096;
    private double absoluteReset = 0;
    private boolean potDirection = false;

    public Pot(final int port) {
        super(port);
    }

    public double getMinAngle() {
        return minAngle;
    }

    public void setMinAngle(double minAngle) {
        this.minAngle = minAngle;
    }

    public double getMaxAngle() {
        return maxAngle;
    }

    public void setMaxAngle(double maxAngle) {
        this.maxAngle = maxAngle;
    }

    public double getMinPotValue() {
        return minPotValue;
    }

    public void setMinPotValue(double minPotValue) {
        this.minPotValue = minPotValue;
    }

    public double getMaxPotValue() {
        return maxPotValue;
    }

    public void setMaxPotValue(double maxPotValue) {
        this.maxPotValue = maxPotValue;
    }

    public void resetAngle() {
        absoluteReset = getAbsoluteAngle();
        potDirection = absoluteReset > 0; // false == less, true == more
    }

    public double getAngle() {
        final double currentAngle = getAbsoluteAngle();
        return potDirection ? currentAngle - absoluteReset : currentAngle + absoluteReset;
    }

    public double getAbsoluteAngle() {
        return -Calc.map(getAverageVoltage(), minPotValue, maxPotValue, minAngle, maxAngle);
    }
}
