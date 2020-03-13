package frc.team5431.titan.core.robot.legacy;

import edu.wpi.first.wpilibj.SpeedController;

public class SpeedCommand<T extends TitanRobot<T>> extends Command<T> {
    private final SpeedController controller;
    private final double speed;
    private final long durationMS;
    private long startTime;
    
    public SpeedCommand(final double speed, final long durationMS, final SpeedController controller) {
        this.controller = controller;
        this.speed = speed;
        this.durationMS = durationMS;

        name = "SpeedCommand";
        properties = String.format("Speed: %f; Duration: %d", speed, durationMS);
    }

    @Override
    public void init(final T robot) {
        startTime = System.currentTimeMillis();
    }

    @Override
    public CommandResult update(T robot) {
        controller.set(speed);
        
        if (System.currentTimeMillis() >= startTime + durationMS) {
            return CommandResult.COMPLETE;
        }

        return CommandResult.IN_PROGRESS;
    }

    @Override
    public void done(final T robot) {
    }
}
