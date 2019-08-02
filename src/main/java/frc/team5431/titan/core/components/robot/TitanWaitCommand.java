package frc.team5431.titan.core.components.robot;

public class TitanWaitCommand<T extends TitanRobot<T>> extends TitanCommand<T> {

    private final long durationMS;
    private long startTime;
    
    public TitanWaitCommand(final long ms) {
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