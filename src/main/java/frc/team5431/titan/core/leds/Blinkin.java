package frc.team5431.titan.core.leds;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Blinkin extends SubsystemBase {
    private Spark spark;
    private BlinkinPattern currentPattern;

    public Blinkin(int pwmPort) {
        this(pwmPort, null);
    }

    public Blinkin(int pwmPort, BlinkinPattern initialPattern) {
        this.spark = new Spark(pwmPort);
        this.spark.setSafetyEnabled(false);
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

    /**
     * Sets blinkin lights to current team color in Teleop
     */
    public void setTeamColor() {
        var robotTeam = DriverStation.getAlliance();
        if (DriverStation.isTeleop()) {
            if (robotTeam.get() == Alliance.Red) {
                set(BlinkinPattern.RED);
            }
            if (robotTeam.get() == Alliance.Blue) {
                set(BlinkinPattern.BLUE);
            }
        }
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
        return Commands.runOnce(() -> this.set(pwmValue));
    }

    public Command ledCommand(BlinkinPattern pattern) {
        return Commands.runOnce(() -> this.set(pattern));
    }

    public Command ledCommand(COMMAND cmd) {
        if (cmd == COMMAND.PREV)
            return Commands.runOnce(() -> this.prevPattern());
        else
            return Commands.runOnce(() -> this.nextPattern());
    }

    public Command ledRunCommand(double pwmValue) {
        return Commands.run(() -> this.set(pwmValue), this);
    }

    public Command ledRunCommand(BlinkinPattern pattern) {
        return Commands.run(() -> this.set(pattern), this);
    }

    public Command ledRunCommand(COMMAND cmd) {
        if (cmd == COMMAND.PREV)
            return Commands.run(() -> this.prevPattern(), this);
        else
            return Commands.run(() -> this.nextPattern(), this);
    }
}
