package frc.team5431.titan.core.robot.legacy;

public class ClearQueueCommand<T extends TitanRobot<T>> extends Command<T>{

    public ClearQueueCommand(){
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