package frc.team5431.titan.core.leds;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Blinkin {
    private Spark spark;
    private BlinkinPattern currentPattern;

    public Blinkin(int pwmPort) {
        this(pwmPort, null);
    }

    public Blinkin(int pwmPort, BlinkinPattern initialPattern) {
        this.spark = new Spark(pwmPort);
        if (initialPattern == null) {
            this.currentPattern = BlinkinPattern.fromNumber(0);
        } else {
            this.currentPattern = initialPattern;
            set(initialPattern);
        }
    }

    public void set(double value) {
        spark.set(value);
    }

    public void set(BlinkinPattern pattern) {
        currentPattern = pattern;
        spark.set(pattern.getSpark());
    }

    public void nextPattern() {
        set(currentPattern.next());
    }

    public void prevPattern() {
        set(currentPattern.previous());
    }
}
