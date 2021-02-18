package frc.team5431.titan.core.robot.legacy;

/**
 * @deprecated
 * @param <T>
 */
@Deprecated
public abstract class Command<T extends TitanRobot<T>> {
    public String name = "Command";
    public String properties = "None";
    public long startTime = 0;

    public abstract void init(final T robot);

    public enum CommandResult {
        IN_PROGRESS, COMPLETE, CLEAR_QUEUE, RESTART_COMMAND
    }

    public abstract CommandResult update(final T robot);

    public abstract void done(final T robot);

    public String getName() {
        return name;
    }

    public String getProperties() {
        return properties;
    }

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    public double getSecondsElapsed() {
        return getElapsed() / 1000.0;
    }
}