package frc.team5431.titan.core.robot.legacy;

/**
 * @deprecated
 * @param <T>
 */
@Deprecated
public class WaitCommand<T extends TitanRobot<T>> extends Command<T> {

    private final long durationMS;
    private long startTime;

    public WaitCommand(final long ms) {
        name = "WaitStep";
        properties = String.format("Millis %d", ms);
        durationMS = ms;
    }

    @Override
    public void init(final T robot) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public CommandResult update(final T robot) {
        if (System.currentTimeMillis() >= startTime + durationMS) {
            return CommandResult.COMPLETE;
        }

        return CommandResult.IN_PROGRESS;
    }

    @Override
    public void done(final T robot) {
    }
}