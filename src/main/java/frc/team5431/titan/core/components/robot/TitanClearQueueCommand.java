package frc.team5431.titan.core.components.robot;

public class TitanClearQueueCommand<T extends TitanRobot<T>> extends TitanCommand<T>{

    public TitanClearQueueCommand(){
        name = "ClearQueueCommand";
        properties = "Clears the command queue";
    }

    @Override
    public void init(final T robot) {
    }

    @Override
    public CommandResult update(final T robot) {
        return CommandResult.CLEAR_QUEUE;
    }

    @Override
    public void done(final T robot) {
    }
}