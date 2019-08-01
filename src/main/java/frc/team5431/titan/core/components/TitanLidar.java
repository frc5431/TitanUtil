package frc.team5431.titan.core.components;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalSource;

public class TitanLidar extends Counter {
    private int calibrationOffset = 0;
    
    public TitanLidar(final int source) {
        this(new DigitalInput(source));
    }
    
    public TitanLidar(final DigitalSource source) {
        super(source);
        setMaxPeriod(1.0);
        setSemiPeriodMode(true);
        setSamplesToAverage(100);
        reset();
    }
    
    public int getCalibrationOffset() {
        return calibrationOffset;
    }

    public void setCalibrationOffset(final int calibrationOffset) {
        this.calibrationOffset = calibrationOffset;
    }
    /*
        * @return distance in cm*/
    public double getDistance() {
        if(get() < 1) {
            return -1;
        }
        
        return ((getPeriod() * 1000000.0 / 10.0) + calibrationOffset) * 0.39370079;
    }
}
