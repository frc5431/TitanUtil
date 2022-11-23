package frc.team5431.titan.core.leds;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static edu.wpi.first.wpilibj2.command.Commands.*;

public class Blinkin extends SubsystemBase {
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

    public BlinkinPattern getPattern() {
        return currentPattern;
    }

    public static enum COMMAND {
        PREV, NEXT;
    }

    public Command ledCommand(double pwmValue) {
        return runOnce(() -> this.set(pwmValue));
    }

    public Command ledCommand(BlinkinPattern pattern) {
        return runOnce(() -> this.set(pattern));
    }

    public Command ledCommand(COMMAND cmd) {
        if (cmd == COMMAND.PREV)
            return runOnce(() -> this.prevPattern());
        else
            return runOnce(() -> this.nextPattern());
    }
}
