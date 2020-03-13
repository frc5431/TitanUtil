package frc.team5431.titan.core.robot.legacy;

import java.util.function.Consumer;

public class ConsumerCommand<T extends TitanRobot<T>> extends Command<T> {
    private final Consumer<T> consumer;
    
    public ConsumerCommand(final Consumer<T> consumer) {
        this.consumer = consumer;

        name = "ConsumerCommand";
        properties = "Runs a Consumer";
    }

    @Override
    public void init(final T robot) {
    }

    @Override
    public CommandResult update(T robot) {
        consumer.accept(robot);

        return CommandResult.COMPLETE;
    }

    @Override
    public void done(final T robot) {
    }
}
